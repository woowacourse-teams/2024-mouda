package mouda.backend.chat.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;

/**
 * 채팅방에서 날짜와 시간이 확정될 때 알림 메시지에 사용되는 날짜 / 시간 형식 변환을 위한 유틸리티 클래스
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ChatDateTimeFormatter {

	private static final String DATETIME_DELIMITER = " ";
	private static final int DATE_PART = 0;
	private static final int TIME_PART = 1;

	public static String formatDateTime(String dateTime) {
		LocalDateTime localDateTime = parseDateTime(dateTime);
		StringBuilder formattedDateTime = new StringBuilder();

		int year = localDateTime.getYear();
		if (year > LocalDate.now().getYear()) {
			formattedDateTime.append(year).append("년 ");
		}

		return formattedDateTime
			.append(localDateTime.getMonthValue()).append("월 ")
			.append(localDateTime.getDayOfMonth()).append("일 ")
			.append(localDateTime.getHour()).append("시 ")
			.append(localDateTime.getMinute()).append("분")
			.toString();
	}

	private static LocalDateTime parseDateTime(String dateTime) {
		try {
			String[] dateTimeParts = dateTime.split(DATETIME_DELIMITER);
			LocalDate date = LocalDate.parse(dateTimeParts[DATE_PART]);
			LocalTime time = LocalTime.parse(dateTimeParts[TIME_PART]);

			return LocalDateTime.of(date, time);
		} catch (Exception e) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_DATE_TIME_FORMAT);
		}
	}
}
