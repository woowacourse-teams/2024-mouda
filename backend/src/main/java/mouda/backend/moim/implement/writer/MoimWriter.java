package mouda.backend.moim.implement.writer;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;

@Component
@RequiredArgsConstructor
public class MoimWriter {

	private final MoimFinder moimFinder;

	public void updateMoimStatusIfFull(Moim moim) {
		int currentPeople = moimFinder.countCurrentPeople(moim);
		if (moim.isFull(currentPeople)) {
			moim.complete();
		}
	}
}
