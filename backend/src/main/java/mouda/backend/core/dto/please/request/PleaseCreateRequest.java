package mouda.backend.core.dto.please.request;

import jakarta.validation.constraints.NotNull;
import mouda.backend.core.domain.please.Please;

public record PleaseCreateRequest(

	@NotNull
	String title,

	@NotNull
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
