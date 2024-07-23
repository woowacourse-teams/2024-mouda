package mouda.backend.moim.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.repository.MoimRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	private final MoimRepository moimRepository;

	private final MemberRepository memberRepository;

	public Moim createMoim(MoimCreateRequest moimCreateRequest) {
		return moimRepository.save(moimCreateRequest.toEntity());
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findAllMoim() {
		List<Moim> moims = moimRepository.findAll();
		return new MoimFindAllResponses(
			moims.stream()
				.map(MoimFindAllResponse::toResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public MoimDetailsFindResponse findMoimDetails(long id) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

		List<String> participants = memberRepository.findNickNamesByMoimId(id);

		return MoimDetailsFindResponse.toResponse(moim, participants);
	}

	public void joinMoim(MoimJoinRequest moimJoinRequest) {
		Member member = new Member(moimJoinRequest.nickName());

		Moim moim = moimRepository.findById(moimJoinRequest.moimId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		member.joinMoim(moim);
		memberRepository.save(member);
		moim.join();
	}

	public void deleteMoim(long id) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

		moimRepository.delete(moim);
	}
}
