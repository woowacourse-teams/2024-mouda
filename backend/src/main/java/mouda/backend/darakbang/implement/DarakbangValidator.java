package mouda.backend.darakbang.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Component
@RequiredArgsConstructor
public class DarakbangValidator {

	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberRepository darakbangMemberRepository;

	public void validateAlreadyExistsName(String name) {
		if (darakbangRepository.existsByName(name)) {
			throw new DarakbangException(HttpStatus.BAD_REQUEST, DarakbangErrorMessage.NAME_ALREADY_EXIST);
		}
	}

	public void validateCanEnterDarakbang(Darakbang darakbang, String nickname, Member member) {
		if (darakbangMemberRepository.existsByDarakbangIdAndNickname(darakbang.getId(), nickname)) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST,
				DarakbangMemberErrorMessage.NICKNAME_ALREADY_EXIST);
		}
		if (darakbangMemberRepository.existsByDarakbangIdAndMemberId(darakbang.getId(), member.getId())) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST,
				DarakbangMemberErrorMessage.MEMBER_ALREADY_EXIST);
		}
	}

	public void validateAlreadyExistsCode(String code) {
		if (darakbangRepository.existsByCode(code.toString())) {
			throw new DarakbangException(HttpStatus.INTERNAL_SERVER_ERROR, DarakbangErrorMessage.CODE_ALREADY_EXIST);
		}
	}
}
