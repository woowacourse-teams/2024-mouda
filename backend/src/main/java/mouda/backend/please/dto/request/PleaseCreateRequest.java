package mouda.backend.please.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import mouda.backend.please.domain.Please;

public record PleaseCreateRequest(
	@NotEmpty
	@Schema(description = "해주세요 제목", example = "농구 모임")
	String title,

	@NotEmpty
	@Schema(description = "해주세요 상세", example = "농구 모임 만들어주세요~")
	String description
) {

	public Please toEntity(long memberId, long darakbangId) {
		return Please.builder()
			.title(title)
			.description(description)
			.authorId(memberId)
			.darakbangId(darakbangId)
			.build();
	}
}
