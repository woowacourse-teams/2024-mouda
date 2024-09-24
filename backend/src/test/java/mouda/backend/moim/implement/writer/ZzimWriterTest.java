package mouda.backend.moim.implement.writer;

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
class ZzimWriterTest extends DarakbangSetUp {

	@Autowired
	private ZzimWriter zzimWriter;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ZzimRepository zzimRepository;

	@DisplayName("찜하지 않은 모임의 경우 찜을 생성한다.")
	@Test
	void createZzimIfAbsent() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		zzimWriter.updateZzimStatus(moim, darakbangAnna);

		assertThat(zzimRepository.findByMoimIdAndDarakbangMemberId(moim.getId(), darakbangAnna.getId()))
			.isPresent();
	}

	@DisplayName("찜한 모임의 경우 찜을 삭제한다.")
	@Test
	void deleteZzimIfPresent() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		Zzim zzim = Zzim.builder()
			.darakbangMember(darakbangAnna)
			.moim(moim)
			.build();
		zzimRepository.save(zzim);

		zzimWriter.updateZzimStatus(moim, darakbangAnna);

		assertThat(zzimRepository.findByMoimIdAndDarakbangMemberId(moim.getId(), darakbangAnna.getId()))
			.isEmpty();
	}
}
