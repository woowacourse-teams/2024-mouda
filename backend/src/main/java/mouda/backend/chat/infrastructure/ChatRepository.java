package mouda.backend.chat.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.chat.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
	@Query("SELECT c FROM ChatEntity c WHERE c.chatRoomId = :chatRoomId AND c.id > :chatId")
	List<ChatEntity> findAllUnloadedChats(@Param("chatRoomId") long chatRoomId, @Param("chatId") long chatId);

	Optional<ChatEntity> findFirstByChatRoomIdOrderByIdDesc(long chatRoomId);
}
