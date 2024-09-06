package mouda.backend.notification.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class NotificationException extends MoudaException {

	public NotificationException(HttpStatus httpStatus, NotificationErrorMessage notificationErrorMessage) {
		super(httpStatus, notificationErrorMessage.getMessage());
	}
}
