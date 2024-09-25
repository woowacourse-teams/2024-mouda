package mouda.backend.common.config.data;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Profile(value = "prod")
@Configuration
public class DataSourceConfiguration {

    public static final String MASTER_DATA_SOURCE = "MASTER";
    public static final String SLAVE_DATA_SOURCE = "SLAVE";
    public static final String ROUTING_DATA_SOURCE = "ROUTING";

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("mouda.backend.*.domain")
                .build();
    }

    @Primary
    @DependsOn(ROUTING_DATA_SOURCE)
    @Bean
    public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE) DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @DependsOn({MASTER_DATA_SOURCE, SLAVE_DATA_SOURCE})
    @Bean(ROUTING_DATA_SOURCE)
    public DataSource routingDataSource(
            @Qualifier(MASTER_DATA_SOURCE) DataSource masterDataSource,
            @Qualifier(SLAVE_DATA_SOURCE) DataSource slaveDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSources = Map.of(
                MASTER_DATA_SOURCE, masterDataSource,
                SLAVE_DATA_SOURCE, slaveDataSource
        );

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean(MASTER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(SLAVE_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
