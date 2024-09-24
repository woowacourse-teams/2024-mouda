package mouda.backend.moim.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.finder.ZzimFinder;
import mouda.backend.moim.implement.validator.MoimValidator;
import mouda.backend.moim.implement.writer.ZzimWriter;
import mouda.backend.moim.presentation.response.zzim.ZzimCheckResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class ZzimService {

	private final MoimValidator moimValidator;
	private final MoimFinder moimFinder;
	private final ZzimFinder zzimFinder;
	private final ZzimWriter zzimWriter;

	@Transactional(readOnly = true)
	public ZzimCheckResponse checkZzimByMember(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		moimValidator.validateMoimExists(moimId, darakbangId);
		boolean moimZzimedByMember = zzimFinder.isMoimZzimedByMember(moimId, darakbangMember);

		return ZzimCheckResponse.toResponse(moimZzimedByMember);
	}

	public void updateZzim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
 		zzimWriter.updateZzimStatus(moim, darakbangMember);
	}
}
