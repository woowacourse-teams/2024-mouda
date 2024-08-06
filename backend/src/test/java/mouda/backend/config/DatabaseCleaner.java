package mouda.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
public class DatabaseCleaner {

	@Autowired
	private EntityManager entityManager;

	public DatabaseCleaner(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public void cleanUp() {
		entityManager.createNativeQuery("DELETE FROM CHAT").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE CHAT alter column id restart with 1").executeUpdate();

		entityManager.createNativeQuery("DELETE FROM COMMENT").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE COMMENT alter column id restart with 1").executeUpdate();

		entityManager.createNativeQuery("DELETE FROM ZZIM").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE ZZIM alter column id restart with 1").executeUpdate();

		entityManager.createNativeQuery("DELETE FROM CHAMYO").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE CHAMYO alter column id restart with 1").executeUpdate();

		entityManager.createNativeQuery("DELETE FROM PLEASE").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE PLEASE alter column id restart with 1").executeUpdate();

		entityManager.createNativeQuery("DELETE FROM MEMBER").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE MEMBER alter column id restart with 1").executeUpdate();

		entityManager.createNativeQuery("DELETE FROM MOIM").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE MOIM alter column id restart with 1").executeUpdate();

		entityManager.clear();
	}
}
