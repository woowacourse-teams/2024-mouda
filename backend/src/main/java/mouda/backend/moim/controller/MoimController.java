package mouda.backend.moim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.comment.dto.request.CommentCreateRequest;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimEditRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.service.MoimService;

@RestController
@RequestMapping("/v1/moim")
@RequiredArgsConstructor
public class MoimController implements MoimSwagger {

	private final MoimService moimService;

	@Override
	@PostMapping
	public ResponseEntity<RestResponse<Long>> createMoim(
		@Valid @RequestBody MoimCreateRequest moimCreateRequest,
		@LoginMember Member member
	) {
		Moim moim = moimService.createMoim(moimCreateRequest, member);

		return ResponseEntity.ok().body(new RestResponse<>(moim.getId()));
	}

	@Override
	@GetMapping
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMoim() {
		MoimFindAllResponses moimFindAllResponses = moimService.findAllMoim();

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}

	@Override
	@GetMapping("/{moimId}")
	public ResponseEntity<RestResponse<MoimDetailsFindResponse>> findMoimDetails(@PathVariable("moimId") Long moimId) {
		MoimDetailsFindResponse moimDetailsFindResponse = moimService.findMoimDetails(moimId);

		return ResponseEntity.ok().body(new RestResponse<>(moimDetailsFindResponse));
	}

	@Deprecated
	@Override
	@PostMapping("/join")
	public ResponseEntity<Void> joinMoim(@RequestBody MoimJoinRequest moimJoinRequest) {
		moimService.joinMoim(moimJoinRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@DeleteMapping("/{moimId}")
	public ResponseEntity<Void> deleteMoim(@PathVariable Long moimId, @LoginMember Member member) {
		moimService.deleteMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/complete")
	public ResponseEntity<Void> completeMoim(@PathVariable Long moimId, @LoginMember Member member) {
		moimService.completeMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/cancel")
	public ResponseEntity<Void> cancelMoim(@PathVariable Long moimId, @LoginMember Member member) {
		moimService.cancelMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/reopen")
	public ResponseEntity<Void> reopenMoim(@PathVariable Long moimId, @LoginMember Member member) {
		moimService.reopenMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping
	public ResponseEntity<Void> editMoim(@Valid @RequestBody MoimEditRequest request, @LoginMember Member member) {
		moimService.editMoim(request, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/{moimId}")
	public ResponseEntity<Void> createComment(
		@LoginMember Member member,
		@PathVariable Long moimId,
		@RequestBody CommentCreateRequest commentCreateRequest
	) {
		moimService.createComment(member, moimId, commentCreateRequest);

		return ResponseEntity.ok().build();
	}
}
