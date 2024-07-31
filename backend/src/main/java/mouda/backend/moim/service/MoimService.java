package mouda.backend.moim.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.comment.domain.Comment;
import mouda.backend.comment.dto.request.CommentCreateRequest;
import mouda.backend.comment.dto.response.ChildCommentResponse;
import mouda.backend.comment.dto.response.CommentResponse;
import mouda.backend.comment.repository.CommentRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.repository.MoimRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

	private final MoimRepository moimRepository;

	private final MemberRepository memberRepository;

	private final CommentRepository commentRepository;

	public Moim createMoim(MoimCreateRequest moimCreateRequest) {
		Member author = new Member(moimCreateRequest.authorNickname());
		Moim moim = moimRepository.save(moimCreateRequest.toEntity());
		author.joinMoim(moim);
		memberRepository.save(author);

		return moim;
	}

	@Transactional(readOnly = true)
	public MoimFindAllResponses findAllMoim() {
		List<Moim> moims = moimRepository.findAll();
		return new MoimFindAllResponses(
			moims.stream()
				.map(moim -> {
					List<Member> participants = memberRepository.findAllByMoimId(moim.getId());
					return MoimFindAllResponse.toResponse(moim, participants.size());
				})
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public MoimDetailsFindResponse findMoimDetails(long id) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

		List<String> participants = memberRepository.findAllByMoimId(id).stream()
			.map(Member::getNickname)
			.toList();

		List<Comment> comments = commentRepository.findAllByMoimIdOrderByCreatedAt(id);
		Map<Long, List<Comment>> childComments = comments.stream()
			.filter(Comment::isChild)
			.collect(groupingBy(Comment::getParentId));

		List<CommentResponse> commentResponses = comments.stream()
			.filter(Comment::isParent)
			.map(comment -> CommentResponse.toResponse(comment, findChildComments(comment, childComments)))
			.toList();

		return MoimDetailsFindResponse.toResponse(moim, participants, commentResponses);
	}

	private List<ChildCommentResponse> findChildComments(Comment comment, Map<Long, List<Comment>> childComments) {
		return childComments.getOrDefault(comment.getId(), List.of()).stream()
			.map(ChildCommentResponse::toResponse)
			.toList();
	}

	public void joinMoim(MoimJoinRequest moimJoinRequest) {
		Member member = new Member(moimJoinRequest.nickname());
		Moim moim = moimRepository.findById(moimJoinRequest.moimId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

		member.joinMoim(moim);
		memberRepository.save(member);
		List<Member> participants = memberRepository.findAllByMoimId(moim.getId());

		moim.validateAlreadyFullMoim(participants.size());
	}

	public void deleteMoim(long id) {
		Moim moim = moimRepository.findById(id)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
		List<Member> participants = memberRepository.findAllByMoimId(moim.getId());
		for (Member participant : participants) {
			memberRepository.deleteById(participant.getId());
		}

		moimRepository.delete(moim);
	}

	public void createComment(Member member, Long moimId, CommentCreateRequest commentCreateRequest) {
		Moim moim = moimRepository.findById(moimId).orElseThrow(
			() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND)
		);

		commentRepository.save(commentCreateRequest.toEntity(moim, member));
	}
}
