package mouda.backend.darakbangmember.implement;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Component
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

	public DarakbangMember saveMember(Darakbang darakbang, String nickname, Member member) {
		DarakbangMember entity = DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname(nickname)
			.role(DarakBangMemberRole.MEMBER)
			.build();
		try {
			return darakbangMemberRepository.save(entity);
		} catch (DataIntegrityViolationException exception) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST,
				DarakbangMemberErrorMessage.MEMBER_ALREADY_EXIST);
		}
	}
}
