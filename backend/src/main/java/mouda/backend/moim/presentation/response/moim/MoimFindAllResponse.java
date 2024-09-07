package mouda.backend.moim.presentation.response.moim;

import static java.time.format.DateTimeFormatter.*;

import lombok.Builder;
import mouda.backend.moim.domain.Moim;

@Builder
public record MoimFindAllResponse(
	Long moimId,
	String title,
	String date,
	String time,
	String place,
	int currentPeople,
	int maxPeople,
	String authorNickname,
	String description,
	boolean isZzimed
) {

	public static MoimFindAllResponse toResponse(Moim moim, int currentPeople, boolean isZzimed) {
		String time = moim.getTime() == null ? "" : moim.getTime().format(ofPattern("HH:mm"));
		String date = moim.getDate() == null ? "" : moim.getDate().format(ISO_LOCAL_DATE);
		String place = moim.getPlace() == null ? "" : moim.getPlace();
		return MoimFindAllResponse.builder()
			.moimId(moim.getId())
			.title(moim.getTitle())
			.date(date)
			.time(time)
			.place(place)
			.currentPeople(currentPeople)
			.maxPeople(moim.getMaxPeople())
			.description(moim.getDescription())
			.isZzimed(isZzimed)
			.build();
	}
}
