package mouda.backend.api.moim.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.core.domain.moim.Zzim;
import mouda.backend.api.moim.exception.ZzimErrorMessage;
import mouda.backend.api.moim.exception.ZzimException;
import mouda.backend.api.moim.infrastructure.MoimRepository;
import mouda.backend.api.moim.infrastructure.ZzimRepository;
import mouda.backend.core.dto.moim.response.zzim.ZzimCheckResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzimService {

	private final ZzimRepository zzimRepository;
	private final MoimRepository moimRepository;

	@Transactional(readOnly = true)
	public ZzimCheckResponse checkZzimByMember(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ZzimException(HttpStatus.NOT_FOUND, ZzimErrorMessage.MOIN_NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ZzimException(HttpStatus.BAD_REQUEST, ZzimErrorMessage.MOIN_NOT_FOUND);
		}

		boolean isZzimed = zzimRepository.existsByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());

		return new ZzimCheckResponse(isZzimed);
	}

	public void updateZzim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ZzimException(HttpStatus.NOT_FOUND, ZzimErrorMessage.MOIN_NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ZzimException(HttpStatus.BAD_REQUEST, ZzimErrorMessage.MOIN_NOT_FOUND);
		}

		zzimRepository.findByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId())
			.ifPresentOrElse(
				zzimRepository::delete,
				() -> zzimRepository.save(Zzim.builder().moim(moim).darakbangMember(darakbangMember).build())
			);
	}
}
