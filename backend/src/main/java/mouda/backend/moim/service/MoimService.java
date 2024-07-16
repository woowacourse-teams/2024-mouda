package mouda.backend.moim.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.response.MoimFindAllResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
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

    public void deleteMoim(long id) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);

		moimRepository.delete(moim);
    }
}
