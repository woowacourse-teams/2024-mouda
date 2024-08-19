package mouda.backend.moim.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.comment.domain.Comment;
import mouda.backend.comment.dto.request.CommentCreateRequest;
import mouda.backend.comment.dto.response.CommentResponse;
import mouda.backend.comment.exception.CommentErrorMessage;
import mouda.backend.comment.exception.CommentException;
import mouda.backend.comment.repository.CommentRepository;
import mouda.backend.common.RequiredDarakbangMoim;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimEditRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.service.NotificationService;
import mouda.backend.zzim.domain.Zzim;
import mouda.backend.zzim.repository.ZzimRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	@Value("${url.base}")
	private String baseUrl;

	@Value("${url.moim}")
	private String moimUrl;

	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;
	private final ZzimRepository zzimRepository;
	private final CommentRepository commentRepository;
	private final NotificationService notificationService;

	public Moim createMoim(Long darakbangId, DarakbangMember member, MoimCreateRequest moimCreateRequest) {
		Moim moim = moimRepository.save(moimCreateRequest.toEntity(darakbangId));
		Chamyo chamyo = Chamyo.builder()
			.member(member)
			.moim(moim)
			.moimRole(MoimRole.MOIMER)
			.build();
		chamyoRepository.save(chamyo);

		NotificationType notificationType = NotificationType.MOIM_CREATED;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(moim.getTitle()))
			.targetUrl(baseUrl + moimUrl + "/" + moim.getId())
			.build();

		notificationService.notifyToAllExceptMember(notification, member.getId());
		return moim;
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findAllMoim(Long darakbangId, DarakbangMember member) {
		List<Moim> moims = moimRepository.findAllByDarakbangId(darakbangId);
		return new MoimFindAllResponses(
			moims.stream()
				.map(moim -> {
					int currentPeople = chamyoRepository.countByMoim(moim);
					boolean isZzimed = zzimRepository.existsByMoimIdAndMemberId(moim.getId(), member.getId());
					return MoimFindAllResponse.toResponse(moim, currentPeople, isZzimed);
				})
				.toList()
		);
	}

	@Transactional(readOnly = true)
	@RequiredDarakbangMoim
	public MoimDetailsFindResponse findMoimDetails(long darakbangId, long moimId) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

		List<Comment> comments = commentRepository.findAllByMoimIdOrderByCreatedAt(moimId);
		List<CommentResponse> commentResponses = toCommentResponse(comments);

		return MoimDetailsFindResponse.toResponse(moim, chamyoRepository.countByMoim(moim), commentResponses);
	}

	private List<CommentResponse> toCommentResponse(List<Comment> comments) {
		Map<Long, List<Comment>> children = comments.stream()
			.filter(Comment::isChild)
			.collect(groupingBy(Comment::getParentId));

		return comments.stream()
			.filter(Comment::isParent)
			.map(comment -> CommentResponse.toResponse(comment, children.getOrDefault(comment.getId(), List.of())))
			.toList();
	}

	@RequiredDarakbangMoim
	public void deleteMoim(long darakbangId, long id, DarakbangMember member) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		validateCanDeleteMoim(moim, member);

		chamyoRepository.deleteAllByMoimId(id);
		zzimRepository.deleteAllByMoimId(id);
		moimRepository.delete(moim);
	}

	private void validateCanDeleteMoim(Moim moim, DarakbangMember member) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndMemberId(moim.getId(), member.getId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND))
			.getMoimRole();

		if (moimRole != MoimRole.MOIMER) {
			throw new MoimException(HttpStatus.FORBIDDEN, MoimErrorMessage.NOT_ALLOWED_TO_DELETE);
		}
	}

	public void updateMoimStatusById(long id, MoimStatus status) {
		moimRepository.updateMoimStatusById(id, status);
	}

	@RequiredDarakbangMoim
	public void createComment(Long darakbangId, Long moimId, DarakbangMember member, CommentCreateRequest request) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

		Long parentId = request.parentId();
		if (parentId != null && !commentRepository.existsById(parentId)) {
			throw new CommentException(HttpStatus.BAD_REQUEST, CommentErrorMessage.PARENT_NOT_FOUND);
		}

		commentRepository.save(request.toEntity(moim, member));

		sendCommentNotification(moim.getId(), member, parentId);
	}

	private void sendCommentNotification(Long moimId, DarakbangMember author, Long parentId) {
		if (parentId != null) {
			Long parentCommentAuthorId = commentRepository.findMemberIdByParentId(parentId);
			MoudaNotification notification = MoudaNotification.builder()
				.type(NotificationType.NEW_REPLY)
				.body(NotificationType.NEW_REPLY.createMessage(author.getNickname()))
				.targetUrl(baseUrl + moimUrl + "/" + moimId)
				.build();
			notificationService.notifyToMember(notification, parentCommentAuthorId);
		}

		MoudaNotification notification = MoudaNotification.builder()
			.type(NotificationType.NEW_COMMENT)
			.body(NotificationType.NEW_COMMENT.createMessage(author.getNickname()))
			.targetUrl(baseUrl + moimUrl + "/" + moimId)
			.build();
		notificationService.notifyToMember(notification, chamyoRepository.findMoimerIdByMoimId(moimId));
	}

	@RequiredDarakbangMoim
	public void completeMoim(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		validateCanCompleteMoim(moim, member);

		moimRepository.updateMoimStatusById(moimId, MoimStatus.COMPLETED);

		sendNotificationWhenMoimStatusChanged(moim, NotificationType.MOIMING_COMPLETED);
	}

	private void sendNotificationWhenMoimStatusChanged(Moim moim, NotificationType notificationType) {
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(moim.getTitle()))
			.targetUrl(baseUrl + moimUrl + "/" + moim.getId())
			.build();

		List<Long> membersToSendNotification = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.filter(chamyo -> chamyo.getMoimRole() != MoimRole.MOIMER)
			.map(chamyo -> chamyo.getMember().getId())
			.toList();

		notificationService.notifyToMembers(notification, membersToSendNotification);
	}

	private void validateCanCompleteMoim(Moim moim, DarakbangMember member) {
		validateIsMoimerWithErrorMessage(moim, member, MoimErrorMessage.NOT_ALLOWED_TO_COMPLETE);
		MoimStatus moimStatus = moim.getMoimStatus();
		if (moimStatus == MoimStatus.COMPLETED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_COMPLETED);
		}
		if (moimStatus == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	@RequiredDarakbangMoim
	public void cancelMoim(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		validateCanCancelMoim(moim, member);

		moimRepository.updateMoimStatusById(moimId, MoimStatus.CANCELED);

		sendNotificationWhenMoimStatusChanged(moim, NotificationType.MOIM_CANCELLED);
	}

	private void validateCanCancelMoim(Moim moim, DarakbangMember member) {
		validateIsMoimerWithErrorMessage(moim, member, MoimErrorMessage.NOT_ALLOWED_TO_CANCEL);
		if (moim.getMoimStatus() == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	@RequiredDarakbangMoim
	public void reopenMoim(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		validateCanReopenMoim(moim, member);

		moimRepository.updateMoimStatusById(moimId, MoimStatus.MOIMING);

		sendNotificationWhenMoimStatusChanged(moim, NotificationType.MOINING_REOPENED);
	}

	private void validateCanReopenMoim(Moim moim, DarakbangMember member) {
		validateIsMoimerWithErrorMessage(moim, member, MoimErrorMessage.NOT_ALLOWED_TO_REOPEN);
		int currentPeople = chamyoRepository.countByMoim(moim);
		if (currentPeople >= moim.getMaxPeople()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_FULL_FOR_REOPEN);
		}
		MoimStatus moimStatus = moim.getMoimStatus();
		if (moimStatus == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
		if (moimStatus == MoimStatus.MOIMING) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_MOIMING);
		}
	}

	private void validateIsMoimerWithErrorMessage(Moim moim, DarakbangMember member, MoimErrorMessage errorMessage) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndMemberId(moim.getId(), member.getId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND))
			.getMoimRole();
		if (moimRole != MoimRole.MOIMER) {
			throw new MoimException(HttpStatus.FORBIDDEN, errorMessage);
		}
	}

	@RequiredDarakbangMoim
	public void editMoim(Long darakbangId, Long moimId, MoimEditRequest request, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		validateCanEditMoim(moim, member);

		moim.update(request.title(), request.date(), request.time(), request.place(), request.maxPeople(),
			request.description(), chamyoRepository.countByMoim(moim));
		moimRepository.save(moim);

		sendNotificationWhenMoimStatusChanged(moim, NotificationType.MOIM_MODIFIED);
	}

	private void validateCanEditMoim(Moim moim, DarakbangMember member) {
		validateIsMoimerWithErrorMessage(moim, member, MoimErrorMessage.NOT_ALLOWED_TO_EDIT);
		MoimStatus moimStatus = moim.getMoimStatus();
		if (moimStatus == MoimStatus.COMPLETED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_COMPLETED);
		}
		if (moimStatus == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	public MoimFindAllResponses findAllMyMoim(DarakbangMember member, FilterType filter) {
		Stream<Chamyo> chamyoStream = chamyoRepository.findAllByMemberId(member.getId()).stream();

		if (filter == FilterType.PAST) {
			chamyoStream = chamyoStream.filter(chamyo -> chamyo.getMoim().isPastMoim());
		}
		if (filter == FilterType.UPCOMING) {
			chamyoStream = chamyoStream.filter(chamyo -> chamyo.getMoim().isUpcomingMoim());
		}

		List<MoimFindAllResponse> responses = chamyoStream
			.map(chamyo -> {
				Moim moim = chamyo.getMoim();
				int currentPeople = chamyoRepository.countByMoim(moim);
				boolean isZzimed = zzimRepository.existsByMoimIdAndMemberId(moim.getId(), member.getId());
				return MoimFindAllResponse.toResponse(moim, currentPeople, isZzimed);
			})
			.toList();

		return new MoimFindAllResponses(responses);
	}

	public MoimFindAllResponses findZzimedMoim(DarakbangMember member) {
		List<Zzim> zzims = zzimRepository.findAllByMemberId(member.getId());

		List<MoimFindAllResponse> responses = zzims.stream()
			.map(zzim -> {
				Moim moim = zzim.getMoim();
				int currentPeople = chamyoRepository.countByMoim(moim);
				boolean zzimed = zzimRepository.existsByMoimIdAndMemberId(moim.getId(), member.getId());
				return MoimFindAllResponse.toResponse(zzim.getMoim(), currentPeople, zzimed);
			}).toList();

		return new MoimFindAllResponses(responses);
	}
}
