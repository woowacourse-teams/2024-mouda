package mouda.backend.notification.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm_token")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class FcmTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String token;

    private boolean isActive;

    private LocalDateTime lastUpdated;

    @Builder
    public FcmTokenEntity(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
        this.isActive = true;
        this.lastUpdated = LocalDateTime.now();
    }

    public void refresh() {
        this.lastUpdated = LocalDateTime.now();
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive(LocalDateTime threshold) {
        return isActive && lastUpdated.isBefore(LocalDateTime.now().minusMonths(1L));
    }

    public boolean isInactive() {
        return !isActive();
    }

    public boolean isExpired() {
        return isInactive() && lastUpdated.isBefore(LocalDateTime.now().minusDays(270L));
    }
}
