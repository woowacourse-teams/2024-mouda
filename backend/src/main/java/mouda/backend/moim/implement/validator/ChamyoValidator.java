package mouda.backend.moim.implement.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
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

	private final MoimFinder moimFinder;
	private final ChamyoRepository chamyoRepository;
	private final ChamyoFinder chamyoFinder;

	public void validateCanParticipate(Moim moim, DarakbangMember darakbangMember) {
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

	public void validateCanCancel(Chamyo chamyo) {
		if (chamyo.getMoimRole() != MoimRole.MOIMEE) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.CANNOT_CANCEL_CHAMYO);
		}
	}

	public void validateMoimer(Moim moim, DarakbangMember darakbangMember) {
		Chamyo chamyo = chamyoFinder.read(moim, darakbangMember);
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.NOT_MOIMER);
		}
	}

	public void validateMemberChamyoMoim(Moim moim, DarakbangMember darakbangMember) {
		if (!chamyoFinder.exists(moim.getId(), darakbangMember)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.NOT_FOUND);
		}
	}
}
