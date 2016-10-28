package components.scalability.aggregation;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import models.ComposedMonitor;
import models.Monitor;
import models.service.ModelService;
import play.db.jpa.JPAApi;

/**
 * Created by Frank on 26.10.2016.
 */
@Singleton
public class AggregationFactory {
    private final JPAApi jpaApi;
    private final ModelService<ComposedMonitor> composedMonitorModelService;

    @Inject
    public AggregationFactory(JPAApi jpaApi, ModelService<ComposedMonitor> composedMonitorModelService) {
        this.jpaApi = jpaApi;
        this.composedMonitorModelService = composedMonitorModelService;
    }

    public RemoveAggregation createRemoveAggregation(Monitor monitor) {
        return new RemoveAggregation(monitor, composedMonitorModelService, jpaApi);
    }
}
