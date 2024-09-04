package mouda.backend.common.global;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.notification.business.NotificationService;

@SpringBootTest
public class IgnoreNotificationTest {

	@MockBean
	private NotificationService notificationService;

	@BeforeEach
	void setUp() {
		doNothing().when(notificationService).notifyToAllExceptMember(any(), anyLong(), any(), any(), anyLong());
		doNothing().when(notificationService).notifyToAllExceptMember(any(), anyLong(), any(), any(), anyList());
		doNothing().when(notificationService).notifyToAllMembers(any(), anyLong(), any(), any());
		doNothing().when(notificationService).notifyToMember(any(), anyLong(), any(), any(), anyLong());
		doNothing().when(notificationService).notifyToMembers(any(), anyLong(), any(), any());
	}
}
