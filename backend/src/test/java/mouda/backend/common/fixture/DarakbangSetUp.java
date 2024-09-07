package mouda.backend.common.fixture;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
public class DarakbangSetUp {

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	protected Darakbang darakbang;
	protected Darakbang mouda;
	protected Member hogee;
	protected Member anna;
	protected DarakbangMember darakbangHogee;
	protected DarakbangMember moudaHogee;
	protected DarakbangMember darakbangAnna;

	@BeforeEach
	void setUp() {
		darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
		mouda = darakbangRepository.save(DarakbangFixture.getDarakbangWithMouda());

		hogee = memberRepository.save(MemberFixture.getHogee());
		darakbangHogee = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, hogee));
		moudaHogee = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(mouda, hogee));

		anna = memberRepository.save(MemberFixture.getAnna());
		darakbangAnna = darakbangMemberRepository.save(
			DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang, anna));
	}
}
