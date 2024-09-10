package mouda.backend.moim.business;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimOverview;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.domain.ParentComment;
import mouda.backend.moim.exception.CommentErrorMessage;
import mouda.backend.moim.exception.CommentException;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.implement.finder.CommentFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.CommentRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimEditRequest;
import mouda.backend.moim.presentation.response.comment.CommentResponses;
import mouda.backend.moim.presentation.response.moim.MoimDetailsFindResponse;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponses;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;
	private final CommentRepository commentRepository;
	private final NotificationService notificationService;
	private final MoimWriter moimWriter;
	private final MoimFinder moimFinder;
	private final CommentFinder commentFinder;

	public Moim createMoim(Long darakbangId, DarakbangMember darakbangMember, MoimCreateRequest moimCreateRequest) {
		Moim moim = moimWriter.save(moimCreateRequest.toEntity(darakbangId), darakbangMember);

		notificationService.notifyToMembers(NotificationType.MOIM_CREATED, darakbangId, moim, darakbangMember);

		return moim;
	}

	@Transactional(readOnly = true)
	public MoimDetailsFindResponse findMoimDetails(long darakbangId, long moimId) {
		Moim moim = moimFinder.read(moimId, darakbangId);

		List<ParentComment> parentComments = commentFinder.readAllParentComments(moim);
		CommentResponses commentResponses = CommentResponses.toResponse(parentComments);

		return MoimDetailsFindResponse.toResponse(moim, moimFinder.countCurrentPeople(moim), commentResponses);
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findAllMoim(Long darakbangId, DarakbangMember darakbangMember) {
		List<MoimOverview> moimOverviews = moimFinder.readAll(darakbangId, darakbangMember);

		return MoimFindAllResponses.toResponse(moimOverviews);
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findAllMyMoim(DarakbangMember darakbangMember, FilterType filter) {
		List<MoimOverview> moimOverviews = moimFinder.readAllMyMoim(darakbangMember, filter);

		return MoimFindAllResponses.toResponse(moimOverviews);
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findZzimedMoim(DarakbangMember darakbangMember) {
		List<MoimOverview> moimOverviews = moimFinder.readAllZzimedMoim(darakbangMember);

		return MoimFindAllResponses.toResponse(moimOverviews);
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
}
