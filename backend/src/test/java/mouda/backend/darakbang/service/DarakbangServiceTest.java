package mouda.backend.darakbang.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;

@SpringBootTest
class DarakbangServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangService darakbangService;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("다락방 생성에 성공한다.")
	@Test
	void success() {
		DarakbangCreateRequest request = new DarakbangCreateRequest("우아한테크코스", "테니");
		Member hogee = memberRepository.save(MemberFixture.getHogee());

		Darakbang darakbang = darakbangService.createDarakbang(request, hogee);

		assertThat(darakbang.getId()).isEqualTo(1L);
	}
}
