package mouda.backend.moim.service;

import static mouda.backend.moim.exception.MoimErrorMessage.*;
import static org.springframework.http.HttpStatus.*;

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
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.repository.MoimRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	private final MoimRepository moimRepository;

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
			.orElseThrow(() -> new MoimException(NOT_FOUND, MOIM_NOT_FOUND));

		return MoimDetailsFindResponse.toResponse(moim);
	}

	public void joinMoim(MoimJoinRequest moimJoinRequest) {
		Moim moim = moimRepository.findById(moimJoinRequest.moimId())
			.orElseThrow(() -> new MoimException(NOT_FOUND, MOIM_NOT_FOUND));
		moim.join();
	}

	public void deleteMoim(long id) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(() -> new MoimException(NOT_FOUND, MOIM_NOT_FOUND));

		moimRepository.delete(moim);
	}
}
