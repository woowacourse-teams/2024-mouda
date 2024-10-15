package mouda.backend.notification.implement.fcm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.MulticastMessage.Builder;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.implement.MessageFactory;

@Component
@RequiredArgsConstructor
public class FcmMessageFactory implements MessageFactory<List<MulticastMessage>> {

    private static final int TOKEN_BATCH_SIZE = 500;
    private final List<FcmConfigBuilder> fcmConfigBuilders;

    @Override
    public List<MulticastMessage> createMessage(CommonNotification notification, List<String> fcmTokens) {
        List<List<String>> partitionedTokens = partitionTokensByBatch(fcmTokens);

        return partitionedTokens.stream()
            .map(tokens -> defaultMulticastMessageBuilder(notification).addAllTokens(tokens).build())
            .toList();
    }

    private Builder defaultMulticastMessageBuilder(CommonNotification notification) {
        Builder builder = MulticastMessage.builder()
                .setNotification(notification.toNotification())
                .putData("link", notification.getRedirectUrl());

        for (FcmConfigBuilder configBuilder : fcmConfigBuilders) {
            builder = configBuilder.buildMulticastMessage(builder);
        }

        return builder;
    }

    private List<List<String>> partitionTokensByBatch(List<String> tokens) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i += TOKEN_BATCH_SIZE) {
            result.add(tokens.subList(i, Math.min(i + TOKEN_BATCH_SIZE, tokens.size())));
        }
        return result;
    }
}
