package mouda.backend.fixture;

import mouda.backend.please.domain.Please;

public class PleaseFixture {

	public static Please getPleaseWithAuthorId1L() {
		return new Please("이거 해주세요", "아니 그냥 해달라고", 1L);
	}
}
