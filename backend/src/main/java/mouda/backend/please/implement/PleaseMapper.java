package mouda.backend.please.implement;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;
import mouda.backend.please.presentation.response.PleaseFindAllResponse;
import mouda.backend.please.presentation.response.PleaseFindAllResponses;

@Component
@RequiredArgsConstructor
public class PleaseMapper {

	private final InterestFinder interestFinder;

	public PleaseFindAllResponses toAllPleaseResponse(List<Please> pleases, DarakbangMember darakbangMember) {
		return PleaseFindAllResponses.toResponse(
			pleases.stream()
				.map(please -> {
					boolean isInterested = interestFinder.findInterest(darakbangMember.getId(), please.getId())
						.isPresent();
					long interestCount = interestFinder.countInterest(please);
					return PleaseFindAllResponse.toResponse(please, isInterested, interestCount);
				})
				.sorted(Comparator.comparing(PleaseFindAllResponse::interestCount).reversed()
					.thenComparing(PleaseFindAllResponse::pleaseId))
				.toList());
	}
}
