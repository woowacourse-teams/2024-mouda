package mouda.backend.chamyo.service;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chamyo.exception.ChamyoException;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.repository.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.fixture.DarakbangFixture;
import mouda.backend.fixture.DarakbangMemberFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class ChamyoServiceTest {

	@Autowired
	private ChamyoService chamyoService;

	@Autowired
	private DarakbangRepository darakbangRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@DisplayName("같은 회원이 따로 따로 여러 번 참여 요청을 하면 두 번째 요청 시 참여에 실패한다.")
	@Test
	void chamyoMoim() throws InterruptedException {
		Darakbang darakbang = DarakbangFixture.getDarakbangWithMouda();
		darakbangRepository.save(darakbang);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		DarakbangMember darakbangMember = DarakbangMemberFixture
			.getDarakbangMemberWithWooteco(darakbang, member);
		darakbangMemberRepository.save(darakbangMember);

		Moim moim = MoimFixture.getBasketballMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoService.chamyoMoim(darakbang.getId(), moim.getId(), darakbangMember);
		assertThatThrownBy(() -> chamyoService.chamyoMoim(darakbang.getId(), moim.getId(), darakbangMember))
			.isInstanceOf(ChamyoException.class);
		assertThat(chamyoService.findAllChamyo(darakbang.getId(), moim.getId()).chamyos()).hasSize(1);
	}

	@DisplayName("동시 참여 테스트")
	@Nested
	class ConcurrencyTest {

		@DisplayName("같은 회원이 동시에 여러 번 참여 요청을 하면 동시 참여가 불가능하다.")
		@Test
		void chamyoMoimConcurrently() throws InterruptedException {
			int threadCount = 2;
			ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
			CountDownLatch latch = new CountDownLatch(threadCount);

			Darakbang darakbang = DarakbangFixture.getDarakbangWithMouda();
			darakbangRepository.save(darakbang);

			Moim moim = MoimFixture.getBasketballMoim(darakbang.getId());
			moimRepository.save(moim);

			Member member = MemberFixture.getAnna();
			memberRepository.save(member);

			DarakbangMember darakbangMember = DarakbangMemberFixture
				.getDarakbangMemberWithWooteco(darakbang, member);
			darakbangMemberRepository.save(darakbangMember);

			long startTime = System.currentTimeMillis();
			for (int i = 0; i < threadCount; i++) {
				executorService.execute(() -> {
					try {
						chamyoService.chamyoMoim(darakbang.getId(), moim.getId(), darakbangMember);
					} finally {
						latch.countDown();
					}
				});
			}
			latch.await();
			executorService.shutdown();

			long endTime = System.currentTimeMillis();
			System.out.printf("Test time : %d ms\n", endTime - startTime);

			assertThat(chamyoService.findAllChamyo(darakbang.getId(), moim.getId()).chamyos()).hasSize(1);
		}
	}
}
