package mouda.backend.darakbang.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;

@Component
@RequiredArgsConstructor
public class DarakbangWriter {

	private final DarakbangRepository darakbangRepository;
	private final InvitationCodeGenerator invitationCodeGenerator;

	public Darakbang save(String name) {
		Darakbang entity = Darakbang.builder()
			.code(generateInvitationCode())
			.name(name)
			.build();
		return darakbangRepository.save(entity);
	}

	private String generateInvitationCode() {
		String invitationCode = invitationCodeGenerator.generate();
		if (darakbangRepository.existsByCode(invitationCode)) {
			throw new DarakbangException(HttpStatus.INTERNAL_SERVER_ERROR, DarakbangErrorMessage.CODE_ALREADY_EXIST);
		}
		return invitationCode;
	}
}
