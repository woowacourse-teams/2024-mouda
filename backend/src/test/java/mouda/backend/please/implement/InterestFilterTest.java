package mouda.backend.please.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.PleaseFixture;
import mouda.backend.please.domain.Please;

@SpringBootTest
class InterestFilterTest extends DarakbangSetUp {

	@Autowired
	private InterestFilter interestFilter;

	@Autowired
	private InterestFinder interestFinder;

	@Autowired
	private PleaseWriter pleaseWriter;

	@DisplayName("관심있어요 상태 변경시 변경한 해주세요만 관심여부가 변경된다.")
	@Test
	void findAllPlease() {
		Please pleaseChicken = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());
		Please pleasePizza = pleaseWriter.savePlease(PleaseFixture.getPleasePizza());

		interestFilter.changeInterest(pleaseChicken, 1L, true, darakbangHogee);

		assertThat(interestFinder.findInterest(darakbangHogee.getId(), pleaseChicken.getId())).isNotEmpty();
	}

}
