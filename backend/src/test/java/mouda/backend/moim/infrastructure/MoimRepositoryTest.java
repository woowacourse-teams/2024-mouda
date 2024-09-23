package mouda.backend.moim.infrastructure;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.moim.domain.Moim;

@SpringBootTest
class MoimRepositoryTest {

	@Autowired
	private MoimRepository moimRepository;

	@DisplayName("모임 아이디로 조회한다.")
	@Test
	void test() {
		long start = System.currentTimeMillis();

		// List<Moim> moims = moimRepository.findAllByDarakbangIdOrderByIdDesc(4999L);
		List<Moim> moims = moimRepository.findAllByDarakbangId(4999L);

		long end = System.currentTimeMillis();

		System.out.println("end - start = " + (end - start) + "ms");
		System.out.println("moims = " + moims.size());
	}
}