package mouda.backend.moim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Entity
@Getter
@NoArgsConstructor
@Table(
	name = "chamyo",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"moim_id", "darakbang_member_id"}
		)
	}
)
public class Chamyo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Moim moim;

	@ManyToOne
	@JoinColumn(nullable = false)
	private DarakbangMember darakbangMember;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MoimRole moimRole;

	private long lastReadChatId;

	@Builder
	public Chamyo(Moim moim, DarakbangMember darakbangMember, MoimRole moimRole) {
		this.moim = moim;
		this.darakbangMember = darakbangMember;
		this.moimRole = moimRole;
		this.lastReadChatId = 0L;
	}

	public void updateLastChat(Long lastReadChatId) {
		this.lastReadChatId = lastReadChatId;
	}
}
