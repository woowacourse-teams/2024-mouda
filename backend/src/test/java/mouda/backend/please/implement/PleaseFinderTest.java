package mouda.backend.please.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.PleaseFixture;
import mouda.backend.please.domain.Please;
import mouda.backend.please.domain.PleaseWithInterest;
import mouda.backend.please.domain.PleaseWithInterests;

@SpringBootTest
class PleaseFinderTest extends DarakbangSetUp {

	@Autowired
	private PleaseFinder pleaseFinder;

	@Autowired
	private PleaseWriter pleaseWriter;

	@Autowired
	private InterestWriter interestWriter;

	@Autowired
	private InterestFinder interestFinder;

	@DisplayName("해주세요 Id로 해주세요를 조회한다.")
	@Test
	void findTest() {
		Please pleaseChicken = PleaseFixture.getPleaseChicken();
		Please savedPlease = pleaseWriter.savePlease(pleaseChicken);
		Please findPlease = pleaseFinder.find(savedPlease.getId(), darakbang.getId());

		assertThat(findPlease.getId()).isEqualTo(savedPlease.getId());
	}

	@DisplayName("해주세요 전체를 조회한다.")
	@Test
	void findAllTest() {
		Please pleasePizza = pleaseWriter.savePlease(PleaseFixture.getPleasePizza());
		Please pleaseChicken = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());
		Please pleaseHogee = pleaseWriter.savePlease(PleaseFixture.getPleaseHogee());

		PleaseWithInterests pleasesDesc = pleaseFinder.findPleasesDesc(darakbang.getId(), darakbangHogee);
		assertThat(pleasesDesc.getPleaseWithInterests().size()).isEqualTo(3);
	}

	@DisplayName("해주세요 목록 조회시 관심이 많은 순서대로 조회하고, 관심이 같다면 생성된 순서대로 조회한다.")
	@Test
	void findAllPlease_isSortedByInterestCount() {
		Please pleasePizza = pleaseWriter.savePlease(PleaseFixture.getPleasePizza());
		Please pleaseChicken = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());
		Please pleaseHogee = pleaseWriter.savePlease(PleaseFixture.getPleaseHogee());

		interestWriter.changeInterest(pleaseHogee, true, darakbangHogee);
		interestWriter.changeInterest(pleaseChicken, true, darakbangHogee);
		interestWriter.changeInterest(pleaseChicken, true, darakbangAnna);

		PleaseWithInterests pleasesDesc = pleaseFinder.findPleasesDesc(darakbang.getId(), darakbangHogee);
		List<PleaseWithInterest> pleaseWithInterests = pleasesDesc.getPleaseWithInterests();
		assertThat(pleaseWithInterests.get(0).getPlease().getId()).isEqualTo(2L);
		assertThat(pleaseWithInterests.get(1).getPlease().getId()).isEqualTo(3L);
		assertThat(pleaseWithInterests.get(2).getPlease().getId()).isEqualTo(1L);
	}
}
