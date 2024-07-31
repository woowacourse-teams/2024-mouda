package mouda.backend.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.moim.exception.MoimException;

class MemberTest {
	private static final String NICNKAME = "안나";

	@DisplayName("회원을 정상적으로 생성한다.")
	@Test
	void createMember() {
		assertDoesNotThrow(() -> Member.builder()
			.nickname(NICNKAME)
			.build());
	}

	@DisplayName("닉네임을 입력하지 않아 회원 생성에 실패한다.")
	@Test
	void failToCreateMemberWhenNicknameIsBlank() {
		Assertions.assertThrows(MoimException.class, () -> Member.builder()
			.nickname("")
			.build());
	}

	@DisplayName("닉네임이 제한 길이를 초과하여 회원 생성에 실패한다.")
	@Test
	void failToCreateMemberWhenNicknameIsTooLong() {
		String longNickname = "a".repeat(11);
		Assertions.assertThrows(MoimException.class, () -> Member.builder()
			.nickname(longNickname)
			.build());
	}
}
