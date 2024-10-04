package mouda.backend.bet.business;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.implement.BetWriter;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.bet.presentation.request.BetCreateRequest;
import mouda.backend.bet.presentation.response.BetFindAllResponse;
import mouda.backend.bet.presentation.response.BetFindAllResponses;
import mouda.backend.bet.presentation.response.BetFindResponse;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;

@SpringBootTest
class BetServiceTest extends DarakbangSetUp {

	@Autowired
	BetService betService;

	@Autowired
	BetWriter betWriter;

	@Autowired
	BetRepository betRepository;

	@Autowired
	BetDarakbangMemberRepository betDarakbangMemberRepository;

	@Autowired
	DarakbangMemberRepository darakbangMemberRepository;

	@DisplayName("조회된 안내면진다는 정렬되어있다.")
	@Test
	void findAll() {
		// given
		long darakbangId = darakbang.getId();
		BetEntity betEntity1 = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId(),
			LocalDateTime.now().plusMinutes(5));
		BetEntity betEntity2 = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId(),
			LocalDateTime.now().minusMinutes(3));
		BetEntity betEntity3 = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId(),
			LocalDateTime.now().plusMinutes(10));

		BetEntity savedBetEntity1 = betRepository.save(betEntity1);
		BetEntity savedBetEntity2 = betRepository.save(betEntity2);
		BetEntity savedBetEntity3 = betRepository.save(betEntity3);

		// when
		BetFindAllResponses findAllResponses = betService.findAllBets(darakbangId);

		// then
		List<Long> expectedOrder = Arrays.asList(savedBetEntity2.getId(), savedBetEntity1.getId(),
			savedBetEntity3.getId());
		List<Long> actualOrder = findAllResponses.bets().stream()
			.map(BetFindAllResponse::id)
			.toList();

		assertThat(actualOrder).isEqualTo(expectedOrder);
	}

	@DisplayName("추첨하지 않은 안내면진다를 상세 조회한다.")
	@Test
	void findNotDrawnBet() {
		// given
		long darakbangId = darakbang.getId();
		BetEntity drawnBetEntity = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId());
		BetEntity savedBetEntity = betRepository.save(drawnBetEntity);

		// when
		BetFindResponse bet = betService.findBet(darakbangId, savedBetEntity.getId(), darakbangAnna);

		// then
		assertThat(bet.isAnnounced()).isFalse();
	}

	@DisplayName("추첨한 안내면진다를 상세 조회한다.")
	@Test
	void findDrawnBet() {
		// given
		long darakbangId = darakbang.getId();
		BetEntity drawnBetEntity = BetEntityFixture.getDrawedBetEntity(darakbangId, darakbangAnna.getId());
		BetEntity savedBetEntity = betRepository.save(drawnBetEntity);

		// when
		BetFindResponse bet = betService.findBet(darakbangId, savedBetEntity.getId(), darakbangAnna);

		// then
		assertThat(bet.isAnnounced()).isTrue();
	}

	@DisplayName("새로운 안내면진다를 생성한다.")
	@Test
	void createBet() {
		// given
		BetCreateRequest betRequest = new BetCreateRequest("테니바보", 10);
		// when
		long createdBetId = betService.createBet(1L, betRequest, darakbangHogee);
		//then
		assertThat(createdBetId).isEqualTo(1L);
	}

	@DisplayName("안내면진다에 참여한다.")
	@Test
	void participateBet() {
		// given
		long darakbangId = 1L;
		BetCreateRequest betRequest = new BetCreateRequest("테니바보", 10);
		Bet bet = betRequest.toBet(2L);
		BetEntity betEntity = betRepository.save(BetEntity.create(bet, darakbangId));

		long betId = betEntity.getId();

		// when
		betService.participateBet(darakbangId, betId, darakbangHogee);

		//then
		List<BetDarakbangMemberEntity> entities = betDarakbangMemberRepository.findAll();
		assertThat(entities).hasSize(1);
	}

	@DisplayName("당첨자를 강제추첨한다.")
	@Test
	void drawBet() {
		// given
		Long darakbangId = darakbang.getId();
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId());
		BetEntity savedBetEntity = betRepository.save(betEntity);
		betWriter.participate(darakbangId, savedBetEntity.getId(), darakbangAnna);

		// when
		betService.drawBet(darakbangId, savedBetEntity.getId());

		//then
		Optional<BetEntity> drawnBetEntity = betRepository.findById(savedBetEntity.getId());
		assertThat(drawnBetEntity).isPresent();
		assertThat(drawnBetEntity.get().getLoserDarakbangMemberId()).isNotNull();
	}
}
