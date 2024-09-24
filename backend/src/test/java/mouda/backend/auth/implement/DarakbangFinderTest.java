package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;

public class DarakbangFinderTest {

	@Mock
	DarakbangRepository darakbangRepository;

	@InjectMocks
	DarakbangFinder darakbangFinder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Mock 초기화
	}

	@Test
	@DisplayName("Darakbang을 정상적으로 찾는 경우")
	void findDarakbang_success() {
		// given
		long darakbangId = 1L;
		Darakbang expectedDarakbang = new Darakbang("테스트 다락방", "W12TE12");
		given(darakbangRepository.findById(darakbangId)).willReturn(Optional.of(expectedDarakbang));

		// when
		Darakbang foundDarakbang = darakbangFinder.find(darakbangId);

		// then
		assertThat(foundDarakbang).isEqualTo(expectedDarakbang);
	}

	@Test
	@DisplayName("Darakbang을 찾지 못한 경우 예외가 발생한다")
	void findDarakbang_notFound() {
		// given
		long darakbangId = 1L;
		given(darakbangRepository.findById(darakbangId)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> darakbangFinder.find(darakbangId))
			.isInstanceOf(AuthException.class)
			.hasMessage(AuthErrorMessage.DARAKBANG_NOT_FOUND.getMessage())
			.hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);
	}
}
