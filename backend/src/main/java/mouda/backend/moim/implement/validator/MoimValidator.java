package mouda.backend.moim.implement.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimValidator {

	private final MoimRepository moimRepository;
	private final ChamyoFinder chamyoFinder;
	private final MoimFinder moimFinder;

	public void validateMoimExists(long moimId, long darakbangId) {
		boolean isNotExists = !moimRepository.existsByIdAndDarakbangId(moimId, darakbangId);
		if (isNotExists) {
			throw new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND);
		}
	}

	public void validateCanCompleteMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimer(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_COMPLETE);
		validateMoimIsNotCompleted(moim);
		validateMoimIsNotCanceled(moim);
	}

	public void validateCanCancelMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimer(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_CANCEL);
		validateMoimIsNotMoiming(moim);
	}

	public void validateCanReopenMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimer(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_REOPEN);
		validateMoimIsNotFull(moim);
		validateMoimIsNotCanceled(moim);
		validateMoimIsNotMoiming(moim);
	}

	public void validateCanEditMoim(Moim moim, DarakbangMember darakbangMember) {
		validateIsMoimer(moim, darakbangMember, MoimErrorMessage.NOT_ALLOWED_TO_EDIT);
		validateMoimIsNotCompleted(moim);
		validateMoimIsNotCanceled(moim);
	}

	private void validateMoimIsNotFull(Moim moim) {
		if (moim.isFull(moimFinder.countCurrentPeople(moim))) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_FULL_FOR_REOPEN);
		}
	}

	public void validateIsMoimer(Moim moim, DarakbangMember darakbangMember, MoimErrorMessage expectedErrorMessage) {
		MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangMember);

		if (moimRole != MoimRole.MOIMER) {
			throw new MoimException(HttpStatus.FORBIDDEN, expectedErrorMessage);
		}
	}

	private void validateMoimIsNotCanceled(Moim moim) {
		if (moim.isCanceled()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MOIM_CANCELED);
		}
	}

	private void validateMoimIsNotCompleted(Moim moim) {
		if (moim.isCompleted()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_COMPLETED);
		}
	}

	private void validateMoimIsNotMoiming(Moim moim) {
		if (moim.isMoiming()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.ALREADY_MOIMING);
		}
	}
}
