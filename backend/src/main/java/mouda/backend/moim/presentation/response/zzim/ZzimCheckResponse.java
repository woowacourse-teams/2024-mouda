package mouda.backend.moim.presentation.response.zzim;

public record ZzimCheckResponse(
	boolean isZzimed
) {

	public static ZzimCheckResponse toResponse(boolean isZzimed) {
		return new ZzimCheckResponse(isZzimed);
	}
}
