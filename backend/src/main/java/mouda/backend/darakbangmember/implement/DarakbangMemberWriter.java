package mouda.backend.darakbangmember.implement;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class DarakbangMemberWriter {

	private final DarakbangMemberRepository darakbangMemberRepository;

	public DarakbangMember saveManager(Darakbang darakbang, String nickname, Member member) {
		DarakbangMember darakbangMember = DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname(nickname)
			.role(DarakBangMemberRole.MANAGER)
			.build();
		return darakbangMemberRepository.save(darakbangMember);
	}
}
