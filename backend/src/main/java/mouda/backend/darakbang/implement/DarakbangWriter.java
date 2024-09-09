package mouda.backend.darakbang.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.implement.DarakbangMemberWriter;
import mouda.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class DarakbangWriter {

	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberWriter darakbangMemberWriter;
	private final InvitationCodeGenerator invitationCodeGenerator;

	public Darakbang save(String name, String nickname, Member member) {
		Darakbang entity = Darakbang.builder()
			.code(generateInvitationCode())
			.name(name)
			.build();
		Darakbang darakbang = darakbangRepository.save(entity);
		darakbangMemberWriter.saveManager(darakbang, nickname, member);

		return darakbang;
	}

	private String generateInvitationCode() {
		String invitationCode = invitationCodeGenerator.generate();
		if (darakbangRepository.existsByCode(invitationCode)) {
			throw new DarakbangException(HttpStatus.INTERNAL_SERVER_ERROR, DarakbangErrorMessage.CODE_ALREADY_EXIST);
		}
		return invitationCode;
	}
}
