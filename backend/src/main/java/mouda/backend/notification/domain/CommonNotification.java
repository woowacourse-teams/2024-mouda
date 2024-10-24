package mouda.backend.notification.domain;

import com.google.firebase.messaging.Notification;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommonNotification {

    private final NotificationType type;
    private final String title; //-> 다락방 이름
    private final String body;
    private final LocalDateTime createdAt;
    private final String redirectUrl;

    @Builder
    public CommonNotification(NotificationType type, String title, String body, String redirectUrl) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.redirectUrl = redirectUrl;
        this.createdAt = LocalDateTime.now();
    }

    public Notification toNotification() {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
