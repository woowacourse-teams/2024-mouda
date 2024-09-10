package mouda.backend.moim.implement.writer;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.validator.MoimValidator;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimWriter {

	private final MoimRepository moimRepository;
	private final MoimValidator moimValidator;
	private final ChamyoWriter chamyoWriter;
	private final MoimFinder moimFinder;

	public Moim save(Moim moim, DarakbangMember darakbangMember) {
		Moim saved = moimRepository.save(moim);
		chamyoWriter.saveAsMoimer(saved, darakbangMember);

		return saved;
	}

	public void updateMoimStatusIfFull(Moim moim) {
		if (moim.isFull(moimFinder.countCurrentPeople(moim))) {
			moim.complete();
		}
	}

	public void completeMoim(Moim moim, DarakbangMember darakbangMember) {
		moimValidator.validateCanCompleteMoim(moim, darakbangMember);
		moim.complete();
	}

	public void cancelMoim(Moim moim, DarakbangMember darakbangMember) {
		moimValidator.validateCanCancelMoim(moim, darakbangMember);
		moim.cancel();
	}

	public void reopenMoim(Moim moim, DarakbangMember darakbangMember) {
		moimValidator.validateCanReopenMoim(moim, darakbangMember);
		moim.reopen();
	}

	public void updateMoim(
		Moim moim, DarakbangMember darakbangMember,
		String newTitle, LocalDate newDate, LocalTime newTime,
		String newPlace, int newMaxPeople, String newDescription
	) {
		moimValidator.validateCanEditMoim(moim, darakbangMember);
		moim.update(newTitle, newDate, newTime, newPlace, newMaxPeople, newDescription,
			moimFinder.countCurrentPeople(moim));
	}
}
