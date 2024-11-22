package mouda.backend.please.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;
import mouda.backend.please.implement.InterestWriter;
import mouda.backend.please.implement.PleaseFinder;
import mouda.backend.please.presentation.request.InterestUpdateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

	private final PleaseFinder pleaseFinder;
	private final InterestWriter interestWriter;

	public void updateInterest(Long darakbangId, DarakbangMember darakbangMember, InterestUpdateRequest request) {
		Please please = pleaseFinder.find(request.pleaseId(), darakbangId);
		interestWriter.changeInterest(please, request.isInterested(), darakbangMember);
	}
}
