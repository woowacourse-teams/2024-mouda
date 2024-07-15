package mouda.backend.moim.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record MoimCreateRequest(
        String title,
        LocalDate date,
        LocalTime time,
        String place,
        int maxPeople,
        String authorNickname,
        String description
) {
}
