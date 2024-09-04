package mouda.backend.common.fixture;

import mouda.backend.please.domain.Please;

public class PleaseFixture {

	public static Please getPlease() {
		return Please.builder()
			.title("치킨 사주세요")
			.description("제발요")
			.authorId(1L)
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

	public static Please getPleaseWithAuthorId1L() {
		return new Please("이거 해주세요", "아니 그냥 해달라고", 1L, 1L);
	}
}
