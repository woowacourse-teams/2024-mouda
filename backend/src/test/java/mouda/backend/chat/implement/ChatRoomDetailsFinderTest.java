package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomDetails;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Participant;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatRoomDetailsFinderTest extends DarakbangSetUp {

	@Autowired
	private ChatRoomDetailsFinder chatRoomDetailsFinder;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private BetDarakbangMemberRepository betDarakbangMemberRepository;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@DisplayName("모임 타입 채팅룸 디테일을 반환한다.")
	@Test
	void find_moimChatRoomType() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		Chamyo annaChamyo = Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build();
		Chamyo hogeeChamyo = Chamyo.builder()
			.moim(moim)
			.darakbangMember(darakbangHogee)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(annaChamyo);
		chamyoRepository.save(hogeeChamyo);

		ChatRoomEntity chatRoomEntity = ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim.getId(), darakbang.getId());
		ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(chatRoomEntity);
		ChatRoom chatRoom = new ChatRoom(savedChatRoomEntity);

		// when
		ChatRoomDetails chatRoomDetails = chatRoomDetailsFinder.find(darakbang.getId(), chatRoom.getId(), darakbangAnna);

		// then
		assertThat(chatRoomDetails.getChatRoomType()).isEqualTo(ChatRoomType.MOIM);
		assertThat(chatRoomDetails.getTitle()).isEqualTo("커피 마실 사람?");
		assertThat(chatRoomDetails.getId()).isEqualTo(chatRoom.getId());
		assertThat(chatRoomDetails.getParticipants()).containsExactly(new Participant("anna", "profile", "MOIMER"), new Participant("hogee", "profile", "MOIMEE"));
		assertThat(chatRoomDetails.getAttributes())
			.containsExactlyInAnyOrderEntriesOf(getExpectedMoimAttributes(savedMoim));
	}

	private Map<String, Object> getExpectedMoimAttributes(Moim moim) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("title", moim.getTitle());
		attributes.put("place", moim.getPlace());
		attributes.put("isMoimer", true);
		attributes.put("isStarted", false);
		attributes.put("description", moim.getDescription());
		attributes.put("date", moim.getDate().toString());
		attributes.put("time", moim.getTime().withNano(0).toString());
		attributes.put("moimId", 1L);
		return attributes;
	}

	@DisplayName("안내면진다 채팅룸 디테일을 반환한다.")
	@Test
	void find_betChatRoomType() {
		// given
		BetEntity betEntity = BetEntityFixture.getDrawedBetEntity(darakbang.getId(), darakbangAnna.getId());
		BetEntity savedBetEntity = betRepository.save(betEntity);

		BetDarakbangMemberEntity annaEntity = new BetDarakbangMemberEntity(darakbangAnna, savedBetEntity);
		BetDarakbangMemberEntity hogeeEntity = new BetDarakbangMemberEntity(darakbangHogee, savedBetEntity);
		betDarakbangMemberRepository.save(annaEntity);
		betDarakbangMemberRepository.save(hogeeEntity);

		ChatRoomEntity chatRoomEntity = ChatRoomEntityFixture.getChatRoomEntityOfBet(betEntity.getId(), darakbang.getId());
		ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(chatRoomEntity);
		ChatRoom chatRoom = new ChatRoom(savedChatRoomEntity);

		// when
		ChatRoomDetails chatRoomDetails = chatRoomDetailsFinder.find(darakbang.getId(), chatRoom.getId(), darakbangAnna);

		// then
		assertThat(chatRoomDetails.getChatRoomType()).isEqualTo(ChatRoomType.BET);
		assertThat(chatRoomDetails.getTitle()).isEqualTo("테바바보");
		assertThat(chatRoomDetails.getId()).isEqualTo(chatRoom.getId());
		assertThat(chatRoomDetails.getParticipants()).containsExactly(new Participant("anna", "profile", "MOIMER"), new Participant("hogee", "profile", "MOIMEE"));
		assertThat(chatRoomDetails.getAttributes())
			.containsExactlyInAnyOrderEntriesOf(getExpectedBetAttributes(savedBetEntity));
	}

	private Map<String, Object> getExpectedBetAttributes(BetEntity bet) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("title", bet.getTitle());
		attributes.put("isLoser", true);
		attributes.put("betId", bet.getId());
		attributes.put("loser", Map.of(
			"nickname", "anna",
			"profile", "profile",
			"role", "MOIMER"
		));
		return attributes;
	}
}
