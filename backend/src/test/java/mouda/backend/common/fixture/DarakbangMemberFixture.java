package mouda.backend.common.fixture;

import mouda.backend.core.domain.darakbang.Darakbang;
import mouda.backend.core.domain.darakbang.DarakBangMemberRole;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.member.Member;

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
