package mouda.backend.chat.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.chat.entity.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
	Optional<ChatRoomEntity> findByIdAndDarakbangId(Long chatRoomId, long darakbangId);
}
