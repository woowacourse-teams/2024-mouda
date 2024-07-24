package mouda.backend.moim.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;

@Entity
@Getter
@NoArgsConstructor
public class Moim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String title;

	@NotNull
	@Column(nullable = false)
	private LocalDate date;

	@NotNull
	@Column(nullable = false)
	private LocalTime time;

	@NotBlank
	@Column(nullable = false)
	private String place;

	@Positive
	@Column(nullable = false)
	private int maxPeople;

	@NotBlank
	@Column(nullable = false)
	private String authorNickname;

	private String description;

	@Builder
	public Moim(
		String title,
		LocalDate date,
		LocalTime time,
		String place,
		int maxPeople,
		String authorNickname,
		String description
	) {
		this.title = title;
		validateMoimIsFuture(date, time);
		this.date = date;
		this.time = time;
		this.place = place;
		this.maxPeople = maxPeople;
		this.authorNickname = authorNickname;
		this.description = description;
	}

	private void validateMoimIsFuture(LocalDate date, LocalTime time) {
		LocalDateTime moimDateTime = LocalDateTime.of(date, time);
		if (moimDateTime.isBefore(LocalDateTime.now())) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.PAST_DATE_TIME);
		}
	}

	public void validateAlreadyFullMoim(int currentPeople) {
		if (currentPeople + 1 > maxPeople) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE);
		}
	}
}
