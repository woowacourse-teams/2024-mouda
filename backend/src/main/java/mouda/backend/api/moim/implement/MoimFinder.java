package mouda.backend.api.moim.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.api.moim.infrastructure.MoimRepository;
import mouda.backend.core.domain.moim.Moim;

@Component
@RequiredArgsConstructor
public class MoimFinder {

	private final MoimRepository moimRepository;

	public Moim find(long moimId, long darakbangId) {
		return moimRepository.findByMoimIdAndDarakbangId(moimId, darakbangId)
			.orElseThrow();
		// Moim moim = moimRepository.findById(moimId).orElseThrow();
		// moim.isNotInDarakbang(darakbangId);
		// return moim;
	}
}
