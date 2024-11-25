package mouda.backend.moim.infrastructure.dto;

import lombok.Getter;

@Getter
public class ChamyoCountResponse {

    private final Long moimId;
    private final Integer count;

    public ChamyoCountResponse(Long moimId, Long count) {
        this.moimId = moimId;
        this.count = count.intValue();
    }
}
