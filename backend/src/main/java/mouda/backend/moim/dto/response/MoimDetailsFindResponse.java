package mouda.backend.moim.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import mouda.backend.moim.domain.Moim;

@Builder
public record MoimDetailsFindResponse(
	String title,
	LocalDate date,
	LocalTime time,
	String place,
	int currentPeople,
	int maxPeople,
	String authorNickname,
	String description,
	List<String> participants
) {

	public static MoimDetailsFindResponse toResponse(Moim moim, List<String> participants) {
		return MoimDetailsFindResponse.builder()
			.title(moim.getTitle())
			.date(moim.getDate())
			.time(moim.getTime())
			.place(moim.getPlace())
			.currentPeople(participants.size())
			.maxPeople(moim.getMaxPeople())
			.description(moim.getDescription())
			.participants(participants)
			.build();
	}
}
