package mouda.backend.chat.domain;

import java.util.Map;

import lombok.Getter;

@Getter
public class MoimAttributes implements Attributes {

	private String place;
	private Boolean isMoimer;
	private Boolean isStarted;
	private String description;
	private String date;
	private String time;
	private Long moimId;

	public MoimAttributes(String place, Boolean isMoimer, Boolean isStarted, String description, String date, String time, Long moimId) {
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
		return null;
	}
}
