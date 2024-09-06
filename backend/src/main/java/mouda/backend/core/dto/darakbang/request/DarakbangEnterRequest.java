package mouda.backend.core.dto.darakbang.request;

import jakarta.validation.constraints.NotNull;
import mouda.backend.core.domain.darakbang.Darakbang;
import mouda.backend.core.domain.darakbang.DarakBangMemberRole;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.member.Member;

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
