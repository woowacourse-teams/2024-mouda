package mouda.backend.comment.domain;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.comment.exception.CommentErrorMessage;
import mouda.backend.comment.exception.CommentException;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "moim_id")
	private Moim moim;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private LocalDateTime createdAt;

	private Long parentId;

	@Builder
	public Comment(String content, Moim moim, Member member, LocalDateTime createdAt, Long parentId) {
		validateContent(content);
		validateMoim(moim);
		validateMember(member);
		this.content = content;
		this.moim = moim;
		this.member = member;
		this.createdAt = createdAt;
		this.parentId = parentId;
	}

	private void validateContent(String content) {
		if (content.isBlank()) {
			throw new CommentException(HttpStatus.BAD_REQUEST, CommentErrorMessage.CONTENT_NOT_FOUND);
		}
	}

	private void validateMoim(Moim moim) {
		if (moim == null) {
			throw new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND);
		}
	}

	private void validateMember(Member member) {
		if (member == null) {
			throw new CommentException(HttpStatus.NOT_FOUND, CommentErrorMessage.MEMBER_NOT_FOUND);
		}
	}

	public boolean isParent() {
		return parentId == null;
	}

	public boolean isChild() {
		return parentId != null;
	}

	public String getAuthorNickname() {
		return member.getNickname();
	}
}
