package mouda.backend.chamyo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.dto.request.ChamyoCancelRequest;
import mouda.backend.chamyo.dto.request.MoimChamyoRequest;
import mouda.backend.chamyo.dto.response.ChamyoFindAllResponse;
import mouda.backend.chamyo.dto.response.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.response.MoimRoleFindResponse;
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
		Moim moim = moimRepository.findById(request.moimId())
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

	public void cancelChamyo(ChamyoCancelRequest request, Member member) {
		Moim moim = moimRepository.findById(request.moimId())
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_FOUND));
		validateCanCancelChamyo(moim, member);

		chamyoRepository.deleteByMoimAndMember(moim, member);
	}

	private void validateCanCancelChamyo(Moim moim, Member member) {
		MoimRole moimRole = chamyoRepository.findByMoimIdAndMemberId(moim.getId(), member.getId())
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.MOIM_NOT_JOINED))
			.getMoimRole();

		if (moimRole != MoimRole.MOIMEE) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.CANNOT_CANCEL_CHAMYO);
		}
	}
}
