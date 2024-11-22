package mouda.backend.notification.implement.fcm;

import com.google.firebase.messaging.MulticastMessage;

public interface FcmConfigBuilder {

    MulticastMessage.Builder buildMulticastMessage(MulticastMessage.Builder builder);
}
