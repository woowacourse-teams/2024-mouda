package mouda.backend.darakbang.inplement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;

@Service
@RequiredArgsConstructor
public class DarakbangValidator {
	private final DarakbangRepository darakbangRepository;

	public void validateAlreadyExistsName(String name) {
		if (darakbangRepository.existsByName(name)) {
			throw new DarakbangException(HttpStatus.BAD_REQUEST, DarakbangErrorMessage.NAME_ALREADY_EXIST);
		}
	}
}
