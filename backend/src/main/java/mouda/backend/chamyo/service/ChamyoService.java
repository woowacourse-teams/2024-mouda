package mouda.backend.chamyo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.dto.response.ChamyoFindAllResponse;
import mouda.backend.chamyo.dto.response.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.response.MoimRoleFindResponse;
import mouda.backend.chamyo.exception.ChamyoErrorMessage;
import mouda.backend.chamyo.exception.ChamyoException;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.common.RequiredDarakbangMoim;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.service.NotificationService;

@Service
@RequiredArgsConstructor
@Transactional
public class ChamyoService {

	@Value("${url.base}")
	private String baseUrl;

	@Value("${url.moim}")
	private String moimUrl;

	private final ChamyoRepository chamyoRepository;
	private final MoimRepository moimRepository;
	private final NotificationService notificationService;

	@Transactional(readOnly = true)
	@RequiredDarakbangMoim
	public MoimRoleFindResponse findMoimRole(Long darakbangId, Long moimId, DarakbangMember member) {
		Optional<Chamyo> chamyoOptional = chamyoRepository.findByMoimIdAndMemberId(moimId, member.getId());

		MoimRole moimRole = chamyoOptional.map(Chamyo::getMoimRole).orElse(MoimRole.NON_MOIMEE);

		return new MoimRoleFindResponse(moimRole.name());
	}

	@Transactional(readOnly = true)
	@RequiredDarakbangMoim
	public ChamyoFindAllResponses findAllChamyo(Long darakbangId, Long moimId) {
		List<ChamyoFindAllResponse> responses = chamyoRepository.findAllByMoimId(moimId)
			.stream()
			.map(ChamyoFindAllResponse::toResponse)
			.toList();

		return new ChamyoFindAllResponses(responses);
	}

	@RequiredDarakbangMoim
	public void chamyoMoim(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		validateCanChamyoMoim(moim, member);

		Chamyo chamyo = Chamyo.builder()
			.moim(moim)
			.member(member)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(chamyo);

		int currentPeople = chamyoRepository.countByMoim(moim);
		if (currentPeople >= moim.getMaxPeople()) {
			moimRepository.updateMoimStatusById(moim.getId(), MoimStatus.COMPLETED);
		}

		NotificationType notificationType = NotificationType.NEW_MOIMEE_JOINED;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(member.getNickname()))
			.targetUrl(baseUrl + moimUrl + "/" + moim.getId())
			.build();

		List<Long> membersToSendNotification = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.map(c -> c.getMember().getMember().getId())
			.filter(memberId -> !memberId.equals(member.getMember().getId()))
			.toList();

		notificationService.notifyToMembers(notification, membersToSendNotification, darakbangId);
	}

	private void validateCanChamyoMoim(Moim moim, DarakbangMember member) {
		int currentPeople = chamyoRepository.countByMoim(moim);
		if (currentPeople >= moim.getMaxPeople()) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_FULL);
		}
		if (moim.getMoimStatus() == MoimStatus.CANCELED) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIMING_CANCLED);
		}
		if (moim.getMoimStatus() == MoimStatus.COMPLETED) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIMING_COMPLETE);
		}
		if (chamyoRepository.existsByMoimIdAndMemberId(moim.getId(), member.getId())) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_ALREADY_JOINED);
		}
	}

	@RequiredDarakbangMoim
	public void cancelChamyo(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		validateCanCancelChamyo(moim, member);

		chamyoRepository.deleteByMoimIdAndMemberId(moim.getId(), member.getId());

		if (moim.getMoimStatus() != MoimStatus.COMPLETED) {
			return;
		}

		NotificationType notificationType = NotificationType.MOIMEE_LEFT;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(member.getNickname()))
			.targetUrl(baseUrl + moimUrl + "/" + moim.getId())
			.build();

		Long moimerId = chamyoRepository.findMoimerIdByMoimId(moim.getId());
		notificationService.notifyToMember(notification, moimerId, darakbangId);
	}

	private void validateCanCancelChamyo(Moim moim, DarakbangMember member) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndMemberId(moim.getId(), member.getId())
			.orElseThrow(() -> new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_JOINED))
			.getMoimRole();

		if (moimRole != MoimRole.MOIMEE) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.CANNOT_CANCEL_CHAMYO);
		}
	}
}
