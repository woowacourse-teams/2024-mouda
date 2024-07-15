package mouda.backend.moim.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MoimServiceTest {

    @Autowired
    private MoimService moimService;

    @DisplayName("모임을 생성한다.")
    @Test
    void createMoim() {
        MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
                "title", LocalDate.now(), LocalTime.now(), "place",
                10, "안나", "설명"
        );
        Moim moim = moimService.createMoim(moimCreateRequest);
        assertThat(moim.getId()).isEqualTo(1L);
    }
}
