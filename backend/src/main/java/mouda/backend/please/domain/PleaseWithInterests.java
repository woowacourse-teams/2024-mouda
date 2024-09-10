package mouda.backend.please.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PleaseWithInterests {

	private final List<PleaseWithInterest> pleaseWithInterests;
}
