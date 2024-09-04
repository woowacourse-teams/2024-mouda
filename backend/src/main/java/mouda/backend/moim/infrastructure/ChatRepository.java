package mouda.backend.moim.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.moim.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query("SELECT c FROM Chat c WHERE c.moim.id = :moimId AND c.id > :chatId")
	List<Chat> findAllUnloadedChats(@Param("moimId") long moimId, @Param("chatId") long chatId);

	Optional<Chat> findFirstByMoimIdOrderByIdDesc(long moimId);
}
