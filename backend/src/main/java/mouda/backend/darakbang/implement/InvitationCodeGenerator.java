package mouda.backend.darakbang.implement;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvitationCodeGenerator {

	private static final int CODE_LENGTH = 7;
	private static final int NUMBER_UPPER_BOUND = 10;
	private static final int CHAR_RANDOM_BOUND = 26;

	private final SecureRandom secureRandom = new SecureRandom();
	private final DarakbangValidator darakbangValidator;

	public String generate() {
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < CODE_LENGTH; i++) {
			if (secureRandom.nextBoolean()) {
				code.append(secureRandom.nextInt(NUMBER_UPPER_BOUND));
			} else {
				code.append(getRandomUpperAlphabet());
			}
		}
		darakbangValidator.validateAlreadyExistsCode(code.toString());
		return code.toString();
	}

	private char getRandomUpperAlphabet() {
		return (char)(secureRandom.nextInt(CHAR_RANDOM_BOUND) + 65);
	}
}
