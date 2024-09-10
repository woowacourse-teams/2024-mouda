package mouda.backend.darakbangmember.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangMemberWriterTest {

	@Autowired
	private DarakbangMemberWriter darakbangMemberWriter;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@DisplayName("정상적으로 매니저 신분으로 다락방 가입에 성공한다.")
	@Test
	void saveManager() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang wooteco = DarakbangFixture.getDarakbangWithWooteco();
		darakbangRepository.save(wooteco);

		DarakbangMember darakbangMember = darakbangMemberWriter.saveManager(wooteco, "호기기", hogee);

		assertThat(darakbangMember.getId()).isEqualTo(1L);
	}

	@DisplayName("닉네임이 존재하지 않으면 다락방 참여에 실패한다.")
	@NullAndEmptySource
	@ParameterizedTest
	void validateWithoutNickname(String nickname) {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang wooteco = DarakbangFixture.getDarakbangWithWooteco();
		darakbangRepository.save(wooteco);

		assertThatThrownBy(() -> darakbangMemberWriter.saveManager(wooteco, nickname, hogee))
			.isInstanceOf(DarakbangMemberException.class);
	}

	@DisplayName("정상적으로 회원 신분으로 다락방 가입에 성공한다.")
	@Test
	void saveMember() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang wooteco = DarakbangFixture.getDarakbangWithWooteco();
		darakbangRepository.save(wooteco);

		DarakbangMember darakbangMember = darakbangMemberWriter.saveMember(wooteco, "호기기", hogee);

		assertThat(darakbangMember.getId()).isEqualTo(1L);
	}
}