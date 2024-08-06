package mouda.backend.fixture;

import mouda.backend.please.domain.Please;

public class PleaseFixture {

	public static Please getPlease() {
		return Please.builder()
			.title("치킨 사주세요")
			.description("제발요")
			.authorId(1L)
			.build();
	}

	public static Please getPlease(long id) {
		return Please.builder()
			.title("라라스윗 사주세요")
			.description("제발요")
			.authorId(id)
			.build();
	}
}
