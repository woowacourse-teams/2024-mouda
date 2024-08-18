package mouda.backend.zzim.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RequiredDarakbangMoim;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.zzim.domain.Zzim;
import mouda.backend.zzim.dto.response.ZzimCheckResponse;
import mouda.backend.zzim.exception.ZzimErrorMessage;
import mouda.backend.zzim.exception.ZzimException;
import mouda.backend.zzim.repository.ZzimRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzimService {

	private final ZzimRepository zzimRepository;
	private final MoimRepository moimRepository;

	@Transactional(readOnly = true)
	@RequiredDarakbangMoim
	public ZzimCheckResponse checkZzimByMember(Long darakbangId, Long moimId, DarakbangMember member) {
		boolean isZzimed = zzimRepository.existsByMoimIdAndMemberId(moimId, member.getId());

		return new ZzimCheckResponse(isZzimed);
	}

	@RequiredDarakbangMoim
	public void updateZzim(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ZzimException(HttpStatus.NOT_FOUND, ZzimErrorMessage.MOIN_NOT_FOUND));

		zzimRepository.findByMoimIdAndMemberId(moimId, member.getId())
			.ifPresentOrElse(
				zzimRepository::delete,
				() -> zzimRepository.save(Zzim.builder().moim(moim).member(member).build())
			);
	}
}
