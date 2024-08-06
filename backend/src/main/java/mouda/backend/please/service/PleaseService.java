package mouda.backend.please.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.response.PleaseFindAllResponse;
import mouda.backend.please.dto.response.PleaseFindAllResponses;
import mouda.backend.please.repository.InterestRepository;
import mouda.backend.please.repository.PleaseRepository;

@Service
@RequiredArgsConstructor
public class PleaseService {

	private final PleaseRepository pleaseRepository;
	private final InterestRepository interestRepository;

	public PleaseFindAllResponses findAllPlease(Member member) {
		List<Please> pleases = pleaseRepository.findAll();

		return new PleaseFindAllResponses(
			pleases.stream()
				.map(please -> {
					boolean isInterested = interestRepository.existsByMemberId(member.getId());
					long interestCount = interestRepository.countByPleaseId(please.getId());
					return PleaseFindAllResponse.toResponse(please, isInterested, interestCount);
				})
				.toList());
	}
}
