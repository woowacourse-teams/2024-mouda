package mouda.backend.moim.implement.finder;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.infrastructure.ZzimRepository;

@Component
@RequiredArgsConstructor
public class ZzimFinder {

	private final ZzimRepository zzimRepository;

	public boolean isMoimZzimedByMember(long moimId, DarakbangMember darakbangMember) {
		return zzimRepository.existsByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());
	}
}
