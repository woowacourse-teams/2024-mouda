package mouda.backend.notification.domain;

import lombok.Getter;

@Getter
public class Recipient {

    private long memberId;

    public Recipient(long memberId) {
        this.memberId = memberId;
    }
}
