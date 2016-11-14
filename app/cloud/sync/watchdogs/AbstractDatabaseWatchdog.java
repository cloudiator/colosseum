package cloud.sync.watchdogs;

import cloud.sync.AbstractWatchDog;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.generic.Model;
import models.service.ModelService;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.11.16.
 */
public abstract class AbstractDatabaseWatchdog<T extends Model> extends AbstractWatchDog<T> {

    private final ModelService<T> modelService;

    @Inject
    protected AbstractDatabaseWatchdog(@Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue, Set<ProblemDetector<T>> problemDetectors, ModelService<T> modelService) {
        super(problemQueue, problemDetectors);

        checkNotNull(modelService, "modelService is null.");

        this.modelService = modelService;
    }

    protected ModelService<T> modelService() {
        return modelService;
    }
}
