package mouda.backend.moim.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import mouda.backend.moim.domain.Moim;

@Builder
public record MoimFindAllResponse(
    Long moimId,
    String title,
    LocalDate date,
    LocalTime time,
    String place,
    int currentPeople,
    int maxPeople,
    String authorNickname,
    String description,
    boolean zzim
) {

    public static MoimFindAllResponse toResponse(Moim moim, int currentPeople, boolean zzim) {
        return MoimFindAllResponse.builder()
            .moimId(moim.getId())
            .title(moim.getTitle())
            .date(moim.getDate())
            .time(moim.getTime())
            .place(moim.getPlace())
            .currentPeople(currentPeople)
            .maxPeople(moim.getMaxPeople())
            .description(moim.getDescription())
            .zzim(zzim)
            .build();
    }
}
