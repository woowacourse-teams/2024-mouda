package mouda.backend.darakbang.implement;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;

@Service
@RequiredArgsConstructor
public class DarakbangWriter {

	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberRepository darakbangMemberRepository;

	public Darakbang save(Darakbang entity) {
		Darakbang darakbang = darakbangRepository.save(entity);

		darakbangMemberRepository.save(darakbangMember);

		return darakbang;
	}
}
