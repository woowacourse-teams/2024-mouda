package mouda.backend.common.fixture;

import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.domain.Member;

public class DarakbangMemberFixture {

	public static DarakbangMember getDarakbangManagerWithWooteco(Darakbang darakbang, Member member) {
		return DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname("호호기기")
			.role(DarakBangMemberRole.MANAGER)
			.build();
	}

	public static DarakbangMember getDarakbangMemberWithWooteco(Darakbang darakbang, Member member) {
		return DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname("소소파파")
			.role(DarakBangMemberRole.MEMBER)
			.build();
	}
}
