package mouda.backend.moim.controller;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.service.MoimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/moim")
@RequiredArgsConstructor
public class MoimController {

    private final MoimService moimService;

    @PostMapping
    public ResponseEntity<Long> createMoim(@RequestBody MoimCreateRequest moimCreateRequest) {
        Moim moim = moimService.createMoim(moimCreateRequest);
        return ResponseEntity.ok().body(moim.getId());
    }
}
