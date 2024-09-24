package mouda.backend.auth.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;

@Component
@RequiredArgsConstructor
public class DarakbangFinder {

	private final DarakbangRepository darakbangRepository;

	public Darakbang find(long darakbangId) {
		return darakbangRepository.findById(darakbangId)
			.orElseThrow(() -> new AuthException(HttpStatus.NOT_FOUND, AuthErrorMessage.DARAKBANG_NOT_FOUND));
	}
}
