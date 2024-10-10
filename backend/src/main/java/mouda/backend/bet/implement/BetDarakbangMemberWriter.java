package mouda.backend.bet.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetDarakbangMemberWriter {

	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	public void updateLastReadChat(long betId, DarakbangMember darakbangMember, long lastReadChatId) {
		BetDarakbangMemberEntity betDarakbangMemberEntity = betDarakbangMemberRepository
			.findByBetIdAndDarakbangMemberId(betId, darakbangMember.getId())
			.orElseThrow(
				() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.BET_DARAKBANG_MEMBER_NOT_FOUND));
		betDarakbangMemberEntity.updateLastChat(lastReadChatId);
	}
}
