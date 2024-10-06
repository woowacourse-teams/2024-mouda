package mouda.backend.notification.implement.fcm;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.BatchResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FcmResponseHandler {

	public void handleBatchResponse(BatchResponse batchResponse) {
	}
}
