package mouda.backend.chat.implement;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;

@Component
@RequiredArgsConstructor
public class AttributeManagerRegistry {

	private final List<AttributeManager> attributeManagers;

	public AttributeManager getManager(ChatRoomType chatRoomType) {
		return attributeManagers.stream()
			.filter(attributeManager -> attributeManager.support(chatRoomType))
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("적절한 AttributeManager 가 없습니다. (ChatRoomType : " + chatRoomType + ")"));
	}
}
