package mouda.backend.bet.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Participant;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class ParticipantFinder {

    private final BetDarakbangMemberRepository betDarakbangMemberRepository;

    public List<Participant> findAllByBetEntity(BetEntity betEntity) {
        List<DarakbangMember> darakbangMembers = betDarakbangMemberRepository.findAllDarakbangMemberByBetId(
            betEntity.getId());
        return darakbangMembers.stream()
            .map(darakbangMember -> new Participant(darakbangMember.getId(), darakbangMember.getNickname()))
            .toList();
    }
}
