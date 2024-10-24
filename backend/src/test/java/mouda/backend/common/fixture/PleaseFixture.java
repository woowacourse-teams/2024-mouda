package mouda.backend.common.fixture;

import mouda.backend.please.domain.Please;

public class PleaseFixture {

	public static Please getPleaseChicken() {
		return Please.builder()
			.title("치킨 사주세요")
			.description("제발요")
			.authorId(1L)
			.darakbangId(1L)
			.build();
	}

	public static Please getPleasePizza() {
		return Please.builder()
			.title("피자 사주세요")
			.description("제발요")
			.authorId(2L)
			.darakbangId(1L)
			.build();
	}

	public static Please getPleaseHogee() {
		return Please.builder()
			.title("호기 사주세요")
			.description("제발요")
			.authorId(2L)
			.darakbangId(1L)
			.build();
	}

	public static Please getPlease(long id, long darakbangId) {
		return Please.builder()
			.title("라라스윗 사주세요")
			.description("제발요")
			.authorId(id)
			.darakbangId(darakbangId)
			.build();
	}
}
