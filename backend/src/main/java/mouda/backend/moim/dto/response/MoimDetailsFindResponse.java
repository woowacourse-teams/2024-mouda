package mouda.backend.moim.dto.response;

import static java.time.format.DateTimeFormatter.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.comment.dto.response.CommentResponse;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;

@Builder
@Schema(name = "모임 상세 조회 응답", description = "모임의 상세 정보를 조회할 때 사용")
public record MoimDetailsFindResponse(
	@Schema(description = "모임 이름", example = "모임 이름")
	String title,
	@Schema(description = "모임 날짜", example = "2021-12-31")
	String date,
	@Schema(description = "모임 시간", example = "12:00")
	String time,
	@Schema(description = "모임 장소", example = "서울시 강남구")
	String place,
	@Schema(description = "현재 참여중인 인원", minimum = "1", example = "3")
	int currentPeople,
	@Schema(description = "최대 인원", minimum = "1", example = "5")
	int maxPeople,
	@Schema(description = "모임 주최자 닉네임", example = "테니")
	String authorNickname,
	@Schema(description = "모임 설명", example = "모임 설명")
	String description,
	@Schema(description = "모임 상태", example = "MOIMING")
	MoimStatus status,
	@Schema(description = "댓글 목록")
	List<CommentResponse> comments
) {

	public static MoimDetailsFindResponse toResponse(Moim moim, int currentPeople, List<CommentResponse> comments) {
		String time = moim.getTime() == null ? "" : moim.getTime().format(ofPattern("HH:mm"));
		String date = moim.getDate() == null ? "" : moim.getDate().format(ISO_LOCAL_DATE);
		String place = moim.getPlace() == null ? "" : moim.getPlace();
		return MoimDetailsFindResponse.builder()
			.title(moim.getTitle())
			.date(date)
			.time(time)
			.place(place)
			.currentPeople(currentPeople)
			.maxPeople(moim.getMaxPeople())
			.description(moim.getDescription())
			.status(moim.getMoimStatus())
			.comments(comments)
			.build();
	}
}
