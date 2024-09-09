package mouda.backend.darakbangmember.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMembers;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class DarakbangMemberFinder {

	private final DarakbangMemberRepository darakbangMemberRepository;

	public List<Darakbang> findAllByMember(Member member) {
		return darakbangMemberRepository.findAllByMemberId(member.getId())
			.stream()
			.map(DarakbangMember::getDarakbang)
			.toList();
	}

	public DarakbangMembers findAllByDarakbangId(Long darakbangId) {
		return new DarakbangMembers(darakbangMemberRepository.findAllByDarakbangId(darakbangId));
	}
}
