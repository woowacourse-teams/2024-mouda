package mouda.backend.moim.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.exception.ZzimErrorMessage;
import mouda.backend.moim.exception.ZzimException;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.finder.ZzimFinder;
import mouda.backend.moim.implement.validator.MoimValidator;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.infrastructure.ZzimRepository;
import mouda.backend.moim.presentation.response.zzim.ZzimCheckResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzimService {

	private final MoimValidator moimValidator;
	private final ZzimFinder zzimFinder;
	private final ZzimRepository zzimRepository;
	private final MoimRepository moimRepository;

	@Transactional(readOnly = true)
	public ZzimCheckResponse checkZzimByMember(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		moimValidator.validateMoimExists(moimId, darakbangId);
		boolean moimZzimedByMember = zzimFinder.isMoimZzimedByMember(moimId, darakbangMember);

		return ZzimCheckResponse.toResponse(moimZzimedByMember);
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
