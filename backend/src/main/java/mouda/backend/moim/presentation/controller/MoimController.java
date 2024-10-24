package mouda.backend.moim.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.business.CommentService;
import mouda.backend.moim.business.MoimService;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.presentation.controller.swagger.MoimSwagger;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimEditRequest;
import mouda.backend.moim.presentation.response.moim.MoimDetailsFindResponse;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponses;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/moim")
@RequiredArgsConstructor
public class MoimController implements MoimSwagger {

	private final MoimService moimService;
	private final CommentService commentService;

	@Override
	@PostMapping
	public ResponseEntity<RestResponse<Long>> createMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody MoimCreateRequest moimCreateRequest
	) {
		Moim moim = moimService.createMoim(darakbangId, member, moimCreateRequest);

		return ResponseEntity.ok().body(new RestResponse<>(moim.getId()));
	}

	@Override
	@GetMapping
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	) {
		MoimFindAllResponses moimFindAllResponses = moimService.findAllMoim(darakbangId, member);

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}

	@Override
	@GetMapping("/{moimId}")
	public ResponseEntity<RestResponse<MoimDetailsFindResponse>> findMoimDetails(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable("moimId") Long moimId
	) {
		MoimDetailsFindResponse moimDetailsFindResponse = moimService.findMoimDetails(darakbangId, moimId);

		return ResponseEntity.ok().body(new RestResponse<>(moimDetailsFindResponse));
	}

	@Override
	@PatchMapping("/{moimId}/complete")
	public ResponseEntity<Void> completeMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	) {
		moimService.completeMoim(darakbangId, moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/cancel")
	public ResponseEntity<Void> cancelMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	) {
		moimService.cancelMoim(darakbangId, moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/reopen")
	public ResponseEntity<Void> reopenMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	) {
		moimService.reopenMoim(darakbangId, moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping
	public ResponseEntity<Void> editMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody MoimEditRequest request
	) {
		moimService.editMoim(darakbangId, request, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/{moimId}")
	public ResponseEntity<Void> createComment(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId,
		@RequestBody CommentCreateRequest commentCreateRequest
	) {
		commentService.createComment(darakbangId, moimId, member, commentCreateRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMyMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam(value = "filter", defaultValue = "ALL") FilterType filter
	) {
		MoimFindAllResponses moimFindAllResponses = moimService.findAllMyMoim(member, filter);

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}

	@Override
	@GetMapping("/zzim")
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllZzimedMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	) {
		MoimFindAllResponses moimFindAllResponses = moimService.findZzimedMoim(member);

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}
}
