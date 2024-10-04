package mouda.backend.member.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class LoginDetail {

	@Enumerated(EnumType.STRING)
	private OauthType oauthType;

	private String socialLoginId;

	protected LoginDetail() {
	}

	public LoginDetail(OauthType oauthType, String socialLoginId) {
		this.oauthType = oauthType;
		this.socialLoginId = socialLoginId;
	}
}
