package mouda.backend.moim.implement.writer;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.ChamyoErrorMessage;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.implement.validator.ChamyoValidator;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Component
@RequiredArgsConstructor
public class ChamyoWriter {

	private final ChamyoValidator chamyoValidator;
	private final ChamyoRepository chamyoRepository;

	public Chamyo saveAsMoimer(Moim moim, DarakbangMember darakbangMember) {
		return save(moim, darakbangMember, MoimRole.MOIMER);
	}

	public Chamyo saveAsMoimee(Moim moim, DarakbangMember darakbangMember) {
		return save(moim, darakbangMember, MoimRole.MOIMEE);
	}

	private Chamyo save(Moim moim, DarakbangMember darakbangMember, MoimRole moimRole) {
		chamyoValidator.validateCanParticipate(moim, darakbangMember);

		Chamyo chamyo = Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangMember)
			.moimRole(moimRole)
			.build();

		try {
			return chamyoRepository.save(chamyo);
		} catch (DataIntegrityViolationException exception) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.ALREADY_PARTICIPATED);
		}
	}

	public void delete(Chamyo chamyo) {
		chamyoValidator.validateCanCancel(chamyo);
		chamyoRepository.delete(chamyo);
	}
}
