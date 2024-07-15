package mouda.backend.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse<T> {
	private T data;
}
