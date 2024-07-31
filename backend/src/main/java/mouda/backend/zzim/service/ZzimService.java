package mouda.backend.zzim.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.zzim.dto.ZzimCheckResponse;
import mouda.backend.zzim.repository.ZzimRepository;

@Service
@RequiredArgsConstructor
public class ZzimService {

	private final ZzimRepository zzimRepository;

	public ZzimCheckResponse checkZzimByMember(Long moimId, Member member) {
		boolean isZzimed = zzimRepository.existsByMoimIdAndMemberId(moimId, member.getId());

		return new ZzimCheckResponse(isZzimed);
	}
}
