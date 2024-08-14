package mouda.backend.darakbang.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class InvitationCodeGenerator {

	private static final int CODE_LENGTH = 7;
	private static final int NUMBER_UPPER_BOUND = 10;
	private static final int CHAR_RANDOM_BOUND = 26;

	private final SecureRandom secureRandom = new SecureRandom();

	public String generate() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < CODE_LENGTH; i++) {
			if (secureRandom.nextBoolean()) {
				sb.append(secureRandom.nextInt(NUMBER_UPPER_BOUND));
			} else {
				sb.append(getRandomUpperAlphabet());
			}
		}

		return sb.toString();
	}

	private char getRandomUpperAlphabet() {
		return (char)(secureRandom.nextInt(CHAR_RANDOM_BOUND) + 65);
	}
}
