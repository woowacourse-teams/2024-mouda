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

@Component
@RequiredArgsConstructor
public class ChamyoValidator {

	private final ChamyoFinder chamyoFinder;

	public void validateMoimer(Moim moim, DarakbangMember darakbangMember) {
		Chamyo chamyo = chamyoFinder.read(moim, darakbangMember);
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.NOT_MOIMER);
		}
	}

	public void exists(Long moimId, DarakbangMember darakbangMember) {
		if (!chamyoFinder.exists(moimId, darakbangMember)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.NOT_FOUND);
		}
	}
}
