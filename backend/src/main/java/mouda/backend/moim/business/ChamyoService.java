package mouda.backend.moim.business;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.exception.ChamyoErrorMessage;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.presentation.response.chamyo.ChamyoFindAllResponse;
import mouda.backend.moim.presentation.response.chamyo.ChamyoFindAllResponses;
import mouda.backend.moim.presentation.response.chamyo.MoimRoleFindResponse;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;

@Service
@RequiredArgsConstructor
@Transactional
public class ChamyoService {

	private final ChamyoRepository chamyoRepository;
	private final MoimRepository moimRepository;
	private final NotificationService notificationService;

	@Transactional(readOnly = true)
	public MoimRoleFindResponse findMoimRole(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Optional<Chamyo> chamyoOptional = chamyoRepository.findByMoimIdAndDarakbangMemberId(moimId,
			darakbangMember.getId());
		chamyoOptional.ifPresent(chamyo -> {
			if (chamyo.getMoim().isNotInDarakbang(darakbangId)) {
				throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_FOUND);
			}
		});

		MoimRole moimRole = chamyoOptional.map(Chamyo::getMoimRole).orElse(MoimRole.NON_MOIMEE);

		return new MoimRoleFindResponse(moimRole.name());
	}

	@Transactional(readOnly = true)
	public ChamyoFindAllResponses findAllChamyo(Long darakbangId, Long moimId) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_FOUND);
		}
		List<ChamyoFindAllResponse> responses = chamyoRepository.findAllByMoimId(moimId)
			.stream()
			.map(ChamyoFindAllResponse::toResponse)
			.toList();

		return new ChamyoFindAllResponses(responses);
	}

	public void chamyoMoim(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findByIdForUpdate(moimId)
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_FOUND);
		}
		validateCanChamyoMoim(moim, darakbangMember);

		Chamyo chamyo = Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangMember)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(chamyo);

		int currentPeople = chamyoRepository.countByMoim(moim);
		if (currentPeople >= moim.getMaxPeople()) {
			moimRepository.updateMoimStatusById(moim.getId(), MoimStatus.COMPLETED);
		}

		notificationService.notifyToMembers(NotificationType.NEW_MOIMEE_JOINED, darakbangId, moim, darakbangMember);
	}

	private void validateCanChamyoMoim(Moim moim, DarakbangMember darakbangMember) {
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
		if (chamyoRepository.existsByMoimIdAndDarakbangMemberId(moim.getId(), darakbangMember.getId())) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_ALREADY_JOINED);
		}
	}

	public void cancelChamyo(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_FOUND);
		}
		validateCanCancelChamyo(moim, darakbangMember);

		chamyoRepository.deleteByMoimIdAndDarakbangMemberId(moim.getId(), darakbangMember.getId());

		if (moim.getMoimStatus() != MoimStatus.COMPLETED) {
			return;
		}

		notificationService.notifyToMembers(NotificationType.MOIMEE_LEFT, darakbangId, moim, darakbangMember);
	}

	private void validateCanCancelChamyo(Moim moim, DarakbangMember darakbangMember) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndDarakbangMemberId(moim.getId(), darakbangMember.getId())
			.orElseThrow(() -> new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_JOINED))
			.getMoimRole();

		if (moimRole != MoimRole.MOIMEE) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.CANNOT_CANCEL_CHAMYO);
		}
	}
}
