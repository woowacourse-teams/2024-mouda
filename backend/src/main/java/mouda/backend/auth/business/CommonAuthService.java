package mouda.backend.auth.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberWriter;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CommonAuthService {

	private final MemberWriter memberWriter;

	public void withdraw(Member member) {
		memberWriter.withdraw(member);
	}
}
