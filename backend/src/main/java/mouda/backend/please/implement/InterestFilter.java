package mouda.backend.please.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;

@Component
@RequiredArgsConstructor
public class InterestFilter {

	private final InterestValidator interestValidator;
	private final InterestWriter interestWriter;

	public void changeInterest(Please please, Long darakbangId, boolean isInterested, DarakbangMember darakbangMember) {
		if (isInterested) {
			interestValidator.validate(please, darakbangId, isInterested, darakbangMember);
			return;
		}
		interestWriter.removeInterest(darakbangMember.getMemberId(), please.getId());
	}
}
