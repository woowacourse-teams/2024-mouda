package mouda.backend.moim.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mouda.backend.moim.domain.Moim;

@Schema(name = "모임 생성 요청", description = "모임을 생성할 때 사용")
public record MoimCreateRequest(
	@NotBlank
	@Schema(description = "모임 이름", minLength = 1)
	String title,

	@Schema(description = "모임 날짜", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2021-12-31")
	LocalDate date,

	@Schema(description = "모임 시간", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "12:00")
	LocalTime time,

	@Schema(description = "모임 장소", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "서울시 강남구")
	String place,

	@NotNull
	@Schema(description = "최대 인원", minimum = "1", example = "5")
	Integer maxPeople,

	@Schema(description = "모임 설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "모임 설명")
	String description
) {

	public Moim toEntity(Long darakbangId) {
		return Moim.builder()
			.title(title)
			.date(date)
			.time(time)
			.place(place)
			.maxPeople(maxPeople)
			.description(description)
			.darakbangId(darakbangId)
			.build();
	}
}
