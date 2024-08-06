package mouda.backend.please.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

	@Builder
	public Please(String title, String description, long authorId) {
		validateTitle(title);
		validateDescription(description);
		this.title = title;
		this.description = description;
		this.authorId = authorId;
	}

	private void validateTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException();
		}
	}

	private void validateDescription(String description) {
		if (description == null || description.isEmpty()) {
			throw new IllegalArgumentException();
		}
	}
}
