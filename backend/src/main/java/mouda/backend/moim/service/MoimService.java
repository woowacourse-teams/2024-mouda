package mouda.backend.moim.service;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.repository.MoimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

    private final MoimRepository moimRepository;

    public Moim createMoim(MoimCreateRequest moimCreateRequest) {
        return moimRepository.save(moimCreateRequest.toEntity());
    }
}
