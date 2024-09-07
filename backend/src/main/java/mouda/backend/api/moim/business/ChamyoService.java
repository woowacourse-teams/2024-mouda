package mouda.backend.api.moim.business;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.api.moim.exception.ChamyoErrorMessage;
import mouda.backend.api.moim.exception.ChamyoException;
import mouda.backend.api.moim.implement.ChamyoFinder;
import mouda.backend.api.moim.implement.MoimFinder;
import mouda.backend.api.moim.infrastructure.ChamyoRepository;
import mouda.backend.api.moim.infrastructure.MoimRepository;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Chamyo;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.core.domain.moim.MoimRole;
import mouda.backend.core.domain.moim.MoimStatus;
import mouda.backend.core.dto.moim.response.chamyo.ChamyoFindAllResponse;
import mouda.backend.core.dto.moim.response.chamyo.ChamyoFindAllResponses;
import mouda.backend.core.dto.moim.response.chamyo.MoimRoleFindResponse;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Service
@RequiredArgsConstructor
@Transactional
public class ChamyoService {

	private final ChamyoRepository chamyoRepository;
	private final MoimRepository moimRepository;
	private final NotificationService notificationService;
	private final ChamyoFinder chamyoFinder;
	private final MoimFinder moimFinder;

	@Transactional(readOnly = true)
	public MoimRoleFindResponse findMoimRole(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		/*
		모임이 다락방에 있는지 확인한다.
		모임에서 자기역할을 찾는다.
			멤버가 모임에 참여하고 있는지 검증한다. -> 침여가 필요하다.
		참여에서 역할을 반환한다.
		 */
		Moim moim = moimFinder.find(moimId, darakbangId);
		MoimRole moimRole = chamyoFinder.readMoimRole(moim, darakbangMember);

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
