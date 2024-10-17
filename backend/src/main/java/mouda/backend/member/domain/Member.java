package mouda.backend.member.domain;

import java.util.Objects;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.member.exception.MemberErrorMessage;
import mouda.backend.member.exception.MemberException;

@Entity
@Getter
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Embedded
	private LoginDetail loginDetail;

	@Builder
	public Member(String name, LoginDetail loginDetail) {
		this.loginDetail = loginDetail;
		validateName(name);
		this.name = name;
	}

	private void validateName(String name) {
		if (name.isBlank()) {
			throw new MemberException(HttpStatus.BAD_REQUEST, MemberErrorMessage.MEMBER_NAME_NOT_EXISTS);
		}
	}

	public String getSocialLoginId() {
		return loginDetail.getSocialLoginId();
	}

	public String getOauthType() {
		return loginDetail.getOauthType().toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member)o;
		return Objects.equals(id, member.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
