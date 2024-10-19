package mouda.backend.member.domain;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LoginDetail that = (LoginDetail)o;
		return oauthType == that.oauthType && Objects.equals(socialLoginId, that.socialLoginId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(oauthType, socialLoginId);
	}
}
