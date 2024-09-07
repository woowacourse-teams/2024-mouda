package mouda.backend.moim.implement.finder;

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

	public Moim read(long moimId, long darakbangId) {
		return moimRepository.findByIdAndDarakbangId(moimId, darakbangId)
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND));
	}

	public int countCurrentPeople(long moimId, long darakbangId) {
		Moim moim = read(moimId, darakbangId);

		return chamyoRepository.countByMoim(moim);
	}
}
