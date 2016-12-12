package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.ActionInstanceDto;
import dtos.PlatformMonitorDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Action;
import models.ActionInstance;
import models.ApplicationComponent;
import models.PlatformMonitor;
import models.service.ModelService;

/**
 * Created by frankgriesinger on 10.12.2016.
 */
@Singleton
public class ActionInstanceConverter extends AbstractConverter<ActionInstance, ActionInstanceDto> {

    private final ModelService<Action> actionModelService;

    @Inject
    public ActionInstanceConverter(ModelService<Action> actionModelService) {
        super(ActionInstance.class, ActionInstanceDto.class);
        this.actionModelService = actionModelService;
    }


    @Override public void configure() {
        binding().fromField("dateStarted").toField("dateStarted");
        binding().fromField("dateFinished").toField("dateFinished");
        binding().fromField("dateFailed").toField("dateFailed");
        binding(Long.class, Action.class).fromField("action").toField("action")
                .withTransformation(new IdToModelTransformer<>(actionModelService));
    }
}
