package mouda.backend.moim.domain;

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
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.exception.CommentErrorMessage;
import mouda.backend.moim.exception.CommentException;

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
	private DarakbangMember darakbangMember;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private Long parentId;

	@Builder
	public Comment(String content, Moim moim, DarakbangMember darakbangMember, LocalDateTime createdAt, Long parentId) {
		validateContent(content);
		validateMoim(moim);
		validateMember(darakbangMember);
		this.content = content;
		this.moim = moim;
		this.darakbangMember = darakbangMember;
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

	private void validateMember(DarakbangMember darakbangMember) {
		if (darakbangMember == null) {
			throw new CommentException(HttpStatus.NOT_FOUND, CommentErrorMessage.MEMBER_NOT_FOUND);
		}
	}

	public boolean isComment() {
		return parentId == null;
	}

	public boolean isReply() {
		return parentId != null;
	}

	public String getAuthorNickname() {
		return darakbangMember.getNickname();
	}
}
