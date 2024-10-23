package mouda.backend.please.domain;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;

@Entity
@Getter
@Table(name = "please")
@NoArgsConstructor
public class Please {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private long authorId;

	@Column(nullable = false)
	private long darakbangId;

	@Builder
	public Please(String title, String description, long authorId, long darakbangId) {
		validateTitle(title);
		validateDescription(description);
		this.title = title;
		this.description = description;
		this.authorId = authorId;
		this.darakbangId = darakbangId;
	}

	private void validateTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.TITLE_NOT_EXIST);
		}
	}

	private void validateDescription(String description) {
		if (description == null || description.isEmpty()) {
			throw new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.DESCRIPTION_NOT_EXIST);
		}
	}

	public boolean isNotAuthor(long memberId) {
		return authorId != memberId;
	}

	public boolean isNotInDarakbang(long darakbangId) {
		return this.darakbangId != darakbangId;
	}
}
