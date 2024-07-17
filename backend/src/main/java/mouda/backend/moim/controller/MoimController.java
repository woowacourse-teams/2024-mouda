package mouda.backend.moim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.service.MoimService;

@RestController
@RequestMapping("/v1/moim")
@RequiredArgsConstructor
public class MoimController {

	private final MoimService moimService;

	@Operation(summary = "모임 생성", description = "모임을 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 생성 성공!"),
	})
	@PostMapping
	public ResponseEntity<RestResponse<Long>> createMoim(@RequestBody MoimCreateRequest moimCreateRequest) {
		Moim moim = moimService.createMoim(moimCreateRequest);
		return ResponseEntity.ok().body(new RestResponse<>(moim.getId()));
	}

	@Operation(summary = "모임 전체 조회", description = "모든 모임을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 조회 성공!"),
	})
	@GetMapping
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMoim() {
		return ResponseEntity.ok().body(new RestResponse<>(moimService.findAllMoim()));
	}

	@Operation(summary = "모임 참여", description = "모임에 참여한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 참여 성공")
	})
	@PostMapping
	public ResponseEntity<Void> joinMoim(@RequestBody MoimJoinRequest moimJoinRequest) {
		moimService.joinMoim(moimJoinRequest);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "모임 삭제", description = "해당하는 id의 모임을 삭제한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 삭제 성공!"),
	})
	@DeleteMapping("/{id}")
	public void deleteMoim(@PathVariable long id) {
		moimService.deleteMoim(id);
	}
}
