import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.implement.ParticipantResolverRegistry;
import mouda.backend.chat.implement.ParticipantsResolver;

class ParticipantResolverRegistryTest {

	@Mock
	private ParticipantsResolver mockResolver1;

	@Mock
	private ParticipantsResolver mockResolver2;

	@InjectMocks
	private ParticipantResolverRegistry participantResolverRegistry;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@DisplayName("MOIM 타입 리졸버를 반환한다.")
	@Test
	void getResolver_shouldReturnCorrectResolverForSupportedChatRoomType() {
		// given
		ChatRoomType chatRoomType = ChatRoomType.MOIM;

		// when
		when(mockResolver1.support(chatRoomType)).thenReturn(false);
		when(mockResolver2.support(chatRoomType)).thenReturn(true);

		List<ParticipantsResolver> resolvers = Arrays.asList(mockResolver1, mockResolver2);
		participantResolverRegistry = new ParticipantResolverRegistry(resolvers);

		// when
		ParticipantsResolver resolver = participantResolverRegistry.getResolver(chatRoomType);

		// then
		assertThat(resolver).isEqualTo(mockResolver2);
	}

	@DisplayName("BET 타입 리졸버를 반환한다.")
	@Test
	void getResolver_shouldThrowExceptionForUnsupportedChatRoomType() {
		// given
		ChatRoomType chatRoomType = ChatRoomType.BET;

		// when
		when(mockResolver1.support(chatRoomType)).thenReturn(false);
		when(mockResolver2.support(chatRoomType)).thenReturn(false);

		List<ParticipantsResolver> resolvers = Arrays.asList(mockResolver1, mockResolver2);
		participantResolverRegistry = new ParticipantResolverRegistry(resolvers);

		// then
		assertThatThrownBy(() -> participantResolverRegistry.getResolver(chatRoomType))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
