package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

import components.job.CreatePlatformInstanceJob;
import components.job.JobException;
import components.job.JobService;
import controllers.generic.BadRequestException;
import controllers.generic.GenericApiController;
import dtos.PlatformInstanceDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformInstance;
import models.Tenant;
import models.generic.RemoteState;
import models.service.FrontendUserService;
import models.service.ModelService;


/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformInstanceController extends GenericApiController<PlatformInstance, PlatformInstanceDto, PlatformInstanceDto, PlatformInstanceDto> {

    private JobService jobService;
    private final ModelService<PlatformInstance> platformInstanceModelService;

    @Inject
    public PlatformInstanceController(FrontendUserService frontendUserService,
                              ModelService<Tenant> tenantModelService, ModelService<PlatformInstance> modelService,
                              TypeLiteral<PlatformInstance> typeLiteral, ModelDtoConversionService conversionService,
                              JobService jobService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);

        this.jobService = jobService;
        this.platformInstanceModelService = modelService;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformInstanceController.get(id).absoluteURL(request());
    }

    @Override
    protected void postPost(PlatformInstance entity) {
        super.postPost(entity);

        // After the entity was created successfully, enact the actual instantiation of the
        // platform instance:
        //TODO bind to tenant...
        jobService.newPlatformInstanceJob(entity, getActiveTenant());

// Has to be done asynchronously bc of database sync issues:
//        try {
//            createPlatformInstanceJob.execute();
//        } catch (JobException e) {
//            entity.setRemoteState(RemoteState.ERROR);
//            this.platformInstanceModelService.save(entity);
//            throw new RuntimeException("Unexpected ERROR during platform instance creation.");
//        }
    }

    @Override
    protected void prePut(PlatformInstanceDto putDto, PlatformInstance entity) throws BadRequestException {
        // Check if state changed from running OK to STOPPED
        super.prePut(putDto, entity);
    }

    @Override
    protected void postPut(PlatformInstance entity) {
        // Enact the changes from prePut, if the call was successful
        super.postPut(entity);
    }
}
