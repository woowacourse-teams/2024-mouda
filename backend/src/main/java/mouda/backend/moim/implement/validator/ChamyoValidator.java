package mouda.backend.moim.implement.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.ChamyoErrorMessage;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Component
@RequiredArgsConstructor
public class ChamyoValidator {

	private final MoimValidator moimValidator;
	private final MoimFinder moimFinder;
	private final ChamyoRepository chamyoRepository;
	private final ChamyoFinder chamyoFinder;

	public void validateCanParticipate(Moim moim, DarakbangMember darakbangMember) {
		moimValidator.validateMoimExists(moim.getId(), darakbangMember.getDarakbang().getId());
		validateMoimIsParticipable(moim);
		validateAlreadyParticipated(moim, darakbangMember);
	}

	private void validateMoimIsParticipable(Moim moim) {
		int currentPeople = moimFinder.countCurrentPeople(moim);
		if (moim.isFull(currentPeople)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_FULL);
		}
		if (moim.isCanceled()) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIMING_CANCLED);
		}
		if (moim.isCompleted()) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIMING_COMPLETE);
		}
	}

	private void validateAlreadyParticipated(Moim moim, DarakbangMember darakbangMember) {
		boolean isChamyoExists = chamyoRepository.existsByMoimIdAndDarakbangMemberId(
			moim.getId(), darakbangMember.getId()
		);
		if (isChamyoExists) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.ALREADY_PARTICIPATED);
		}
	}

	public void validateCanCancel(Moim moim, DarakbangMember darakbangMember) {
		MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangMember);
		if (moimRole != MoimRole.MOIMEE) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.CANNOT_CANCEL_CHAMYO);

		}
	}
}
