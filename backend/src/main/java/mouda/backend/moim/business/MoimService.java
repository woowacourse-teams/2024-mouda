package mouda.backend.moim.business;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.exception.CommentErrorMessage;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.infrastructure.ZzimRepository;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimEditRequest;
import mouda.backend.moim.presentation.response.comment.CommentResponse;
import mouda.backend.moim.presentation.response.moim.MoimDetailsFindResponse;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponse;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponses;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;
	private final ZzimRepository zzimRepository;
	private final CommentRepository commentRepository;
	private final NotificationService notificationService;

	public Moim createMoim(Long darakbangId, DarakbangMember darakbangMember, MoimCreateRequest moimCreateRequest) {
		Moim moim = moimRepository.save(moimCreateRequest.toEntity(darakbangId));
		Chamyo chamyo = Chamyo.builder()
			.darakbangMember(darakbangMember)
			.moim(moim)
			.moimRole(MoimRole.MOIMER)
			.build();
		chamyoRepository.save(chamyo);

		notificationService.notifyToMembers(NotificationType.MOIM_CREATED, darakbangId, moim, darakbangMember);
		return moim;
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findAllMoim(Long darakbangId, DarakbangMember darakbangMember) {
		List<Moim> moims = moimRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId);
		return new MoimFindAllResponses(
			moims.stream()
				.map(moim -> {
					int currentPeople = chamyoRepository.countByMoim(moim);
					boolean isZzimed = zzimRepository.existsByMoimIdAndDarakbangMemberId(moim.getId(),
						darakbangMember.getId());
					return MoimFindAllResponse.toResponse(moim, currentPeople, isZzimed);
				})
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public MoimDetailsFindResponse findMoimDetails(long darakbangId, long moimId) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}

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

	public void deleteMoim(long darakbangId, long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}
		validateCanDeleteMoim(moim, darakbangMember);

		chamyoRepository.deleteAllByMoimId(moimId);
		zzimRepository.deleteAllByMoimId(moimId);
		moimRepository.delete(moim);
	}

	private void validateCanDeleteMoim(Moim moim, DarakbangMember darakbangMember) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndDarakbangMemberId(moim.getId(), darakbangMember.getId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND))
			.getMoimRole();

		if (moimRole != MoimRole.MOIMER) {
			throw new MoimException(HttpStatus.FORBIDDEN, MoimErrorMessage.NOT_ALLOWED_TO_DELETE);
		}
	}

	public void createComment(
		Long darakbangId, Long moimId, DarakbangMember darakbangMember, CommentCreateRequest request
	) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}

		Long parentId = request.parentId();
		if (parentId != null && !commentRepository.existsById(parentId)) {
			throw new CommentException(HttpStatus.BAD_REQUEST, CommentErrorMessage.PARENT_NOT_FOUND);
		}

		commentRepository.save(request.toEntity(moim, darakbangMember));

		if (Objects.equals(chamyoRepository.findMoimerIdByMoimId(moimId), darakbangMember.getMemberId())) {
			return;
		}
		sendCommentNotification(moim, darakbangMember, parentId, darakbangId);
	}

	private void sendCommentNotification(Moim moim, DarakbangMember author, Long parentId, Long darakbangId) {
		if (parentId != null) {
			Long parentCommentAuthorId = commentRepository.findMemberIdByParentId(parentId);
			notificationService.notifyToMember(NotificationType.NEW_REPLY, darakbangId, moim, author,
				parentCommentAuthorId);
		}

		notificationService.notifyToMembers(NotificationType.NEW_COMMENT, darakbangId, moim, author);
	}

	public void completeMoim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}
		validateCanCompleteMoim(moim, darakbangMember);

		moimRepository.updateMoimStatusById(moimId, MoimStatus.COMPLETED);

		notificationService.notifyToMembers(NotificationType.MOIMING_COMPLETED, darakbangId, moim, darakbangMember);
	}

	private void validateCanCompleteMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimerWithErrorMessage(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_COMPLETE);
		MoimStatus moimStatus = moim.getMoimStatus();
		if (moimStatus == MoimStatus.COMPLETED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_COMPLETED);
		}
		if (moimStatus == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	public void cancelMoim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}
		validateCanCancelMoim(moim, darakbangMember);

		moimRepository.updateMoimStatusById(moimId, MoimStatus.CANCELED);

		notificationService.notifyToMembers(NotificationType.MOIM_CANCELLED, darakbangId, moim, darakbangMember);
	}

	private void validateCanCancelMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimerWithErrorMessage(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_CANCEL);
		if (moim.getMoimStatus() == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	public void reopenMoim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}
		validateCanReopenMoim(moim, darakbangMember);

		moimRepository.updateMoimStatusById(moimId, MoimStatus.MOIMING);

		notificationService.notifyToMembers(NotificationType.MOINING_REOPENED, darakbangId, moim, darakbangMember);
	}

	private void validateCanReopenMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimerWithErrorMessage(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_REOPEN);
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

	private void validateIsMoimerWithErrorMessage(Moim moim, DarakbangMember darakbangMember,
		MoimErrorMessage errorMessage) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndDarakbangMemberId(moim.getId(), darakbangMember.getId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND))
			.getMoimRole();
		if (moimRole != MoimRole.MOIMER) {
			throw new MoimException(HttpStatus.FORBIDDEN, errorMessage);
		}
	}

	public void editMoim(Long darakbangId, MoimEditRequest request, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(request.moimId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.NOT_FOUND);
		}
		validateCanEditMoim(moim, darakbangMember);

		moim.update(request.title(), request.date(), request.time(), request.place(), request.maxPeople(),
			request.description(), chamyoRepository.countByMoim(moim));
		moimRepository.save(moim);

		notificationService.notifyToMembers(NotificationType.MOIM_MODIFIED, darakbangId, moim, darakbangMember);
	}

	private void validateCanEditMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimerWithErrorMessage(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_EDIT);
		MoimStatus moimStatus = moim.getMoimStatus();
		if (moimStatus == MoimStatus.COMPLETED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_COMPLETED);
		}
		if (moimStatus == MoimStatus.CANCELED) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	public MoimFindAllResponses findAllMyMoim(DarakbangMember darakbangMember, FilterType filter) {
		Stream<Chamyo> chamyoStream = chamyoRepository.findAllByDarakbangMemberIdOrderByIdDesc(darakbangMember.getId())
			.stream();

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
				boolean isZzimed = zzimRepository.existsByMoimIdAndDarakbangMemberId(moim.getId(),
					darakbangMember.getId());
				return MoimFindAllResponse.toResponse(moim, currentPeople, isZzimed);
			})
			.toList();

		return new MoimFindAllResponses(responses);
	}

	public MoimFindAllResponses findZzimedMoim(DarakbangMember darakbangMember) {
		List<Zzim> zzims = zzimRepository.findAllByDarakbangMemberIdOrderByIdDesc(darakbangMember.getId());

		List<MoimFindAllResponse> responses = zzims.stream()
			.map(zzim -> {
				Moim moim = zzim.getMoim();
				int currentPeople = chamyoRepository.countByMoim(moim);
				boolean zzimed = zzimRepository.existsByMoimIdAndDarakbangMemberId(moim.getId(),
					darakbangMember.getId());
				return MoimFindAllResponse.toResponse(zzim.getMoim(), currentPeople, zzimed);
			}).toList();

		return new MoimFindAllResponses(responses);
	}
}
