package mouda.backend.comment.domain;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Column;
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
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Moim moim;

	@ManyToOne
	@JoinColumn(nullable = false)
	private DarakbangMember member;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private Long parentId;

	@Builder
	public Comment(String content, Moim moim, DarakbangMember member, LocalDateTime createdAt, Long parentId) {
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
		if (content == null || content.isBlank()) {
			throw new CommentException(HttpStatus.BAD_REQUEST, CommentErrorMessage.CONTENT_NOT_FOUND);
		}
	}

	private void validateMoim(Moim moim) {
		if (moim == null) {
			throw new CommentException(HttpStatus.NOT_FOUND, CommentErrorMessage.MOIM_NOT_FOUND);
		}
	}

	private void validateMember(DarakbangMember member) {
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
