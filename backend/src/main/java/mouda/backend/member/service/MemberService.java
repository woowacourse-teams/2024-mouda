package mouda.backend.member.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.member.dto.response.MemberFindResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

	public MemberFindResponse findMyInfo(Member member) {
		String profile = "";
		return new MemberFindResponse(member.getNickname(), profile);
	}
}
