package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.implement.ChatRoomFinder;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimOverview;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;
import mouda.backend.moim.infrastructure.ZzimRepository;
import mouda.backend.moim.infrastructure.dto.ChamyoMoim;

@Component
@RequiredArgsConstructor
public class MoimFinder {

	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;
	private final ZzimFinder zzimFinder;
	private final ZzimRepository zzimRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomFinder chatRoomFinder;
	private final ChamyoFinder chamyoFinder;

	public Moim read(long moimId, long currentDarakbangId) {
		return moimRepository.findByIdAndDarakbangId(moimId, currentDarakbangId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
	}

	public List<MoimOverview> readAll(long darakbangId, DarakbangMember darakbangMember) {
		List<Moim> moims = moimRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId);
		List<ChamyoMoim> chamyoMoims = chamyoRepository.findAllByMoims(moims);
		Set<Long> zzimedMoimIds = zzimRepository.findAllByDarakbangMemberId(darakbangMember.getId());

		return createMoimOverview(moims, chamyoMoims, zzimedMoimIds);
	}

	public List<MoimOverview> readAllMyMoim(DarakbangMember darakbangMember, FilterType filterType) {
		List<ChamyoMoim> chamyoMoims = chamyoRepository.findAllByDarakbangMemberId(darakbangMember.getId());
		Set<Long> moimIds = chamyoMoims.stream()
			.map(ChamyoMoim::getMoimId)
			.collect(Collectors.toSet());
		List<Moim> moims = moimRepository.findAllByIds(moimIds)
			.stream()
			.filter(getFilter(filterType))
			.toList();
		Set<Long> zzimedMoimIds = zzimRepository.findAllByDarakbangMemberId(darakbangMember.getId());

		return createMoimOverview(moims, chamyoMoims, zzimedMoimIds);
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
		Set<Long> zzimMoimIds = zzimRepository.findAllByDarakbangMemberId(darakbangMember.getId());
		List<Moim> moims = moimRepository.findAllByIds(zzimMoimIds);
		List<ChamyoMoim> chamyoMoims = chamyoRepository.findAllByMoims(moims);

		return createMoimOverview(moims, chamyoMoims, zzimMoimIds);
	}

	private List<MoimOverview> createMoimOverview(List<Moim> moims, List<ChamyoMoim> chamyoMoims,
		Set<Long> zzimedMoimIds) {
		Map<Long, Long> chamyoMap = chamyoMoims.stream()
			.collect(Collectors.toMap(ChamyoMoim::getMoimId, ChamyoMoim::getChamyoCount));

		return moims.stream()
			.map(moim -> new MoimOverview(
				moim,
				chamyoMap.getOrDefault(moim.getId(), 0L),
				zzimedMoimIds.contains(moim.getId())
			))
			.toList();
	}

	public int countCurrentPeople(Moim moim) {
		return chamyoRepository.countByMoim(moim);
	}

	public List<Moim> readAllMyMoims(DarakbangMember darakbangMember) {
		return chamyoRepository.findAllByDarakbangMemberIdOrderByIdDesc(darakbangMember.getId())
			.stream()
			.map(Chamyo::getMoim)
			.toList();
	}
}
