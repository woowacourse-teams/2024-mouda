package mouda.backend.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.chat.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
