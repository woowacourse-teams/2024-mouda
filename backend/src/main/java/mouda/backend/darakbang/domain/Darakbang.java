package mouda.backend.darakbang.domain;

import java.util.Objects;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;

@Entity
@Getter
@NoArgsConstructor
public class Darakbang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String code;

	@Builder
	public Darakbang(String name, String code) {
		validateName(name);
		this.name = name;
		this.code = code;
	}

	private void validateName(String name) {
		if (name == null || name.isBlank()) {
			throw new DarakbangException(HttpStatus.BAD_REQUEST, DarakbangErrorMessage.NAME_NOT_EXIST);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Darakbang darakbang = (Darakbang)o;
		return Objects.equals(id, darakbang.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
