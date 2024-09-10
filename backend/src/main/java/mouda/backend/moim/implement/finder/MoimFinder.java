package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimOverview;
import mouda.backend.moim.domain.Zzim;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.infrastructure.ZzimRepository;

@Component
@RequiredArgsConstructor
public class MoimFinder {

	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;
	private final ZzimFinder zzimFinder;
	private final ZzimRepository zzimRepository;

	public Moim read(long moimId, long currentDarakbangId) {
		return moimRepository.findByIdAndDarakbangId(moimId, currentDarakbangId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
	}

	public List<MoimOverview> readAll(long darakbangId, DarakbangMember darakbangMember) {
		return moimRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId).stream()
			.map(moim -> createMoimOverview(moim, darakbangMember))
			.toList();
	}

	public List<MoimOverview> readAllMyMoim(DarakbangMember darakbangMember, FilterType filterType) {
		return chamyoRepository.findAllByDarakbangMemberIdOrderByIdDesc(darakbangMember.getId()).stream()
			.map(Chamyo::getMoim)
			.filter(getFilter(filterType))
			.map(moim -> createMoimOverview(moim, darakbangMember))
			.toList();
	}

	private Predicate<Moim> getFilter(FilterType filterType) {
		if (filterType == FilterType.PAST) {
			return Moim::isPastMoim;
		}
		if (filterType == FilterType.UPCOMING) {
			return Moim::isUpcomingMoim;
		}
		return moim -> true;
	}

	public List<MoimOverview> readAllZzimedMoim(DarakbangMember darakbangMember) {
		return zzimRepository.findAllByDarakbangMemberIdOrderByIdDesc(darakbangMember.getId()).stream()
			.map(zzim -> createMoimOverview(zzim.getMoim(), darakbangMember))
			.toList();
	}

	private MoimOverview createMoimOverview(Moim moim, DarakbangMember darakbangMember) {
		int currentPeople = countCurrentPeople(moim);
		boolean isZzimed = zzimFinder.isMoimZzimedByMember(moim.getId(), darakbangMember);

		return new MoimOverview(moim, currentPeople, isZzimed);
	}

	public int countCurrentPeople(Moim moim) {
		return chamyoRepository.countByMoim(moim);
	}
}
