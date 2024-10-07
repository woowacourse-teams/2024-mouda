package mouda.backend.moim.implement.sender;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.event.ChamyoEvent;
import mouda.backend.moim.domain.event.MoimEvent;
import mouda.backend.notification.domain.NotificationType;

@Component
@EnableConfigurationProperties(value = UrlConfig.class)
@RequiredArgsConstructor
public class MoimEventFactory {

	private final UrlConfig urlConfig;

	public MoimEvent create(Moim moim, NotificationType notificationType) {
		return MoimEvent.builder()
			.baseUrl(urlConfig.getBase())
			.specificUrl(urlConfig.getMoim())
			.notificationType(notificationType)
			.moim(moim)
			.build();
	}

	public ChamyoEvent create(Chamyo chamyo, NotificationType notificationType, DarakbangMember darakbangMember) {
		return ChamyoEvent.builder()
			.baseUrl(urlConfig.getBase())
			.specificUrl(urlConfig.getMoim())
			.moim(chamyo.getMoim())
			.notificationType(notificationType)
			.updatedMember(darakbangMember)
			.build();
	}
}
