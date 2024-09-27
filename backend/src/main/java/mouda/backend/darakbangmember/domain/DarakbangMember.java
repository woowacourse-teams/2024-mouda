package mouda.backend.darakbangmember.domain;

import java.util.Objects;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;

@Entity
@Getter
@NoArgsConstructor
@Table(
	name = "darakbang_member",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"member_id", "darakbang_id"}
		)
	}
)
public class DarakbangMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Darakbang darakbang;

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DarakBangMemberRole role;

	@Builder
	public DarakbangMember(Darakbang darakbang, Long memberId, String nickname, DarakBangMemberRole role) {
		validateNickname(nickname);
		this.darakbang = darakbang;
		this.memberId = memberId;
		this.nickname = nickname;
		this.role = role;
	}

	private void validateNickname(String nickname) {
		if (nickname == null || nickname.isBlank()) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST, DarakbangMemberErrorMessage.NICKNAME_NOT_EXIST);
		}
	}

	public boolean isNotManager() {
		return role != DarakBangMemberRole.MANAGER;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DarakbangMember that = (DarakbangMember)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
