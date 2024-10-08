package mouda.backend.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	name = "chat_room",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"target_id", "type"}
		)
	}
)
public class ChatRoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long targetId;

	private long darakbangId;

	private ChatRoomType type;

	@Builder
	public ChatRoomEntity(long targetId, long darakbangId, ChatRoomType type) {
		this.targetId = targetId;
		this.darakbangId = darakbangId;
		this.type = type;
	}
}
