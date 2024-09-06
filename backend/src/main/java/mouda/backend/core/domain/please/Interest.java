package mouda.backend.core.domain.please;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.api.please.exception.PleaseErrorMessage;
import mouda.backend.api.please.exception.PleaseException;

@Entity
@Getter
@NoArgsConstructor
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private DarakbangMember darakbangMember;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Please please;

	@Builder
	public Interest(DarakbangMember darakbangMember, Please please) {
		validateMember(darakbangMember);
		validatePlease(please);
		this.darakbangMember = darakbangMember;
		this.please = please;
	}

	private void validateMember(DarakbangMember darakbangMember) {
		if (darakbangMember == null) {
			throw new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.MEMBER_NOT_FOUND);
		}
	}

	private void validatePlease(Please please) {
		if (please == null) {
			throw new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND);
		}
	}
}
