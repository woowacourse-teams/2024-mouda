package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.Attributes;
import mouda.backend.chat.domain.BetAttributes;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakbangMember;

class BetAttributeManagerTest {

	@Mock
	private BetFinder betFinder;

	@Mock
	private BetDarakbangMemberRepository betDarakbangMemberRepository;

	@InjectMocks
	private BetAttributeManager betAttributeManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@DisplayName("안내면진다 채팅방을 지원한다.")
	@Test
	void support_shouldReturnTrueForBetType() {
		// given
		ChatRoomType chatRoomType = ChatRoomType.BET;

		// when
		boolean result = betAttributeManager.support(chatRoomType);

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("안내면진다 채팅방이 아닌 채팅방은 지원하지 않는다.")
	@Test
	void support_shouldReturnFalseForNonBetType() {
		// given
		ChatRoomType chatRoomType = ChatRoomType.MOIM;

		// when
		boolean result = betAttributeManager.support(chatRoomType);

		// then
		assertThat(result).isFalse();
	}

	@DisplayName("안내면진다 어트리뷰트를 반환한다.")
	@Test
	void create_shouldReturnBetAttributes() {
		// given
		ChatRoom chatRoom = mock(ChatRoom.class);
		DarakbangMember darakbangMember = mock(DarakbangMember.class);
		Darakbang darakbang = mock(Darakbang.class);
		Bet bet = mock(Bet.class);

		when(chatRoom.getTargetId()).thenReturn(1L);
		when(darakbangMember.getDarakbang()).thenReturn(darakbang);
		when(darakbang.getId()).thenReturn(1L);

		when(betFinder.find(1L, 1L)).thenReturn(bet);
		when(bet.getBetDetails()).thenReturn(
			new BetDetails(1L, "test bet", LocalDateTime.of(2024, 6, 5, 12, 3), 1L, null));
		when(bet.isLoser(darakbangMember.getId())).thenReturn(true);
		when(bet.getId()).thenReturn(1L);
		when(bet.getLoserId()).thenReturn(2L);
		when(bet.getMoimerId()).thenReturn(1L);

		DarakbangMember loserMember = mock(DarakbangMember.class);
		when(loserMember.getNickname()).thenReturn("loserNick");
		when(loserMember.getProfile()).thenReturn("profilePic");
		BetEntity betEntity = BetEntity.builder()
			.id(1L)
			.bettingTime(LocalDateTime.now())
			.title("test bet")
			.moimerId(1L)
			.loserDarakbangMemberId(2L)
			.darakbangId(2L)
			.build();
		when(betDarakbangMemberRepository.findByBetIdAndDarakbangMemberId(1L, 2L))
			.thenReturn(Optional.of(new BetDarakbangMemberEntity(loserMember, betEntity)));

		// when
		Attributes attributes = betAttributeManager.create(chatRoom, darakbangMember);

		// then
		assertThat(attributes).isInstanceOf(BetAttributes.class);
		BetAttributes betAttributes = (BetAttributes)attributes;
		assertThat(betAttributes.isLoser()).isTrue();
		assertThat(betAttributes.getBetId()).isEqualTo(1L);
		assertThat(betAttributes.getLoser().getNickname()).isEqualTo("loserNick");
		assertThat(betAttributes.getLoser().getProfile()).isEqualTo("profilePic");
	}

	@DisplayName("참여하지 않는 안내면진다의 어트리뷰트를 요청하면 예외를 발생한다.")
	@Test
	void create_shouldThrowExceptionWhenLoserNotFound() {
		// given
		ChatRoom chatRoom = mock(ChatRoom.class);
		DarakbangMember darakbangMember = mock(DarakbangMember.class);
		Darakbang darakbang = mock(Darakbang.class);
		Bet bet = mock(Bet.class);

		when(chatRoom.getTargetId()).thenReturn(1L);
		when(darakbangMember.getDarakbang()).thenReturn(darakbang);
		when(darakbang.getId()).thenReturn(1L);

		when(betFinder.find(1L, 1L)).thenReturn(bet);
		when(bet.getLoserId()).thenReturn(2L);

		when(betDarakbangMemberRepository.findByBetIdAndDarakbangMemberId(1L, 2L))
			.thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> betAttributeManager.create(chatRoom, darakbangMember))
			.isInstanceOf(ChatException.class);
	}
}
