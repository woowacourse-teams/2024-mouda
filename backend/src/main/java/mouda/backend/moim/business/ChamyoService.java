package mouda.backend.moim.business;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.event.ChamyoEvent;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.sender.ChamyoNotificationSender;
import mouda.backend.moim.implement.writer.ChamyoWriter;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.moim.presentation.response.chamyo.ChamyoFindAllResponses;
import mouda.backend.moim.presentation.response.chamyo.MoimRoleFindResponse;
import mouda.backend.notification.domain.NotificationType;

@Service
@RequiredArgsConstructor
@Transactional
public class ChamyoService {

	private final MoimFinder moimFinder;
	private final MoimWriter moimWriter;
	private final ChamyoFinder chamyoFinder;
	private final ChamyoWriter chamyoWriter;
	private final ChamyoNotificationSender chamyoNotificationSender;

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
		Chamyo chamyo = chamyoWriter.saveAsMoimee(moim, darakbangMember);
		moimWriter.updateMoimStatusIfFull(moim);

		chamyoNotificationSender.sendChamyoNotification(moimId, darakbangMember, NotificationType.NEW_MOIMEE_JOINED);
	}

	public void cancelChamyo(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		Chamyo chamyo = chamyoFinder.read(moim, darakbangMember);
		chamyoWriter.delete(chamyo);

		chamyoNotificationSender.sendChamyoNotification(moimId, darakbangMember, NotificationType.MOIMEE_LEFT);
	}
}
