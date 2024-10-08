package mouda.backend.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
