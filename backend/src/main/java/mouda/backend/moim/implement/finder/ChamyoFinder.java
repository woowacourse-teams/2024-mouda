package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.ChamyoErrorMessage;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Component
@RequiredArgsConstructor
public class ChamyoFinder {

	private final ChamyoRepository chamyoRepository;

	public Chamyo read(Moim moim, DarakbangMember darakbangMember) {
		return read(moim.getId(), darakbangMember);
	}

	public Chamyo read(long moimId, DarakbangMember darakbangMember) {
		return find(moimId, darakbangMember)
			.orElseThrow(() -> new ChamyoException(HttpStatus.NOT_FOUND, ChamyoErrorMessage.NOT_FOUND));
	}

	private Optional<Chamyo> find(long moimId, DarakbangMember darakbangMember) {
		return chamyoRepository.findByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());
	}

	public boolean exists(long moimId, DarakbangMember darakbangMember) {
		return chamyoRepository.existsByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());
	}

	public MoimRole readMoimRole(Moim moim, DarakbangMember darakbangMember) {
		Optional<Chamyo> chamyoOptional = find(moim.getId(), darakbangMember);
		if (chamyoOptional.isEmpty()) {
			return MoimRole.NON_MOIMEE;
		}

		Chamyo chamyo = chamyoOptional.get();
		return chamyo.getMoimRole();
	}

	public List<Chamyo> readAll(Moim moim) {
		return chamyoRepository.findAllByMoimId(moim.getId());
	}

	public List<Chamyo> readAllChatOpened(long darakbangId, DarakbangMember darakbangMember) {
		return chamyoRepository.findAllByDarakbangMemberIdAndMoim_DarakbangId(darakbangMember.getId(), darakbangId)
			.stream()
			.filter(chamyo -> chamyo.getMoim().isChatOpened())
			.toList();
	}
}
