package mouda.backend.chat.presentation.response;

import java.util.List;
import java.util.Map;

import mouda.backend.chat.domain.ChatRoomDetails;

public record ChatRoomDetailsResponse(
	long chatRoomId,
	Map<String, Object> attributes,
	String title,
	String type,
	List<ChatRoomParticipantResponse> participations
) {

	public static ChatRoomDetailsResponse from(ChatRoomDetails chatRoomDetails) {
		return new ChatRoomDetailsResponse(
			chatRoomDetails.getId(),
			chatRoomDetails.getAttributes(),
			chatRoomDetails.getTitle(),
			chatRoomDetails.getChatRoomType().toString(),
			getParticipants(chatRoomDetails)
		);
	}

	private static List<ChatRoomParticipantResponse> getParticipants(ChatRoomDetails chatRoomDetails) {
		return chatRoomDetails.getParticipants().stream()
			.map(participant -> new ChatRoomParticipantResponse(participant.getNickname(), participant.getProfile(), participant.getRole()))
			.toList();
	}
}