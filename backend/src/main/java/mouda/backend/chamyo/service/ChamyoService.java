package mouda.backend.chamyo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.dto.ChamyoFindAllResponse;
import mouda.backend.chamyo.dto.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.MoimRoleFindResponse;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class ChamyoService {

	private final ChamyoRepository chamyoRepository;

	public MoimRoleFindResponse findMoimRole(Long moimId, Member member) {
		Optional<Chamyo> chamyoOptional = chamyoRepository.findByMoimIdAndMemberId(moimId, member.getId());

		MoimRole moimRole = chamyoOptional.map(Chamyo::getMoimRole).orElse(MoimRole.NON_MOIMEE);

		return new MoimRoleFindResponse(moimRole.name());
	}

	public ChamyoFindAllResponses findAllChamyo(Long moimId) {
		List<ChamyoFindAllResponse> responses = chamyoRepository.findAllByMoimId(moimId).stream()
			.map(ChamyoFindAllResponse::toResponse)
			.toList();

		return new ChamyoFindAllResponses(responses);
	}
}
