package mouda.backend.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.chat.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query("SELECT c FROM Chat c WHERE c.moim.id = :moimId AND c.id > :chatId")
	List<Chat> findAllUnloadedChats(@Param("moimId") long moimId, @Param("chatId") long chatId);

	Chat findLastByOrderById();
}
