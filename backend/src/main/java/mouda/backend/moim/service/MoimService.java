package mouda.backend.moim.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.repository.MoimRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	private final MoimRepository moimRepository;

	public Moim createMoim(MoimCreateRequest moimCreateRequest) {
		Moim moim = moimCreateRequest.toEntity();
		moim.initCurrentPeople();

		return moimRepository.save(moim);
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
	public MoimDetailsFindResponse findMoimDetails(Long moimId) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(IllegalArgumentException::new);

		return MoimDetailsFindResponse.toResponse(moim);
	}

	public void joinMoim(MoimJoinRequest moimJoinRequest) {
		Moim moim = moimRepository.findById(moimJoinRequest.moimId())
			.orElseThrow(() -> new IllegalArgumentException("모임이 존재하지 않습니다."));
		moim.join();
	}

	public void deleteMoim(Long moimId) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(IllegalArgumentException::new);

		moimRepository.delete(moim);
	}
}
