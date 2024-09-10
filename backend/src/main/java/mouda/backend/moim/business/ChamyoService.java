package mouda.backend.moim.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.writer.ChamyoWriter;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.moim.presentation.response.chamyo.ChamyoFindAllResponses;
import mouda.backend.moim.presentation.response.chamyo.MoimRoleFindResponse;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Service
@RequiredArgsConstructor
@Transactional
public class ChamyoService {

	private final MoimFinder moimFinder;
	private final MoimWriter moimWriter;
	private final ChamyoFinder chamyoFinder;
	private final ChamyoWriter chamyoWriter;
	private final NotificationService notificationService;

	@Transactional(readOnly = true)
	public MoimRoleFindResponse findMoimRole(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangMember);

		return MoimRoleFindResponse.toResponse(moimRole);
	}

	@Transactional(readOnly = true)
	public ChamyoFindAllResponses findAllChamyo(Long darakbangId, Long moimId) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		List<Chamyo> chamyos = chamyoFinder.readAll(moim);

		return ChamyoFindAllResponses.toResponse(chamyos);
	}

	public void chamyoMoim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		chamyoWriter.saveAsMoimee(moim, darakbangMember);
		moimWriter.updateMoimStatusIfFull(moim);

		notificationService.notifyToMembers(NotificationType.NEW_MOIMEE_JOINED, darakbangId, moim, darakbangMember);
	}

	public void cancelChamyo(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		chamyoWriter.delete(moim, darakbangMember);

		sendCancelNotification(darakbangId, darakbangMember, moim);
	}

	private void sendCancelNotification(Long darakbangId, DarakbangMember darakbangMember, Moim moim) {
		if (moim.isCompleted()) {
			notificationService.notifyToMembers(NotificationType.MOIMEE_LEFT, darakbangId, moim, darakbangMember);
		}
	}
}
