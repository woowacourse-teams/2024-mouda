package mouda.backend.moim.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import mouda.backend.comment.dto.response.CommentResponse;
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
	String status,
	List<CommentResponse> comments
) {

	public static MoimDetailsFindResponse toResponse(Moim moim, int currentPeople, List<CommentResponse> comments) {
		return MoimDetailsFindResponse.builder()
			.title(moim.getTitle())
			.date(moim.getDate())
			.time(moim.getTime())
			.place(moim.getPlace())
			.currentPeople(currentPeople)
			.maxPeople(moim.getMaxPeople())
			.description(moim.getDescription())
			.status(moim.getMoimStatus().name())
			.comments(comments)
			.build();
	}
}
