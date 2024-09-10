package mouda.backend.moim.implement.writer;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.validator.ChamyoValidator;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimWriter {

	private final MoimRepository moimRepository;
	private final MoimFinder moimFinder;
	private final ChamyoWriter chamyoWriter;
	private final ChamyoValidator chamyoValidator;

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

	public void confirmPlace(Moim moim, DarakbangMember darakbangMember, String place) {
		chamyoValidator.validateMoimer(moim, darakbangMember);
		moim.confirmPlace(place);
	}

	public void confirmDateTime(Moim moim, DarakbangMember darakbangMember, LocalDate date, LocalTime time) {
		chamyoValidator.validateMoimer(moim, darakbangMember);
		moim.confirmDateTime(date, time);
	}
}
