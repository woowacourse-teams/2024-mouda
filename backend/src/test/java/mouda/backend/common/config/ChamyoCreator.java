package mouda.backend.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
public class ChamyoCreator {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public void setUpTwoPastThreeUpcomingMoim() {
		entityManager.createNativeQuery(
			"INSERT INTO Moim (title, date, time, place, max_people, description, moim_status, is_chat_opened) VALUES "
				+ "('과거 축구 모임', DATEADD('DAY', -1, CURRENT_DATE), CURRENT_TIME(), '과거 축구 장소', 10, '축구추구', 'COMPLETED', false), "
				+ "('과거 농구 모임', DATEADD('DAY', -5, CURRENT_DATE), CURRENT_TIME(), '과거 농구 장소', 15, '농구농구', 'CANCELED', true);"
		).executeUpdate();

		entityManager.createNativeQuery(
			"INSERT INTO Moim (title, date, time, place, max_people, description, moim_status, is_chat_opened) VALUES "
				+ "('미래 축구 모임', DATEADD('DAY', 5, CURRENT_DATE), CURRENT_TIME(), '축구 장소', 20, '많이 오세요', 'MOIMING', true), "
				+ "('미래 농구 모임', DATEADD('DAY', 10, CURRENT_DATE), CURRENT_TIME(), '농구 장소', 25, '많이 오세요', 'MOIMING', false), "
				+ "('미래 커피 모임', DATEADD('DAY', 15, CURRENT_DATE), CURRENT_TIME(), '커피 장소', 30, '많이 오세요', 'MOIMING', true);"
		).executeUpdate();

		entityManager.createNativeQuery("INSERT INTO MEMBER (nickname) VALUES ('테바')").executeUpdate();

		entityManager.createNativeQuery("INSERT INTO CHAMYO (member_id, moim_id, moim_role, last_read_chat_id) VALUES "
			+ "(1, 1, 'MOIMEE', 0), "
			+ "(1, 2, 'MOIMEE', 0), "
			+ "(1, 3, 'MOIMEE', 0), "
			+ "(1, 4, 'MOIMEE', 0), "
			+ "(1, 5, 'MOIMEE', 0)").executeUpdate();

		entityManager.createNativeQuery("INSERT INTO ZZIM (member_id, moim_id) VALUES "
			+ "(1, 1), "
			+ "(1, 3), "
			+ "(1, 5)").executeUpdate();

		entityManager.clear();
	}
}
