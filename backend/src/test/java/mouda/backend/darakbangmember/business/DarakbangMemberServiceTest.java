package mouda.backend.darakbangmember.business;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import mouda.backend.common.fixture.DarakbangMemberFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.implement.S3Client;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;
import mouda.backend.member.presentation.response.DarakbangMemberInfoResponse;

@SpringBootTest
class DarakbangMemberServiceTest extends DarakbangSetUp {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DarakbangMemberRepository darakbangMemberRepository;

	@Autowired
	private DarakbangMemberService darakbangMemberService;

	@MockBean
	private S3Client s3Client;

	@Value("${aws.s3.prefix}")
	private String prefix;

	@DisplayName("다락방 멤버 권한 조회 테스트")
	@Nested
	class DarakbangMemberRoleReadTest {

		@DisplayName("다락방 멤버가 아니라면 OUTSIDER를 반환한다.")
		@Test
		void success() {
			Member chico = MemberFixture.getChico();
			memberRepository.save(chico);
			DarakbangMember darakbangMember = DarakbangMemberFixture.getDarakbangOutsiderWithWooteco(darakbang, chico);
			darakbangMemberRepository.save(darakbangMember);
			DarakbangMemberRoleResponse response = darakbangMemberService.findDarakbangMemberRole(
				darakbang.getId(), chico);

			assertThat(response.role()).isEqualTo("OUTSIDER");
		}
	}

	@DisplayName("다락방 멤버 조회 테스트")
	@Nested
	class DarakbangMemberReadTest {

		@DisplayName("매니저는 다락방 멤버 목록을 조회할 수 있다.")
		@Test
		void managerCanReadDarakbangMembers() {
			DarakbangMemberResponses responses = darakbangMemberService.findAllDarakbangMembers(
				darakbang.getId(), darakbangManager);

			assertThat(responses.responses()).hasSize(3);
		}

		@DisplayName("다락방 멤버는 다락방 멤버 목록을 조회할 수 있다.")
		@Test
		void memberCanReadDarakbangMembers() {
			DarakbangMemberResponses responses = darakbangMemberService.findAllDarakbangMembers(
				darakbang.getId(), darakbangAnna);

			assertThat(responses.responses()).hasSize(3);
		}
	}

	@DisplayName("내 정보를 조회한다.")
	@Test
	void findMyInfo() {
		DarakbangMemberInfoResponse response = darakbangMemberService.findMyInfo(darakbangHogee);

		assertThat(response.name()).isEqualTo("hogee");
		assertThat(response.nickname()).isEqualTo("hogee");
		assertThat(response.profile()).isEqualTo("profile");
		assertThat(response.description()).isEqualTo("description");
	}

	@DisplayName("마이페이지 수정 테스트")
	@Nested
	class UpdateMyInfoTest {

		private String nickname = "수정된 닉네임";
		private String description = "수정된 소개";

		@DisplayName("이미지 추가 없이 닉네임, 소개만 변경하는 경우 S3 통신 없이 DB값을 변경한다.")
		@Test
		void updateMyInfoWhenNoProfileUpdate() {
			// when
			darakbangMemberService.updateMyInfo(darakbangAnna, "false", null, nickname, description);

			// then
			Optional<DarakbangMember> annaOptional = darakbangMemberRepository.findById(darakbangAnna.getId());
			assertThat(annaOptional.isPresent()).isTrue();
			assertThat(annaOptional.get().getNickname()).isEqualTo(nickname);
			assertThat(annaOptional.get().getDescription()).isEqualTo(description);

			verify(s3Client, times(0)).uploadFile(any());
		}

		@DisplayName("이미지 추가와 더불어 닉네임, 소개를 변경하는 경우 S3 통신과 함께 DB 값을 변경한다.")
		@Test
		void updateMyInfoWithProfileUpdate() {
			// given
			MockMultipartFile file = new MockMultipartFile("file", "test-file.txt",
				MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
			when(s3Client.uploadFile(any())).thenReturn("https://s3url/uuid.png");

			// when
			darakbangMemberService.updateMyInfo(darakbangAnna, "false", file, nickname, description);

			// then
			Optional<DarakbangMember> annaOptional = darakbangMemberRepository.findById(darakbangAnna.getId());
			assertThat(annaOptional.isPresent()).isTrue();
			assertThat(annaOptional.get().getNickname()).isEqualTo(nickname);
			assertThat(annaOptional.get().getDescription()).isEqualTo(description);
			String expectedUrl = prefix + "uuid.png";
			assertThat(annaOptional.get().getProfile()).isEqualTo(expectedUrl);

			verify(s3Client, times(1)).uploadFile(any());
		}

		@DisplayName("기존 이미지로 변경하는 경우 S3 통신 없이 DB의 프로필 이미지 URL 값을 제거한다.")
		@Test
		void updateMyInfoWithBasicProfileUpdate() {
			// given
			doNothing().when(s3Client).deleteFile(anyString());

			// when
			darakbangMemberService.updateMyInfo(darakbangAnna, "true", null, nickname, description);

			// then
			Optional<DarakbangMember> annaOptional = darakbangMemberRepository.findById(darakbangAnna.getId());
			assertThat(annaOptional.isPresent()).isTrue();
			assertThat(annaOptional.get().getNickname()).isEqualTo(nickname);
			assertThat(annaOptional.get().getDescription()).isEqualTo(description);
			assertThat(annaOptional.get().getProfile()).isNull();

			verify(s3Client, times(0)).uploadFile(any());
		}
	}
}
