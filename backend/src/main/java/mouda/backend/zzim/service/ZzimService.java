package mouda.backend.zzim.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.zzim.domain.Zzim;
import mouda.backend.zzim.dto.ZzimCheckResponse;
import mouda.backend.zzim.dto.request.ZzimUpdateRequest;
import mouda.backend.zzim.exception.ZzimErrorMessage;
import mouda.backend.zzim.exception.ZzimException;
import mouda.backend.zzim.repository.ZzimRepository;

@Service
@RequiredArgsConstructor
public class ZzimService {

	private final ZzimRepository zzimRepository;
	private final MoimRepository moimRepository;

	public ZzimCheckResponse checkZzimByMember(Long moimId, Member member) {
		boolean isZzimed = zzimRepository.existsByMoimIdAndMemberId(moimId, member.getId());

		return new ZzimCheckResponse(isZzimed);
	}

	public void updateZzim(ZzimUpdateRequest request, Member member) {
		Moim moim = moimRepository.findById(request.moimId())
			.orElseThrow(() -> new ZzimException(HttpStatus.NOT_FOUND, ZzimErrorMessage.MOIN_NOT_FOUND));

		zzimRepository.findByMoimIdAndMemberId(request.moimId(), member.getId())
			.ifPresentOrElse(
				zzimRepository::delete,
				() -> zzimRepository.save(Zzim.builder().moim(moim).member(member).build())
			);
	}
}
