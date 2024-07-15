package mouda.backend.moim.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import mouda.backend.moim.domain.Moim;

@Builder
public record MoimFindAllResponse(
        String title,
        LocalDate date,
        LocalTime time,
        String place,
        int maxPeople,
        String authorNickname,
        String description
) {
    public static MoimFindAllResponse toResponse(Moim moim) {
        return MoimFindAllResponse.builder()
                .title(moim.getTitle())
                .date(moim.getDate())
                .time(moim.getTime())
                .place(moim.getPlace())
                .maxPeople(moim.getMaxPeople())
                .authorNickname(moim.getAuthorNickname())
                .description(moim.getDescription())
                .build();
    }
}
