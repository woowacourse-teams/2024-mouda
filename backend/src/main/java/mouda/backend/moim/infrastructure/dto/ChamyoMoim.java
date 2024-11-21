package mouda.backend.moim.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ChamyoMoim {
	private final long moimId;
	private final long chamyoCount;
}
