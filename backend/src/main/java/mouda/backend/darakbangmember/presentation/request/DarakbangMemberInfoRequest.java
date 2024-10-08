package mouda.backend.darakbangmember.presentation.request;

import org.springframework.web.multipart.MultipartFile;

public record DarakbangMemberInfoRequest(
	String nickname,
	String description,
	MultipartFile file
) {
}
