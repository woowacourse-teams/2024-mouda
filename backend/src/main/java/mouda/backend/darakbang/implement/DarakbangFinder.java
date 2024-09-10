package mouda.backend.darakbang.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.domain.Darakbangs;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.implement.DarakbangMemberFinder;
import mouda.backend.member.domain.Member;

@Component
@RequiredArgsConstructor
public class DarakbangFinder {

	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberFinder darakbangMemberFinder;

	public Darakbangs findAllMyDarakbangs(Member member) {
		List<Darakbang> darakbangs = darakbangMemberFinder.findAllByMember(member);
		return new Darakbangs(darakbangs);
	}

	public Darakbang findById(Long darakbangId) {
		return darakbangRepository.findById(darakbangId)
			.orElseThrow(() -> new DarakbangException(HttpStatus.NOT_FOUND, DarakbangErrorMessage.DARAKBANG_NOT_FOUND));
	}

	public Darakbang findByCode(String code) {
		return darakbangRepository.findByCode(code)
			.orElseThrow(() -> new DarakbangException(HttpStatus.NOT_FOUND, DarakbangErrorMessage.DARAKBANG_NOT_FOUND));
	}
}
