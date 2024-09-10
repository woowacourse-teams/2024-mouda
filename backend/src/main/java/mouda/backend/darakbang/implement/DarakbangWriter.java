package mouda.backend.darakbang.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;

@Component
@RequiredArgsConstructor
public class DarakbangWriter {

	private final DarakbangRepository darakbangRepository;
	private final InvitationCodeGenerator invitationCodeGenerator;

	public Darakbang save(String name) {
		Darakbang entity = Darakbang.builder()
			.code(invitationCodeGenerator.generate())
			.name(name)
			.build();
		return darakbangRepository.save(entity);
	}
}
