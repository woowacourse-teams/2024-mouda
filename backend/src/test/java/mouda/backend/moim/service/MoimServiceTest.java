package mouda.backend.moim.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.response.MoimFindAllResponses;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MoimServiceTest {

    @Autowired
    private MoimService moimService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    void cleanUp() {
        databaseCleaner.cleanUp();
    }

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

    @DisplayName("모임을 전체 조회한다.")
    @Test
    void findAllMoim() {
        MoimCreateRequest moimCreateRequest = new MoimCreateRequest(
                "title", LocalDate.now(), LocalTime.now(), "place",
                10, "안나", "설명"
        );
        moimService.createMoim(moimCreateRequest);
        moimService.createMoim(moimCreateRequest);

        MoimFindAllResponses moimResponses = moimService.findAllMoim();

        assertThat(moimResponses).isNotNull();
        assertThat(moimResponses.moims()).hasSize(2);
    }
}
