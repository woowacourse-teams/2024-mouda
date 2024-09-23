package mouda.backend.member.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class LoginDetail {

	private OauthType oauthType;
	private Long socialLoinId;

	protected LoginDetail() {
	}

	public LoginDetail(OauthType oauthType, Long socialLoinId) {
		this.oauthType = oauthType;
		this.socialLoinId = socialLoinId;
	}
}
