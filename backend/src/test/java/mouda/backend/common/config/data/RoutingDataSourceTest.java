package mouda.backend.common.config.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import mouda.backend.common.config.WithTransactionalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@WithTransactionalTest
class RoutingDataSourceTest {

    private RoutingDataSource routingDataSource;

    @BeforeEach
    void setUp() {
        DataSource masterDataSource = mock(DataSource.class);
        DataSource slaveDataSource = mock(DataSource.class);

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceConfiguration.MASTER_DATA_SOURCE, masterDataSource);
        dataSources.put(DataSourceConfiguration.SLAVE_DATA_SOURCE, slaveDataSource);

        routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
    }

    @DisplayName("@Transactional이 readOnly가 아니면 Master DataSource로 라우팅한다.")
    @Test
    @Transactional
    void routeToMasterDataSource() {
        Object lookupKey = routingDataSource.determineCurrentLookupKey();
        assertThat(lookupKey).isEqualTo(DataSourceConfiguration.MASTER_DATA_SOURCE);
    }

    @DisplayName("@Transactional이 readOnly 이면 Slave DataSource로 라우팅한다.")
    @Test
    @Transactional(readOnly = true)
    void routeToSlaveDataSource() {
        Object lookupKey = routingDataSource.determineCurrentLookupKey();
        assertThat(lookupKey).isEqualTo(DataSourceConfiguration.SLAVE_DATA_SOURCE);
    }

    @DisplayName("@Transactional이 없는 경우 기본적으로 Master DataSource로 라우팅한다.")
    @Test
    void routeToMasterDataSource_WhenNoTransactionalExist() {
        Object lookupKey = routingDataSource.determineCurrentLookupKey();
        assertThat(lookupKey).isEqualTo(DataSourceConfiguration.MASTER_DATA_SOURCE);
    }
}
