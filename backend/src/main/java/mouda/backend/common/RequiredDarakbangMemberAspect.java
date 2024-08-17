package mouda.backend.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Aspect
@Component
@RequiredArgsConstructor
public class RequiredDarakbangMemberAspect {

	private final DarakbangMemberRepository darakbangMemberRepository;

	@Before(value = "bean(*Controller) && args(darakbangId, member,..)")
	public void getDarakbangMember(Long darakbangId, Member member) {
		if (!darakbangMemberRepository.existsByDarakbangIdAndMemberId(darakbangId, member.getId())) {
			throw new DarakbangMemberException(HttpStatus.FORBIDDEN, DarakbangMemberErrorMessage.MEMBER_NOT_EXIST);
		}
	}
}
