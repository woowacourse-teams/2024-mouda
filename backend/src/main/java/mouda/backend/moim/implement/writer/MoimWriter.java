package mouda.backend.moim.implement.writer;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimWriter {

	private final MoimRepository moimRepository;
	private final MoimFinder moimFinder;
	private final ChamyoWriter chamyoWriter;

	public Moim save(Moim moim, DarakbangMember darakbangMember) {
		Moim saved = moimRepository.save(moim);
		chamyoWriter.saveAsMoimer(saved, darakbangMember);

		return saved;
	}

	public void updateMoimStatusIfFull(Moim moim) {
		int currentPeople = moimFinder.countCurrentPeople(moim);
		if (moim.isFull(currentPeople)) {
			moim.complete();
		}
	}
}
