package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import components.job.JobService;
import controllers.generic.BadRequestException;
import controllers.generic.GenericApiController;
import dtos.ActionInstanceDto;
import dtos.conversion.ModelDtoConversionService;
import models.ActionInstance;
import models.PlatformInstance;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.db.jpa.JPAApi;

/**
 * Created by frankgriesinger on 10.12.2016.
 */
public class ActionInstanceController extends GenericApiController<ActionInstance, ActionInstanceDto, ActionInstanceDto, ActionInstanceDto> {

    private JobService jobService;
    private final ModelService<ActionInstance> actionInstanceModelService;
    private final JPAApi jpaApi;

    @Inject
    public ActionInstanceController(FrontendUserService frontendUserService,
                                      ModelService<Tenant> tenantModelService, ModelService<ActionInstance> modelService,
                                      TypeLiteral<ActionInstance> typeLiteral, ModelDtoConversionService conversionService,
                                      JobService jobService, JPAApi jpaApi) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);

        this.jobService = jobService;
        this.actionInstanceModelService = modelService;
        this.jpaApi = jpaApi;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ActionInstanceController.get(id).absoluteURL(request());
    }

    @Override
    protected void postPost(ActionInstance entity) {
        super.postPost(entity);

        // After the entity was created successfully, enact the actual instantiation of the
        // action instance:
        //TODO bind to tenant...
        //jobService.newExecuteActionInstanceJob(entity, getActiveTenant());

        /** Intermediate implementation of scale out of component : **/
    }

    @Override
    protected void prePut(ActionInstanceDto putDto, ActionInstance entity) throws BadRequestException {
        // Check if state changed from running OK to STOPPED
        super.prePut(putDto, entity);
    }

    @Override
    protected void postPut(ActionInstance entity) {
        // Enact the changes from prePut, if the call was successful
        super.postPut(entity);
    }
}

