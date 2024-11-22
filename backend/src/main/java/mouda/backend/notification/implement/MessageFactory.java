package mouda.backend.notification.implement;

import java.util.List;

import mouda.backend.notification.domain.CommonNotification;

public interface MessageFactory<T> {

	T createMessage(CommonNotification notification, List<String> receivers);
}
