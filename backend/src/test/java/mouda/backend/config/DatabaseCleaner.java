package mouda.backend.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DatabaseCleaner(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void cleanUp() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC' AND table_type='BASE TABLE'";
        List<String> tables = jdbcTemplate.queryForList(sql, String.class);
        for (String tableName : tables) {
            jdbcTemplate.update("DELETE FROM " + tableName);
            jdbcTemplate.update("ALTER TABLE " + tableName + " alter column id restart with 1");
        }
    }
}
