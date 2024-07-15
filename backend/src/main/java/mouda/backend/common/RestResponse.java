package mouda.backend.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestResponse<T> {
	private T data;
}
