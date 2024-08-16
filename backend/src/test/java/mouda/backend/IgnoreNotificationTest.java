package mouda.backend;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.notification.service.NotificationService;

@SpringBootTest
public class IgnoreNotificationTest {

	@MockBean
	private NotificationService notificationService;

	@BeforeEach
	void setUp() {
		doNothing().when(notificationService).notifyToAllExceptMember(any(), (Long)any());
		doNothing().when(notificationService).notifyToAllExceptMember(any(), (List<Long>)any());
		doNothing().when(notificationService).notifyToAllMembers(any());
		doNothing().when(notificationService).notifyToMember(any(), any());
		doNothing().when(notificationService).notifyToMembers(any(), any());
	}
}
