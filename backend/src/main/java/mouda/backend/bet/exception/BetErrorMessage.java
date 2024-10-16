package mouda.backend.bet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BetErrorMessage {

	BET_DARAKBANG_MEMBER_NOT_FOUND("참여하지 않은 안내면진다입니다."),
	BET_NOT_FOUND("안내면진다가 존재하지 않습니다."),
	LOSER_NOT_FOUND("당첨자가 존재하지 않습니다.");
	
	private final String message;
}
