package mouda.backend.darakbangmember.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMembers;
import mouda.backend.darakbangmember.implement.DarakbangMemberFinder;
import mouda.backend.darakbangmember.implement.DarakbangMemberWriter;
import mouda.backend.darakbangmember.implement.ImageParser;
import mouda.backend.darakbangmember.implement.S3Client;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.presentation.response.DarakbangMemberInfoResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangMemberService {

	private final DarakbangMemberFinder darakbangMemberFinder;
	private final DarakbangFinder darakbangFinder;
	private final MemberFinder memberFinder;
	private final DarakbangMemberWriter darakbangMemberWriter;
	private final S3Client s3Client;
	private final ImageParser imageParser;

	@Transactional(readOnly = true)
	public DarakbangMemberResponses findAllDarakbangMembers(Long darakbangId, DarakbangMember member) {
		DarakbangMembers darakbangMembers = darakbangMemberFinder.findAllDarakbangMembers(darakbangId);

		return DarakbangMemberResponses.toResponse(darakbangMembers);
	}

	@Transactional(readOnly = true)
	public DarakbangMemberRoleResponse findDarakbangMemberRole(Long darakbangId, Member member) {
		DarakBangMemberRole role = darakbangMemberFinder.findDarakbangMemberRole(darakbangId, member.getId());

		return DarakbangMemberRoleResponse.toResponse(role);
	}

	public DarakbangMember findDarakbangMember(long darakbangId, Member member) {
		Darakbang darakbang = darakbangFinder.findById(darakbangId);
		return darakbangMemberFinder.find(darakbang, member);
	}

	@Transactional(readOnly = true)
	public DarakbangMemberInfoResponse findMyInfo(DarakbangMember darakbangMember) {
		Member member = memberFinder.findByMemberId(darakbangMember.getMemberId());
		return new DarakbangMemberInfoResponse(member.getName(), darakbangMember.getNickname(),
			darakbangMember.getProfile(), darakbangMember.getDescription());
	}

	public void updateMyInfo(
		DarakbangMember darakbangMember, String isReset,
		MultipartFile file, String nickname, String description
	) {
		if (file != null) {
			String url = s3Client.uploadFile(file);
			String newProfileUrl = imageParser.parse(url);
			darakbangMemberWriter.updateMyInfo(darakbangMember, nickname, description, newProfileUrl);
			return;
		}
		if (isReset.equals("true")) {
			darakbangMemberWriter.updateMyInfo(darakbangMember, nickname, description, null);
		} else {
			darakbangMemberWriter.updateMyInfo(darakbangMember, nickname, description);
		}
	}
}
