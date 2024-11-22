package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.infrastructure.ZzimRepository;

@SpringBootTest
class ZzimFinderTest extends DarakbangSetUp {

	@Autowired
	private ZzimFinder zzimFinder;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ZzimRepository zzimRepository;

	@DisplayName("찜한 모임의 경우 참을 반환한다.")
	@Test
	void isTrueWhenMoimZzimedByMember() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));

		boolean isZzimed = zzimFinder.isMoimZzimedByMember(moim.getId(), darakbangAnna);

		assertThat(isZzimed).isFalse();
	}

	@DisplayName("찜하지 않은 모임의 경우 거짓을 반환한다.")
	@Test
	void isFalseWhenMoimNotZzimedByMember() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		Zzim zzim = Zzim.builder()
			.darakbangMember(darakbangAnna)
			.moim(moim)
			.build();
		zzimRepository.save(zzim);

		boolean isZzimed = zzimFinder.isMoimZzimedByMember(moim.getId(), darakbangAnna);

		assertThat(isZzimed).isTrue();
	}
}
