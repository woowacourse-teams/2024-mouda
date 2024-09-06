package mouda.backend.notification.domain.recipient;

import java.util.List;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.notification.domain.MoudaNotification;

public interface RecipientResolverStrategy {

	List<Long> resolveRecipients(long darakbangId, MoudaNotification notification, Moim moim,
		DarakbangMember sender);
}
