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
			.profile("profile")
			.description("description")
			.role(DarakBangMemberRole.MANAGER)
			.build();
	}

	public static DarakbangMember getDarakbangMemberWithWooteco(Darakbang darakbang, Member member) {
		return DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname("소소파파")
			.profile("profile")
			.description("description")
			.role(DarakBangMemberRole.MEMBER)
			.build();
	}

	public static DarakbangMember getDarakbangOutsiderWithWooteco(Darakbang darakbang, Member member) {
		return DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname("치코치코니")
			.profile("profile")
			.description("description")
			.role(DarakBangMemberRole.OUTSIDER)
			.build();
	}
}
