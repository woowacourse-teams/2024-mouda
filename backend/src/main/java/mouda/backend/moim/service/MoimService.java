package mouda.backend.moim.service;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.comment.domain.Comment;
import mouda.backend.comment.dto.request.CommentCreateRequest;
import mouda.backend.comment.dto.response.CommentResponse;
import mouda.backend.comment.exception.CommentErrorMessage;
import mouda.backend.comment.exception.CommentException;
import mouda.backend.comment.repository.CommentRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.zzim.repository.ZzimRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MoimService {

    private final MoimRepository moimRepository;
    private final MemberRepository memberRepository;
    private final ChamyoRepository chamyoRepository;
    private final ZzimRepository zzimRepository;
    private final CommentRepository commentRepository;

    public Moim createMoim(MoimCreateRequest moimCreateRequest, Member member) {
        Moim moim = moimRepository.save(moimCreateRequest.toEntity());
        Chamyo chamyo = Chamyo.builder()
            .member(member)
            .moim(moim)
            .moimRole(MoimRole.MOIMER)
            .build();
        chamyoRepository.save(chamyo);

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
        List<CommentResponse> commentResponses = toCommentResponse(comments);

        return MoimDetailsFindResponse.toResponse(moim, participants, commentResponses);
    }

    private List<CommentResponse> toCommentResponse(List<Comment> comments) {
        Map<Long, List<Comment>> children = comments.stream()
            .filter(Comment::isChild)
            .collect(groupingBy(Comment::getParentId));

        return comments.stream()
            .filter(Comment::isParent)
            .map(comment -> CommentResponse.toResponse(comment, children.getOrDefault(comment.getId(), List.of())))
            .toList();
    }

    @Deprecated
    public void joinMoim(MoimJoinRequest moimJoinRequest) {
        Member member = new Member(moimJoinRequest.nickname());
        Moim moim = moimRepository.findById(moimJoinRequest.moimId())
            .orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));

        member.joinMoim(moim);
        memberRepository.save(member);
        List<Member> participants = memberRepository.findAllByMoimId(moim.getId());

        moim.validateAlreadyFullMoim(participants.size());
    }

    public void deleteMoim(long id, Member member) {
        Moim moim = moimRepository.findById(id)
            .orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
        validateCanDeleteMoim(moim, member);

        chamyoRepository.deleteAllByMoimId(id);
        zzimRepository.deleteAllByMoimId(id);
        moimRepository.delete(moim);
    }

    private void validateCanDeleteMoim(Moim moim, Member member) {
        MoimRole moimRole = chamyoRepository.findByMoimIdAndMemberId(moim.getId(), member.getId())
            .orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND))
            .getMoimRole();

        if (moimRole != MoimRole.MOIMER) {
            throw new MoimException(HttpStatus.FORBIDDEN, MoimErrorMessage.NOT_ALLOWED_TO_DELETE);
        }
    }

    public void updateMoimStatusById(long id, MoimStatus status) {
        moimRepository.updateMoimStatusById(id, status);
    }

    public void createComment(Member member, Long moimId, CommentCreateRequest commentCreateRequest) {
        Moim moim = moimRepository.findById(moimId).orElseThrow(
            () -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND)
        );

        Long parentId = commentCreateRequest.parentId();
        if (parentId != null && !commentRepository.existsById(parentId)) {
            throw new CommentException(HttpStatus.BAD_REQUEST, CommentErrorMessage.PARENT_NOT_FOUND);
        }

        commentRepository.save(commentCreateRequest.toEntity(moim, member));
    }

    public MoimFindAllResponses findAllMyMoim(Member member, FilterType filter) {
        Stream<Chamyo> chamyoStream = chamyoRepository.findAllByMemberId(member.getId()).stream();

        if (filter == FilterType.PAST) {
            chamyoStream = chamyoStream.filter(chamyo -> chamyo.getMoim().isPastMoim());
        }
        if (filter == FilterType.UPCOMING) {
            chamyoStream = chamyoStream.filter(chamyo -> chamyo.getMoim().isUpcomingMoim());
        }

        List<MoimFindAllResponse> responses = chamyoStream
            .map(chamyo -> {
                Moim moim = chamyo.getMoim();
                int participantCount = memberRepository.findAllByMoimId(moim.getId()).size();
                return MoimFindAllResponse.toResponse(moim, participantCount);
            })
            .toList();

        return new MoimFindAllResponses(responses);
    }
}
