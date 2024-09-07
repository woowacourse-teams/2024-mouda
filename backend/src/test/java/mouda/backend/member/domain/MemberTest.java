package mouda.backend.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.common.fixture.MemberFixture;

class MemberTest {

	@DisplayName("회원을 정상적으로 생성한다.")
	@Test
	void createMember() {
		assertDoesNotThrow(MemberFixture::getAnna);
	}
}
