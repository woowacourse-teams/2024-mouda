package mouda.backend.moim.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Entity;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private LocalDate date;

	private LocalTime time;

	private String place;

	private int maxPeople;

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
		this.date = date;
		this.time = time;
		this.place = place;
		this.maxPeople = maxPeople;
		this.authorNickname = authorNickname;
		this.description = description;
	}

	public void validateAlreadyFullMoim(int currentPeople) {
		if (currentPeople + 1 > maxPeople) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE);
		}
	}
}
