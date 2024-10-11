package mouda.backend.moim.implement.writer;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.validator.ChamyoValidator;
import mouda.backend.moim.implement.validator.MoimValidator;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimWriter {

	private final MoimRepository moimRepository;
	private final MoimValidator moimValidator;
	private final ChamyoWriter chamyoWriter;
	private final MoimFinder moimFinder;
	private final ChamyoValidator chamyoValidator;
	private final ChamyoRepository chamyoRepository;

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

	public void confirmPlace(Moim moim, DarakbangMember darakbangMember, String place) {
		chamyoValidator.validateMoimer(moim, darakbangMember);
		moim.confirmPlace(place);
	}

	public void confirmDateTime(Moim moim, DarakbangMember darakbangMember, LocalDate date, LocalTime time) {
		chamyoValidator.validateMoimer(moim, darakbangMember);
		moim.confirmDateTime(date, time);
	}

	public void openChatByMoimer(Moim moim, DarakbangMember darakbangMember) {
		chamyoValidator.validateMoimer(moim, darakbangMember);
		moim.openChat();
	}

	public void confirmPlace(long targetId, DarakbangMember darakbangMember, String place) {
		Chamyo chamyo = readChamyo(targetId, darakbangMember);

		Moim moim = chamyo.getMoim();
		moim.confirmPlace(place);
	}

	public void confirmDateTime(long targetId, DarakbangMember darakbangMember, LocalDate date, LocalTime time) {
		Chamyo chamyo = readChamyo(targetId, darakbangMember);

		Moim moim = chamyo.getMoim();
		moim.confirmDateTime(date, time);
	}

	private Chamyo readChamyo(long targetId, DarakbangMember darakbangMember) {
		Chamyo chamyo = chamyoRepository.findByMoimIdAndDarakbangMemberId(
				targetId, darakbangMember.getId())
			.orElseThrow(() -> new ChatException(HttpStatus.UNAUTHORIZED, ChatErrorMessage.UNAUTHORIZED));

		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.UNAUTHORIZED, ChatErrorMessage.UNAUTHORIZED_MOIMER);
		}
		return chamyo;
	}
}
