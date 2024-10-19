package mouda.backend.member.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class LoginDetail {

	@Enumerated(EnumType.STRING)
	private OauthType oauthType;

	@Column(unique = true)
	private String identifier;

	protected LoginDetail() {
	}

	public LoginDetail(OauthType oauthType, String identifier) {
		this.oauthType = oauthType;
		this.identifier = identifier;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LoginDetail that = (LoginDetail)o;
		return oauthType == that.oauthType && Objects.equals(identifier, that.identifier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(oauthType, identifier);
	}
}
