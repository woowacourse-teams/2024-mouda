package mouda.backend.chat.implement;

import java.util.List;

import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@FunctionalInterface
public interface ChatPreviewManager {

	List<ChatPreview> create(DarakbangMember darakbangMember);
}
