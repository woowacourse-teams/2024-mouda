package mouda.backend.please.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.PleaseFixture;
import mouda.backend.please.domain.Interest;
import mouda.backend.please.domain.Please;

@SpringBootTest
class InterestFinderTest extends DarakbangSetUp {

	@Autowired
	private InterestFinder interestFinder;

	@Autowired
	private InterestWriter interestWriter;

	@Autowired
	private PleaseWriter pleaseWriter;

	@DisplayName("다락방 맴버가 해주세요에 관심을 눌렀는지 확인한다.")
	@Test
	void findByDarakBangMemberAndPleaseTest() {
		Please pleaseChicken = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());
		interestWriter.changeInterest(pleaseChicken, true, darakbangHogee);

		Optional<Interest> interest = interestFinder.findInterest(
			darakbangHogee.getMemberId(),
			pleaseChicken.getId()
		);

		assertThat(interest.isPresent()).isTrue();
	}

	@DisplayName("관심있어요 의 개수를 카운트한다.")
	@Test
	void countInterestTest() {
		Please pleaseChicken = pleaseWriter.savePlease(PleaseFixture.getPleaseChicken());
		interestWriter.changeInterest(pleaseChicken, true, darakbangHogee);
		interestWriter.changeInterest(pleaseChicken, true, darakbangAnna);

		assertThat(interestFinder.countInterest(pleaseChicken)).isEqualTo(2);
	}
}
