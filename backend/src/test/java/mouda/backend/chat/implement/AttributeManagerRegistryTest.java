package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chat.domain.ChatRoomType;

@SpringBootTest
class AttributeManagerRegistryTest {

	@Autowired
	AttributeManagerRegistry attributeManagerRegistry;

	@Autowired
	MoimAttributeManager moimAttributeManager;

	@Autowired
	BetAttributeManager betAttributeManager;

	@DisplayName("채팅방 타입에 맞는 매니저를 가져온다.")
	@ParameterizedTest
	@CsvSource({
		"MOIM",
		"BET"
	})
	void getManager(ChatRoomType chatRoomType) {
		// given
		// when
		AttributeManager result = attributeManagerRegistry.getManager(chatRoomType);

		// then
		assertThat(result.support(chatRoomType)).isTrue();
	}

}
