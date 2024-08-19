package mouda.backend.chamyo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;

@Entity
@Getter
@NoArgsConstructor
public class Chamyo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Moim moim;

	@ManyToOne
	@JoinColumn(nullable = false)
	private DarakbangMember member;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MoimRole moimRole;

	private long lastReadChatId;

	@Builder
	public Chamyo(Moim moim, DarakbangMember member, MoimRole moimRole) {
		this.moim = moim;
		this.member = member;
		this.moimRole = moimRole;
		this.lastReadChatId = 0L;
	}

	public void updateLastChat(Long lastReadChatId) {
		this.lastReadChatId = lastReadChatId;
	}
}
