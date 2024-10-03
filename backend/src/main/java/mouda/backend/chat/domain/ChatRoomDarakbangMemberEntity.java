package mouda.backend.chat.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room_darakbang_member")
public class ChatRoomDarakbangMemberEntity { // TODO: 복합 유니크 키 걸기
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chat_room_id")
	private long chatRoomId;

	@Column(name = "darakbang_member_id")
	private long darakbangMemberId;

	@Builder
	public ChatRoomDarakbangMemberEntity(long chatRoomId, long darakbangMemberId) {
		this.chatRoomId = chatRoomId;
		this.darakbangMemberId = darakbangMemberId;
	}
}
