package mouda.backend.member.domain;

import java.util.Objects;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.moim.domain.Moim;
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

	@ManyToOne(fetch = FetchType.LAZY)
	private Moim moim;

	@Builder
	public Member(String nickname) {
		validateNickname(nickname);
		this.nickname = nickname;
	}

	private void validateNickname(String nickname) {
		if (nickname.isBlank()) {
			throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MEMBER_NICKNAME_NOT_EXISTS);
		}
		// if (nickname.length() >= NICKNAME_MAX_LENGTH) {
		// 	throw new MoimException(HttpStatus.BAD_REQUEST, MoimErrorMessage.MEMBER_NICKNAME_TOO_LONG);
		// }
	}

	public void joinMoim(Moim moim) {
		this.moim = moim;
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
