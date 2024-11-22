package mouda.backend.please.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;

@Component
@RequiredArgsConstructor
public class PleaseValidator {

	public void validate(Please please, Long darakbangId, DarakbangMember darakbangMember) {
		validateNotInDarakbang(please, darakbangId);
		validateAuthorize(please, darakbangMember);
	}

	public void validateNotInDarakbang(Please please, Long darakbangId) {
		if (please.isNotInDarakbang(darakbangId)) {
			throw new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.PLEASE_NOT_IN_DARAKBANG);
		}
	}

	private void validateAuthorize(Please please, DarakbangMember darakbangMember) {
		if (please.isNotAuthor(darakbangMember.getId())) {
			throw new PleaseException(HttpStatus.FORBIDDEN, PleaseErrorMessage.NOT_ALLOWED_TO_DELETE);
		}
	}
}
