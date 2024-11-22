package mouda.backend.notification.implement.fcm;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;

@Component
public class WebFcmConfigBuilder implements FcmConfigBuilder {

    private static final WebpushConfig webpushConfig;

    static {
        webpushConfig = createWebpushConfig();
    }

    @Override
    public MulticastMessage.Builder buildMulticastMessage(MulticastMessage.Builder builder) {
        return builder.setWebpushConfig(webpushConfig);
    }

    private static WebpushConfig createWebpushConfig() {
        return WebpushConfig.builder()
                .setFcmOptions(option())
                .build();
    }

    private static WebpushFcmOptions option() {
        return WebpushFcmOptions.builder().build();
    }
}
