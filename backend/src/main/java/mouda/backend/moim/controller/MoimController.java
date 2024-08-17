package mouda.backend.moim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import mouda.backend.comment.dto.request.CommentCreateRequest;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimEditRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.service.MoimService;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/moim")
@RequiredArgsConstructor
public class MoimController implements MoimSwagger {

	private final MoimService moimService;

	@Override
	@PostMapping
	public ResponseEntity<RestResponse<Long>> createMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody MoimCreateRequest moimCreateRequest
	) {
		Moim moim = moimService.createMoim(darakbangId, member, moimCreateRequest);

		return ResponseEntity.ok().body(new RestResponse<>(moim.getId()));
	}

	@Override
	@GetMapping
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	) {
		MoimFindAllResponses moimFindAllResponses = moimService.findAllMoim(member);

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}

	@Override
	@GetMapping("/{moimId}")
	public ResponseEntity<RestResponse<MoimDetailsFindResponse>> findMoimDetails(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@PathVariable("moimId") Long moimId
	) {
		MoimDetailsFindResponse moimDetailsFindResponse = moimService.findMoimDetails(moimId);

		return ResponseEntity.ok().body(new RestResponse<>(moimDetailsFindResponse));
	}

	@Deprecated
	@Override
	@PostMapping("/join")
	public ResponseEntity<Void> joinMoim(
		@PathVariable Long darakbangId,
		@RequestBody MoimJoinRequest moimJoinRequest
	) {
		moimService.joinMoim(moimJoinRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@DeleteMapping("/{moimId}")
	public ResponseEntity<Void> deleteMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@PathVariable Long moimId
	) {
		moimService.deleteMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/complete")
	public ResponseEntity<Void> completeMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@PathVariable Long moimId
	) {
		moimService.completeMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/cancel")
	public ResponseEntity<Void> cancelMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@PathVariable Long moimId
	) {
		moimService.cancelMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping("/{moimId}/reopen")
	public ResponseEntity<Void> reopenMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@PathVariable Long moimId
	) {
		moimService.reopenMoim(moimId, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PatchMapping
	public ResponseEntity<Void> editMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody MoimEditRequest request
	) {
		moimService.editMoim(request, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/{moimId}")
	public ResponseEntity<Void> createComment(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@PathVariable Long moimId,
		@RequestBody CommentCreateRequest commentCreateRequest
	) {
		moimService.createComment(member, moimId, commentCreateRequest);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMyMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@RequestParam(value = "filter", defaultValue = "ALL") FilterType filter
	) {
		MoimFindAllResponses moimFindAllResponses = moimService.findAllMyMoim(member, filter);

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}

	@Override
	@GetMapping("/zzim")
	public ResponseEntity<RestResponse<MoimFindAllResponses>> findAllZzimedMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	) {
		MoimFindAllResponses moimFindAllResponses = moimService.findZzimedMoim(member);

		return ResponseEntity.ok().body(new RestResponse<>(moimFindAllResponses));
	}
}
