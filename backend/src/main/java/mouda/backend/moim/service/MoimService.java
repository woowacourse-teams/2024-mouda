package mouda.backend.moim.service;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.repository.MoimRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoimService {

    private MoimRepository moimRepository;

    public Moim createMoim(MoimCreateRequest moimCreateRequest) {
        return moimRepository.save(moimCreateRequest.toEntity());
    }
}
