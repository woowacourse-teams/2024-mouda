package mouda.backend.please.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.please.presentation.response.PleaseFindAllResponse;

@Getter
@RequiredArgsConstructor
public class PleaseWithInterests {

	private final List<PleaseWithInterest> pleaseWithInterests;

	public List<PleaseFindAllResponse> getPleaseFindAll() {
		return pleaseWithInterests.stream()
			.map(pleaseWithInterest -> PleaseFindAllResponse.toResponse(pleaseWithInterest.getPlease(),
				pleaseWithInterest.isInterested(), pleaseWithInterest.getInterestCount()))
			.toList();
	}
}
