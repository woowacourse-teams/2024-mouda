package mouda.backend.darakbangmember.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMembers;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Component
@RequiredArgsConstructor
public class DarakbangMemberFinder {

	private final DarakbangMemberRepository darakbangMemberRepository;

	public DarakbangMember find(Darakbang darakbang, Member member) {
		return darakbangMemberRepository.findByDarakbangIdAndMemberId(darakbang.getId(), member.getId())
			.orElseThrow(() ->
				new DarakbangMemberException(HttpStatus.UNAUTHORIZED, DarakbangMemberErrorMessage.MEMBER_NOT_EXIST));
	}

	public List<Darakbang> findAllByMember(Member member) {
		return darakbangMemberRepository.findAllByMemberId(member.getId())
			.stream()
			.map(DarakbangMember::getDarakbang)
			.toList();
	}

	public DarakbangMembers findAllDarakbangMembers(Long darakbangId) {
		return new DarakbangMembers(darakbangMemberRepository.findAllByDarakbangId(darakbangId));
	}

	public DarakBangMemberRole findDarakbangMemberRole(Long darakbangId, Long memberId) {
		Optional<DarakbangMember> optionalDarakbangMember = darakbangMemberRepository
			.findByDarakbangIdAndMemberId(darakbangId, memberId);

		if (optionalDarakbangMember.isPresent()) {
			DarakbangMember darakbangMember = optionalDarakbangMember.get();
			return darakbangMember.getRole();
		}
		return DarakBangMemberRole.OUTSIDER;
	}
}
