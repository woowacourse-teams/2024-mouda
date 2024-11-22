package mouda.backend.bet.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.domain.Participant;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;

@SpringBootTest
class ParticipantFinderTest extends DarakbangSetUp {

    @Autowired
    ParticipantFinder participantFinder;

    @Autowired
    BetRepository betRepository;

    @Autowired
    BetDarakbangMemberRepository betDarakbangMemberRepository;

    @DisplayName("안내면진다에 참여한 참여자 전원을 조회한다.")
    @Test
    void findAllByBetEntity() {
        // given
        BetEntity betEntity = BetEntityFixture.getBetEntity(darakbang.getId(), darakbangAnna.getId());
        betRepository.save(betEntity);

        BetDarakbangMemberEntity betDarakbangMemberEntity1 = new BetDarakbangMemberEntity(darakbangAnna, betEntity);
        betDarakbangMemberRepository.save(betDarakbangMemberEntity1);
        BetDarakbangMemberEntity betDarakbangMemberEntity2 = new BetDarakbangMemberEntity(darakbangHogee, betEntity);
        betDarakbangMemberRepository.save(betDarakbangMemberEntity2);

        // when
        List<Participant> participants = participantFinder.findAllByBetEntity(betEntity);

        //then
        assertThat(participants).hasSize(2);
    }
}
