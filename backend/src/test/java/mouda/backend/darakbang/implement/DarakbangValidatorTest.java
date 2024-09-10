package mouda.backend.darakbang.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangValidatorTest {

	@Autowired
	private DarakbangValidator darakbangValidator;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("다락방 이름이 중복되면 생성에 실패한다.")
	@Test
	void validateDuplicatedName() {
		Darakbang darakbang = DarakbangFixture.getDarakbangWithWooteco();
		darakbangRepository.save(darakbang);

		assertThatThrownBy(() -> darakbangValidator.validateAlreadyExistsName(darakbang.getName()))
			.isInstanceOf(DarakbangException.class);
	}

	@DisplayName("다락방에 이미 가입한 멤버라면 가입에 실패한다.")
	@Test
	void validateDuplicatedNickname() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));

		assertThatThrownBy(() -> darakbangValidator.validateCanEnterDarakbang(darakbang, "안나", hogee))
			.isInstanceOf(DarakbangMemberException.class);
	}

	@DisplayName("다락방에 해당 닉네임이 이미 존재한다면 가입에 실패한다.")
	@Test
	void validateAlreadyEnteredMember() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));

		Member anna = memberRepository.save(MemberFixture.getAnna());

		assertThatThrownBy(() -> darakbangValidator.validateCanEnterDarakbang(darakbang, "소소파파", anna))
			.isInstanceOf(DarakbangMemberException.class);
	}

	@DisplayName("이미 사용 중인 참여 코드를 발급하면 다락방 생성에 실패한다. ")
	@Test
	void validateAlreadyExistsCode() {
		Member hogee = memberRepository.save(MemberFixture.getHogee());
		Darakbang wooteco = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangMemberWithWooteco(wooteco, hogee));

		assertThatThrownBy(() -> darakbangValidator.validateAlreadyExistsCode(wooteco.getCode()))
			.isInstanceOf(DarakbangException.class);
	}
}