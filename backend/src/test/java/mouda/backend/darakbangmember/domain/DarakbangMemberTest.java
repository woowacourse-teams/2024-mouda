package mouda.backend.darakbangmember.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;

class DarakbangMemberTest extends DarakbangSetUp {

	@DisplayName("닉네임은 10글자 미만 이여야 한다.")
	@Test
	void invalidNickName() {
		assertThatThrownBy(() -> new DarakbangMember(
			darakbang, 10L,
			"hogeehogeehgoee", " ",
			" ", DarakBangMemberRole.MEMBER))
			.isInstanceOf(DarakbangMemberException.class)
			.hasMessage("닉네임은 9글자 이하로만 가능합니다.");
	}

}
