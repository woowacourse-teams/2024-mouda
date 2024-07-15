package mouda.backend.moim.controller;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.ApiResponse;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.service.MoimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/moim")
@RequiredArgsConstructor
public class MoimController {

    private final MoimService moimService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMoim(@RequestBody MoimCreateRequest moimCreateRequest) {
        Moim moim = moimService.createMoim(moimCreateRequest);
        return ResponseEntity.ok().body(new ApiResponse<>(moim.getId()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<MoimFindAllResponses>> findAllMoim() {
        return ResponseEntity.ok().body(new ApiResponse<>(moimService.findAllMoim()));
    }
}
