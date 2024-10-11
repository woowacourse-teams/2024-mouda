package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mouda.backend.chat.domain.Attributes;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.MoimAttributes;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.MoimFinder;

class MoimAttributeManagerTest {

	@Mock
	private MoimFinder moimFinder;

	@Mock
	private ChamyoFinder chamyoFinder;

	@InjectMocks
	private MoimAttributeManager moimAttributeManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void support_shouldReturnTrueForMoimType() {
		// given
		ChatRoomType chatRoomType = ChatRoomType.MOIM;

		// when
		boolean result = moimAttributeManager.support(chatRoomType);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void support_shouldReturnFalseForNonMoimType() {
		// given
		ChatRoomType chatRoomType = ChatRoomType.BET;

		// when
		boolean result = moimAttributeManager.support(chatRoomType);

		// then
		assertThat(result).isFalse();
	}

	@Test
	void create_shouldReturnMoimAttributes() {
		// given
		ChatRoom chatRoom = mock(ChatRoom.class);
		DarakbangMember darakbangMember = mock(DarakbangMember.class);
		Moim moim = mock(Moim.class);
		Chamyo chamyo = mock(Chamyo.class);

		when(chatRoom.getTargetId()).thenReturn(1L);
		when(darakbangMember.getDarakbang()).thenReturn(mock(Darakbang.class));
		when(darakbangMember.getDarakbang().getId()).thenReturn(1L);

		when(moimFinder.read(1L, 1L)).thenReturn(moim);
		when(chamyoFinder.read(moim, darakbangMember)).thenReturn(chamyo);
		when(moim.getPlace()).thenReturn("우테코 잠실캠");
		when(moim.isPastMoim()).thenReturn(false);
		when(moim.getDescription()).thenReturn("설명");
		when(moim.getDate()).thenReturn(mock(java.time.LocalDate.class));
		when(moim.getDate().toString()).thenReturn("3333-01-01");
		when(moim.getTime()).thenReturn(mock(java.time.LocalTime.class));
		when(moim.getTime().toString()).thenReturn("12:12:00");
		when(moim.getId()).thenReturn(12L);
		when(chamyo.getMoimRole()).thenReturn(MoimRole.MOIMER);

		// when
		Attributes attributes = moimAttributeManager.create(chatRoom, darakbangMember);

		// then
		assertThat(attributes).isInstanceOf(MoimAttributes.class);
		MoimAttributes moimAttributes = (MoimAttributes)attributes;
		assertThat(moimAttributes.getPlace()).isEqualTo("우테코 잠실캠");
		assertThat(moimAttributes.getIsMoimer()).isTrue();
		assertThat(moimAttributes.getIsStarted()).isFalse();
		assertThat(moimAttributes.getDescription()).isEqualTo("설명");
		assertThat(moimAttributes.getDate()).isEqualTo("3333-01-01");
		assertThat(moimAttributes.getTime()).isEqualTo("12:12:00");
		assertThat(moimAttributes.getMoimId()).isEqualTo(12L);
	}
}
