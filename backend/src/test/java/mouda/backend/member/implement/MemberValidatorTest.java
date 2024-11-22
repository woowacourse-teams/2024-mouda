package mouda.backend.member.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class MemberValidatorTest {

	@Autowired
	private MemberValidator memberValidator;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@DisplayName("관리자가 아니라면 예외를 던진다.")
	@Test
	void validateNotManager() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		DarakbangMember darakbangHogee = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));

		assertThatThrownBy(() -> memberValidator.validateNotManager(darakbangHogee))
			.isInstanceOf(DarakbangMemberException.class);
	}
}