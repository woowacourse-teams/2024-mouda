package mouda.backend.moim.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;

@Entity
@Getter
@NoArgsConstructor
public class Moim {

	private static final int TITLE_MAX_LENGTH = 30;
	private static final int PLACE_MAX_LENGTH = 100;
	private static final int MAX_PEOPLE_LOWER_BOUND = 1;
	private static final int MAX_PEOPLE_UPPER_BOUND = 99;
	private static final int AUTHOR_NICKNAME_MAX_LENGTH = 10;
	private static final int DESCRIPTION_MAX_LENGTH = 1000;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime time;

	@Column(nullable = false)
	private String place;

	@Column(nullable = false)
	private int maxPeople;

	private String description;

	@Enumerated(EnumType.STRING)
	private MoimStatus moimStatus;

	private boolean isChatOpened;

	@Builder
	public Moim(
		String title,
		LocalDate date,
		LocalTime time,
		String place,
		int maxPeople,
		String description
	) {
		validateTitle(title);
		validateDate(date);
		validateTime(time);
		validateMoimIsFuture(date, time);
		validatePlace(place);
		validateMaxPeople(maxPeople);
		validateDescription(description);

		this.title = title;
		this.date = date;
		this.time = time;
		this.place = place;
		this.maxPeople = maxPeople;
		this.description = description;
		this.moimStatus = MoimStatus.MOIMING;
	}

	private void validateTitle(String title) {
		if (title.isBlank()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.TITLE_NOT_EXIST);
		}
		if (title.length() > TITLE_MAX_LENGTH) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.TITLE_TOO_LONG);
		}
	}

	private void validateDate(LocalDate date) {
		if (date == null) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.DATE_NOT_EXIST);
		}
	}

	private void validateTime(LocalTime time) {
		if (time == null) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.TIME_NOT_EXIST);
		}
	}

	private void validateMoimIsFuture(LocalDate date, LocalTime time) {
		LocalDateTime moimDateTime = LocalDateTime.of(date, time);
		if (moimDateTime.isBefore(LocalDateTime.now())) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.PAST_DATE_TIME);
		}
	}

	private void validatePlace(String place) {
		if (place.isBlank()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.PLACE_NOT_EXIST);
		}
		if (place.length() > PLACE_MAX_LENGTH) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.PLACE_TOO_LONG);
		}
	}

	private void validateMaxPeople(int maxPeople) {
		if (maxPeople < MAX_PEOPLE_LOWER_BOUND) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE_IS_POSITIVE);
		}
		if (maxPeople > MAX_PEOPLE_UPPER_BOUND) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE_TOO_MANY);
		}
	}

	private void validateDescription(String description) {
		if (description != null && description.length() > DESCRIPTION_MAX_LENGTH) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.DESCRIPTION_TOO_LONG);
		}
	}

	public void validateAlreadyFullMoim(int currentPeople) {
		if (currentPeople > maxPeople) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE);
		}
	}

	public void update(String title, LocalDate date, LocalTime time, String place, int maxPeople,
		String description, int currentPeople) {
		if (!Objects.equals(this.title, title)) {
			validateTitle(title);
			this.title = title;
		}

		if (!Objects.equals(this.date, date)) {
			validateDate(date);
			this.date = date;
		}

		if (!Objects.equals(this.time, time)) {
			validateTime(time);
			this.time = time;
		}

		validateMoimIsFuture(this.date, this.time);

		if (!Objects.equals(this.place, place)) {
			validatePlace(place);
			this.place = place;
		}

		if (!Objects.equals(this.maxPeople, maxPeople)) {
			validateMaxPeople(maxPeople);
			validateMaxPeopleIsUpperThanCurrentPeople(maxPeople, currentPeople);
			this.maxPeople = maxPeople;
		}

		if (!Objects.equals(this.description, description)) {
			validateDescription(description);
			this.description = description;
		}
	}

	private void validateMaxPeopleIsUpperThanCurrentPeople(int maxPeople, int currentPeople) {
		if (maxPeople < currentPeople) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE_IS_LOWER_THAN_CURRENT_PEOPLE);
		}
	}

	public boolean isPastMoim() {
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime.isBefore(LocalDateTime.now());
	}

	public boolean isUpcomingMoim() {
		return !isPastMoim();
	}

}
