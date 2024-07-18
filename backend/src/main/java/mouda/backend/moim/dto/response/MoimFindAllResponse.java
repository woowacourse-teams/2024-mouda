package mouda.backend.moim.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import mouda.backend.moim.domain.Moim;

@Builder
public record MoimFindAllResponse(
	Long moimId,
	String title,
	LocalDate date,
	LocalTime time,
	String place,
	Integer currentPeople,
	Integer maxPeople,
	String authorNickname,
	String description
) {

	public static MoimFindAllResponse toResponse(Moim moim) {
		return MoimFindAllResponse.builder()
			.moimId(moim.getId())
			.title(moim.getTitle())
			.date(moim.getDate())
			.time(moim.getTime())
			.place(moim.getPlace())
			.currentPeople(moim.getCurrentPeople())
			.maxPeople(moim.getMaxPeople())
			.authorNickname(moim.getAuthorNickname())
			.description(moim.getDescription())
			.build();
	}
}
