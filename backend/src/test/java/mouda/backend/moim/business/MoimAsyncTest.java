package mouda.backend.moim.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.notificiation.MoimRecipientFinder;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.presentation.request.moim.MoimCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimEditRequest;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponse;

@SpringBootTest
public class MoimAsyncTest extends DarakbangSetUp {

	@Autowired
	private MoimService moimService;

	@Autowired
	private MoimRepository moimRepository;

	@MockBean
	private MoimRecipientFinder moimRecipientFinder;

	@DisplayName("모임 생성시 알림 전송 과정에서 예외가 발생해도 모임은 생성된다.")
	@Test
	void asyncWhenMoimCreate() {
		// given
		String title = "비동기 확인 ~";
		String description = "비동기동비";

		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			title,
			null,
			null,
			null,
			10,
			description
		);

		// when
		when(moimRecipientFinder.getMoimCreatedNotificationRecipients(anyLong(), anyLong()))
			.thenThrow(new RuntimeException("삐용12"));

		moimService.createMoim(darakbang.getId(), darakbangAnna, moimCreateRequest);
		long moimId = getMoimId(title, description);

		// then
		Optional<Moim> moimOptional = moimRepository.findById(moimId);
		assertThat(moimOptional).isNotEmpty();

		Moim moim = moimOptional.get();
		assertThat(moim.getTitle()).isEqualTo(title);
		assertThat(moim.getDescription()).isEqualTo(description);
	}

	@DisplayName("모임 정보 수정시 알림 전송 과정에서 예외가 발생해도 수정은 반영된다.")
	@Test
	void asyncWhenMoimEdit() {
		// given
		String title = "비동기 확인 ~";
		String description = "비동기동비";
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			title,
			null,
			null,
			null,
			10,
			description
		);
		moimService.createMoim(darakbang.getId(), darakbangAnna, moimCreateRequest);
		long moimId = getMoimId(title, description);

		// when
		when(moimRecipientFinder.getMoimCreatedNotificationRecipients(anyLong(), anyLong()))
			.thenThrow(new RuntimeException("삐용12"));
		String editedTitle = "수정된 비동기 ~";
		String editedDescription = "수정된 비동기동비";

		moimService.editMoim(darakbang.getId(), new MoimEditRequest(
			moimId,
			editedTitle,
			null,
			null,
			null,
			10,
			editedDescription
		), darakbangAnna);

		// then
		Optional<Moim> moimOptional = moimRepository.findById(moimId);
		assertThat(moimOptional).isNotEmpty();
		Moim moim = moimOptional.get();
		assertThat(moim.getTitle()).isEqualTo(editedTitle);
		assertThat(moim.getDescription()).isEqualTo(editedDescription);
	}

	@DisplayName("모임 상태 변경시 알림 전송 과정에서 예외가 발생해도 상태 변경은 반영된다.")
	@Test
	void asyncWhenMoimStatusChange() {
		// given
		String title = "비동기 확인 ~";
		String description = "비동기동비";
		MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
			title,
			null,
			null,
			null,
			10,
			description
		);
		moimService.createMoim(darakbang.getId(), darakbangAnna, moimCreateRequest);
		long moimId = getMoimId(title, description);

		// when
		when(moimRecipientFinder.getMoimCreatedNotificationRecipients(anyLong(), anyLong()))
			.thenThrow(new RuntimeException("삐용12"));

		moimService.completeMoim(darakbang.getId(), moimId, darakbangAnna);

		// then
		Optional<Moim> moimOptional = moimRepository.findById(moimId);
		assertThat(moimOptional).isNotEmpty();

		Moim moim = moimOptional.get();
		assertThat(moim.isCompleted()).isTrue();
	}

	private Long getMoimId(String title, String description) {
		return moimService.findAllMoim(darakbang.getId(), darakbangAnna).moims().stream()
			.filter(moim -> moim.title().equals(title) && moim.description().equals(description))
			.map(MoimFindAllResponse::moimId)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
