package mouda.backend.moim.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record MoimFindAllResponse(
        String title,
        LocalDate date,
        LocalTime time,
        String place,
        int maxPeople,
        String authorNickname,
        String description
) {
}
