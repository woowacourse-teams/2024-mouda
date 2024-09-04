package mouda.backend.darakbang.presentation.request;

import jakarta.validation.constraints.NotNull;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.domain.Member;

public record DarakbangEnterRequest(
	@NotNull
	String nickname
) {
	public DarakbangMember toEntity(Darakbang darakbang, Member member) {
		return DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname(nickname)
			.role(DarakBangMemberRole.MEMBER)
			.build();
	}
}
