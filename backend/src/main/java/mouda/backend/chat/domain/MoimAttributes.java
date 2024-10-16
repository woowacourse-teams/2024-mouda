package mouda.backend.chat.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class MoimAttributes implements Attributes {

	private final String title;
	private final String place;
	private final Boolean isMoimer;
	private final Boolean isStarted;
	private final String description;
	private final LocalDate date;
	private final LocalTime time;
	private final Long moimId;

	public MoimAttributes(String title, String place, Boolean isMoimer, Boolean isStarted, String description, LocalDate date, LocalTime time, Long moimId) {
		this.title = title;
		this.place = place;
		this.isMoimer = isMoimer;
		this.isStarted = isStarted;
		this.description = description;
		this.date = date;
		this.time = time;
		this.moimId = moimId;
	}

	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("title", title);
		attributes.put("place", place);
		attributes.put("isMoimer", isMoimer);
		attributes.put("isStarted", isStarted);
		attributes.put("description", description);
		attributes.put("date", date);
		attributes.put("time", time);
		attributes.put("moimId", moimId);
		return attributes;
	}
}
