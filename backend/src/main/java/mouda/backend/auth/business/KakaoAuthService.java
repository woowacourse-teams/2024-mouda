package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.KakaoUserInfoProvider;
import mouda.backend.auth.presentation.request.KakaoConvertRequest;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private final KakaoUserInfoProvider userInfoProvider;
	private final MemberWriter memberWriter;
	private final MemberFinder memberFinder;

	public void convert(Member alternation, KakaoConvertRequest kakaoConvertRequest) {
		String identifier = userInfoProvider.getIdentifier(kakaoConvertRequest.code());
		Member target = memberFinder.findByIdentifier(identifier);
		memberWriter.updateLoginDetail(target.getId(), alternation.getLoginDetail());
		target.convert();
		memberWriter.withdraw(alternation);
	}
}
