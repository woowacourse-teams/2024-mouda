package mouda.backend.moim.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private LocalDate date;

	private LocalTime time;

	private String place;

	private int currentPeople;

	private int maxPeople;

	private String authorNickname;

	private String description;

	public void join() {
		if (currentPeople + 1 > maxPeople) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MAX_PEOPLE);
		}
		currentPeople++;
	}
}
