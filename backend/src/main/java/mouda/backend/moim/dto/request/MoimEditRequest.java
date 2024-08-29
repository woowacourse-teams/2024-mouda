package mouda.backend.moim.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "모임 수정 요청", description = "모임을 수정할 때 사용")
public record MoimEditRequest(
	@NotNull
	@Positive
	@Schema(description = "수정하고자 하는 모임의 ID", minimum = "1", example = "1")
	Long moimId,

	@NotBlank
	@Schema(description = "변경할 모임 이름", minLength = 1)
	String title,

	@Schema(description = "변경할 모임 날짜", example = "2021-12-31")
	LocalDate date,

	@Schema(description = "변경할 모임 시간", type = "string", example = "12:00")
	LocalTime time,

	@Schema(description = "변경할 모임 장소", example = "서울시 강남구")
	String place,

	@NotNull
	@Positive
	@Schema(description = "변경할 최대 인원, 현재 참여중인 인원 이상의 숫자여야함", example = "5")
	Integer maxPeople,

	@Schema(description = "변경할 모임 설명", example = "모임 설명")
	String description
) {
}
