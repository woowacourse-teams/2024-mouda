package mouda.backend.moim.implement.finder;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimFinder {

	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;

	public Moim read(long moimId, long currentDarakbangId) {
		return moimRepository.findByIdAndDarakbangId(moimId, currentDarakbangId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
	}

	public List<Moim> readAll(long darakbangId) {
		return moimRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId);
	}

	public int countCurrentPeople(Moim moim) {
		return chamyoRepository.countByMoim(moim);
	}
}
