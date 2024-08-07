package mouda.backend.please.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Please please;

	@Builder
	public Interest(Member member, Please please) {
		this.member = member;
		this.please = please;
	}
}
