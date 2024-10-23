package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Participant;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Component
@RequiredArgsConstructor
public class MoimParticipantResolver implements ParticipantsResolver {

	private final ChamyoRepository chamyoRepository;

	@Override
	public boolean support(ChatRoomType chatRoomType) {
		return chatRoomType == ChatRoomType.MOIM;
	}

	@Override
	@Transactional
	public List<Participant> resolve(ChatRoom chatRoom) {
		return chamyoRepository.findAllByMoimId(chatRoom.getTargetId()).stream()
			.map(chamyo -> {
				DarakbangMember darakbangMember = chamyo.getDarakbangMember();
				return new Participant(
					darakbangMember.getId(),
					darakbangMember.getNickname(),
					darakbangMember.getProfile(),
					chamyo.getMoimRole().toString());
			}).toList();
	}
}
