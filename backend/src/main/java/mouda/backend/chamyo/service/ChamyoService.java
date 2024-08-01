package mouda.backend.chamyo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.dto.ChamyoFindAllResponse;
import mouda.backend.chamyo.dto.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.MoimChamyoRequest;
import mouda.backend.chamyo.dto.MoimRoleFindResponse;
import mouda.backend.chamyo.exception.ChamyoErrorMessage;
import mouda.backend.chamyo.exception.ChamyoException;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.repository.MoimRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ChamyoService {

	private final ChamyoRepository chamyoRepository;
	private final MoimRepository moimRepository;

	@Transactional(readOnly = true)
	public MoimRoleFindResponse findMoimRole(Long moimId, Member member) {
		Optional<Chamyo> chamyoOptional = chamyoRepository.findByMoimIdAndMemberId(moimId, member.getId());

		MoimRole moimRole = chamyoOptional.map(Chamyo::getMoimRole).orElse(MoimRole.NON_MOIMEE);

		return new MoimRoleFindResponse(moimRole.name());
	}

	@Transactional(readOnly = true)
	public ChamyoFindAllResponses findAllChamyo(Long moimId) {
		List<ChamyoFindAllResponse> responses = chamyoRepository.findAllByMoimId(moimId).stream()
			.map(ChamyoFindAllResponse::toResponse)
			.toList();

		return new ChamyoFindAllResponses(responses);
	}

	public void chamyoMoim(MoimChamyoRequest request, Member member) {
		// 모임 조회 및 참여 가능 여부 확인
		Moim moim = moimRepository.findById(request.moimId())
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		validateCanChamyoMoim(moim, member);

		// 참여 처리
		Chamyo chamyo = Chamyo.builder()
			.moim(moim)
			.member(member)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(chamyo);

		// 현재 참여로 인해 모임 인원이 꽉 찬 경우 모임 상태를 변경
		updateMoimStatus(moim);
	}

	private void validateCanChamyoMoim(Moim moim, Member member) {
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
		if (chamyoRepository.existsByMoimAndMember(moim, member)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_ALREADY_JOINED);
		}
	}

	private void updateMoimStatus(Moim moim) {
		int currentPeople = chamyoRepository.countByMoim(moim);
		if (currentPeople >= moim.getMaxPeople()) {
			moimRepository.updateMoimStatusById(moim.getId(), MoimStatus.COMPLETED);
		}
	}
}
