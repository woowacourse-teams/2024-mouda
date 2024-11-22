package mouda.backend.darakbang.business;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangFixture;
import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbang.presentation.request.DarakbangEnterRequest;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class DarakbangServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private DarakbangService darakbangService;

	@DisplayName("다락방 참여 동시성 테스트")
	@Nested
	class DarakbangEnterConcurrencyTest {

		@DisplayName("한 명의 회원이 다락방에 여러 번 참여 시도하면 실패한다.")
		@Test
		void failToEnterDarakbangTwoTimes() throws InterruptedException {
			Member hogee = memberRepository.save(MemberFixture.getHogee());
			Darakbang darakbang = darakbangRepository.save(DarakbangFixture.getDarakbangWithWooteco());
			darakbangMemberRepository.save(DarakbangMemberFixture.getDarakbangManagerWithWooteco(darakbang, hogee));

			int threadCount = 2;
			ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
			CountDownLatch latch = new CountDownLatch(threadCount);

			DarakbangEnterRequest request = new DarakbangEnterRequest("안나");
			Member member = memberRepository.save(MemberFixture.getAnna());
			for (int i = 0; i < threadCount; i++) {
				executorService.execute(() -> {
					try {
						darakbangService.enter(darakbang.getCode(), request, member);
					} finally {
						latch.countDown();
					}
				});
			}
			latch.await();
			executorService.shutdown();

			assertThat(darakbangMemberRepository.findAllByDarakbangId(darakbang.getId())).hasSize(2);
		}
	}
}
