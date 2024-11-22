package mouda.backend.moim.implement.writer;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.finder.ZzimFinder;
import mouda.backend.moim.infrastructure.ZzimRepository;

@Component
@RequiredArgsConstructor
public class ZzimWriter {

	private final ZzimRepository zzimRepository;
	private final ZzimFinder zzimFinder;

	public void updateZzimStatus(Moim moim, DarakbangMember darakbangMember) {
		zzimFinder.find(moim.getId(), darakbangMember).ifPresentOrElse(
			this::delete,
			() -> save(moim, darakbangMember)
		);
	}

	public Zzim save(Moim moim, DarakbangMember darakbangMember) {
		Zzim zzim = Zzim.builder()
			.moim(moim)
			.darakbangMember(darakbangMember)
			.build();
		return zzimRepository.save(zzim);
	}

	public void delete(Zzim zzim) {
		zzimRepository.delete(zzim);
	}
}
