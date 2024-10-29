package mouda.backend.common;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HealthCheckController {

	private static final String HOST_IPV4 = "127.0.0.1";
	private static final String HOST_IPV6 = "0:0:0:0:0:0:0:1";
	private static final String HOST_NAME = "localhost";

	private final AtomicBoolean isTerminating = new AtomicBoolean(false);

	@GetMapping("/health")
	public ResponseEntity<Void> checkHealth() {
		if (isTerminating.get()) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/termination")
	public ResponseEntity<Void> terminate(HttpServletRequest request) {
		String remoteHost = request.getRemoteHost();
		System.out.println(remoteHost);
		if (HOST_IPV6.equals(remoteHost) || HOST_IPV4.equals(remoteHost) || HOST_NAME.equals(remoteHost)) {
			isTerminating.set(true);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
