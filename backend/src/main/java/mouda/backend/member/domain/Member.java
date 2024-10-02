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
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;

@Entity
@Getter
@NoArgsConstructor
public class Member {

	private static final int NICKNAME_MAX_LENGTH = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nickname;

	@Embedded
	private LoginDetail loginDetail;

	@Builder
	public Member(String nickname, LoginDetail loginDetail) {
		this.loginDetail = loginDetail;
		validateNickname(nickname);
		this.nickname = nickname;
	}

	private void validateNickname(String nickname) {
		if (nickname.isBlank()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MEMBER_NICKNAME_NOT_EXISTS);
		}
		if (nickname.length() >= NICKNAME_MAX_LENGTH) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MEMBER_NICKNAME_TOO_LONG);
		}
	}

	public String getSocialLoginId() {
		return loginDetail.getSocialLoginId();
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
