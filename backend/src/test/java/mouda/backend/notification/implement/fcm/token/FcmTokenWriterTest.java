package mouda.backend.notification.implement.fcm.token;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;

@SpringBootTest
class FcmTokenWriterTest extends DarakbangSetUp {

	@Autowired
	private FcmTokenRepository fcmTokenRepository;

	@Autowired
	private FcmTokenWriter fcmTokenWriter;

	@DisplayName("토큰 등록 / 갱신 테스트")
	@Nested
	class SaveOrRefreshTest {

		@DisplayName("토큰이 존재하지 않는 경우 새로 등록한다.")
		@Test
		void saveToken() {
			// given
			String token = "testToken";

			// when
			fcmTokenWriter.saveOrRefresh(darakbangHogee, token);

			// then
			List<FcmTokenEntity> results = fcmTokenRepository.findAllByMemberId(darakbangHogee.getMemberId());

			assertThat(results).hasSize(1);
			assertThat(results).extracting(FcmTokenEntity::getToken).containsExactly(token);
		}

		@DisplayName("토큰이 존재하는 경우 갱신한다.")
		@Test
		void refreshToken() {
			// given
			FcmTokenEntity existToken = fcmTokenRepository.save(FcmTokenEntity.builder()
				.memberId(darakbangHogee.getMemberId())
				.token("testToken")
				.build());

			// when
			fcmTokenWriter.saveOrRefresh(darakbangHogee, existToken.getToken());

			// then
			List<FcmTokenEntity> results = fcmTokenRepository.findAllByMemberId(darakbangHogee.getMemberId());
			assertThat(results).hasSize(1);

			FcmTokenEntity result = results.get(0);
			assertThat(result.getLastUpdated()).isAfter(existToken.getLastUpdated());
		}
	}

	@DisplayName("입력된 모든 토큰을 삭제한다.")
	@Test
	void deleteAllTest() {
		// given
		String token1 = "token1";
		String token2 = "token2";

		fcmTokenRepository.save(FcmTokenEntity.builder()
			.memberId(darakbangHogee.getMemberId())
			.token(token1)
			.build());

		fcmTokenRepository.save(FcmTokenEntity.builder()
			.memberId(darakbangAnna.getMemberId())
			.token(token2)
			.build());

		// when
		List<String> tokens = List.of(token1, token2);
		fcmTokenWriter.deleteAll(tokens);

		// then
		assertThat(fcmTokenRepository.findAll()).isEmpty();
	}
}
