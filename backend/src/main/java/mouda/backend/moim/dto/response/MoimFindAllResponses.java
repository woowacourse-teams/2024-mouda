package mouda.backend.moim.dto.response;

import java.util.List;
import mouda.backend.moim.domain.Moim;

public record MoimFindAllResponses(
        List<MoimFindAllResponse> moims
) {
}
