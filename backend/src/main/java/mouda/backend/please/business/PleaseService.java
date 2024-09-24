package mouda.backend.please.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;
import mouda.backend.please.domain.PleaseWithInterests;
import mouda.backend.please.implement.PleaseFinder;
import mouda.backend.please.implement.PleaseValidator;
import mouda.backend.please.implement.PleaseWriter;
import mouda.backend.please.presentation.request.PleaseCreateRequest;
import mouda.backend.please.presentation.response.PleaseFindAllResponses;

@Service
@Transactional
@RequiredArgsConstructor
public class PleaseService {

	private final PleaseWriter pleaseWriter;
	private final PleaseFinder pleaseFinder;
	private final PleaseValidator pleaseValidator;

	public Please createPlease(Long darakbangId, DarakbangMember darakbangMember,
		PleaseCreateRequest pleaseCreateRequest) {
		Please please = pleaseCreateRequest.toEntity(darakbangMember.getId(), darakbangId);

		return pleaseWriter.savePlease(please);
	}

	public void deletePlease(Long darakbangId, Long pleaseId, DarakbangMember darakbangMember) {
		Please please = pleaseFinder.find(pleaseId, darakbangId);
		pleaseValidator.validate(please, darakbangId, darakbangMember);
		pleaseWriter.delete(pleaseId);
	}

	public PleaseFindAllResponses findAllPlease(Long darakbangId, DarakbangMember darakbangMember) {
		PleaseWithInterests pleaseWithInterests = pleaseFinder.findPleasesDesc(darakbangId, darakbangMember);

		return PleaseFindAllResponses.toResponse(pleaseWithInterests);
	}
}
