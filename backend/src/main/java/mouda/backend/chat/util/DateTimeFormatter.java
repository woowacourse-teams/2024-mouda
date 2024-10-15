package mouda.backend.chat.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeFormatter {

	public static String formatDateTime(LocalDate date, LocalTime time) {
		return date.getMonth().getValue() + "월 "
			+ date.getDayOfMonth() + "일 "
			+ time.getHour() + "시 "
			+ time.getMinute() + "분";
	}
}
