package mouda.backend.common.config.data;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceConfiguration.SLAVE_DATA_SOURCE;
        }
        return DataSourceConfiguration.MASTER_DATA_SOURCE;
    }
}
