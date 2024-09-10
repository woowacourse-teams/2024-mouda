package mouda.backend.moim.implement.finder;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.infrastructure.ZzimRepository;

@Component
@RequiredArgsConstructor
public class ZzimFinder {

	private final ZzimRepository zzimRepository;

	public Optional<Zzim> find(long moimId, DarakbangMember darakbangMember) {
		return zzimRepository.findByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());
	}

	public boolean isMoimZzimedByMember(long moimId, DarakbangMember darakbangMember) {
		return zzimRepository.existsByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());
	}
}
