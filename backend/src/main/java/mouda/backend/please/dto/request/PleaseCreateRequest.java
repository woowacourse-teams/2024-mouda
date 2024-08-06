package mouda.backend.please.dto.request;

import jakarta.validation.constraints.NotNull;
import mouda.backend.please.domain.Please;

public record PleaseCreateRequest(

	@NotNull
	String title,

	@NotNull
	String description
) {

	public Please toEntity(long memberId) {
		return Please.builder()
			.title(title)
			.description(description)
			.authorId(memberId)
			.build();
	}
}
