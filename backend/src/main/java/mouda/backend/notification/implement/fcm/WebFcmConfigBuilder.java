package mouda.backend.notification.implement.fcm;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;

public class WebFcmConfigBuilder implements FcmConfigBuilder {

    private final WebpushConfig webpushConfig;

    public WebFcmConfigBuilder() {
        this.webpushConfig = createWebpushConfig();
    }

    @Override
    public MulticastMessage.Builder buildMulticastMessage(MulticastMessage.Builder builder) {
        return builder.setWebpushConfig(webpushConfig);
    }

    private WebpushConfig createWebpushConfig() {
        return WebpushConfig.builder()
                .setFcmOptions(option())
                .build();
    }

    private WebpushFcmOptions option() {
        return WebpushFcmOptions.builder().build();
    }
}
