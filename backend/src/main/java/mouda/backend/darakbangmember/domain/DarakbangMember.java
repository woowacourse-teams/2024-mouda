package mouda.backend.darakbangmember.domain;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor
public class DarakbangMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Darakbang darakbang;

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DarakBangMemberRole role;

	@Builder
	public DarakbangMember(Darakbang darakbang, Member member, String nickname, DarakBangMemberRole role) {
		validateNickname(nickname);
		this.darakbang = darakbang;
		this.member = member;
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

	public String getDarakbangName() {
		return darakbang.getName();
	}
}
