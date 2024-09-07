package mouda.backend.api.moim.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.api.moim.infrastructure.ChamyoRepository;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Chamyo;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.core.domain.moim.MoimRole;

@Component
@RequiredArgsConstructor
public class ChamyoFinder {

	private final ChamyoValidator chamyoValidator;
	private final ChamyoRepository chamyoRepository;

	public Chamyo read(Moim moim, DarakbangMember darakbangMember) {
		Chamyo chamyo = find(moim.getId(), darakbangMember).orElseThrow();
		chamyoValidator.validateInDarakbang(chamyo.getMoim(), darakbangMember.getDarakbang().getId());
		return chamyo;
	}

	private Optional<Chamyo> find(long moimId, DarakbangMember darakbangMember) {
		return chamyoRepository.findByMoimIdAndDarakbangMemberId(moimId,
			darakbangMember.getId());
	}

	public MoimRole readMoimRole(Moim moim, DarakbangMember darakbangMember) {
		Optional<Chamyo> chamyo = find(moim.getId(), darakbangMember);
		if (chamyo.isPresent()) {
			return chamyo.get().getMoimRole();
		}
		return MoimRole.NON_MOIMEE;
	}
}
