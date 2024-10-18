package mouda.backend.chat.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChatDateTimeFormatter {

	// 2024-10-21 17:08 형식의 데이터를 10월 21일 17시 08분으로 변경
	public static String formatDateTime(String dateTime) {
		String[] splitDateTime = dateTime.split(" ");
		LocalDate date = LocalDate.parse(splitDateTime[0]);
		LocalTime time = LocalTime.parse(splitDateTime[1]);

		return date.getMonthValue() + "월 "
			+ date.getDayOfMonth() + "일 "
			+ time.getHour() + "시 "
			+ time.getMinute() + "분";
	}
}
