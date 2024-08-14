package mouda.backend.notification.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FcmInitializer {
	@Value("${app.firebase-config-file}")
	private String firebaseConfigPath;

	@PostConstruct
	public void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(
					GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				log.info("Firebase application initialized");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
