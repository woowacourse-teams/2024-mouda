package mouda.backend.moim.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.moim.domain.Moim;

public record MoimCreateRequest(
	String title,
	LocalDate date,
	LocalTime time,
	String place,
	Integer maxPeople,
	String authorNickname,
	String description
) {

	public Moim toEntity() {
		return Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.authorNickname(authorNickname)
			.description(description)
			.build();
	}
}
