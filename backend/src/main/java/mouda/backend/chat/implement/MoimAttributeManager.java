package mouda.backend.chat.implement;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.Attributes;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.MoimAttributes;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.implement.finder.ChamyoFinder;
import mouda.backend.moim.implement.finder.MoimFinder;

@Component
@RequiredArgsConstructor
public class MoimAttributeManager implements AttributeManager {

	private final MoimFinder moimFinder;
	private final ChamyoFinder chamyoFinder;

	@Override
	public boolean support(ChatRoomType chatRoomType) {
		return chatRoomType == ChatRoomType.MOIM;
	}

	@Override
	public Attributes create(ChatRoom chatRoom, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangMember.getDarakbang().getId());
		Chamyo chamyo = chamyoFinder.read(moim, darakbangMember);
		boolean isMoimer = getIsMoimer(chamyo);
		return new MoimAttributes(
			moim.getTitle(),
			moim.getPlace(),
			isMoimer,
			moim.isPastMoim(),
			moim.getDescription(),
			moim.getDate(),
			formatToSecondPrecision(moim.getTime()),
			moim.getId()
		);
	}

	private boolean getIsMoimer(Chamyo chamyo) {
		return chamyo.getMoimRole() == MoimRole.MOIMER;
	}

	private LocalTime formatToSecondPrecision(LocalTime time) {
		return time.withNano(0);
	}
}
