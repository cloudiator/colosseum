package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

import components.scalability.ScalingEngine;
import controllers.generic.GenericApiController;
import de.uniulm.omi.cloudiator.visor.client.ClientBuilder;
import de.uniulm.omi.cloudiator.visor.client.ClientController;
import de.uniulm.omi.cloudiator.visor.client.entities.Monitor;
import de.uniulm.omi.cloudiator.visor.client.entities.PushMonitor;
import de.uniulm.omi.cloudiator.visor.client.entities.PushMonitorBuilder;
import dtos.PlatformMonitorDto;
import dtos.conversion.ModelDtoConversionService;
import models.*;
import models.service.FrontendUserService;
import models.service.ModelService;
import play.db.jpa.JPAApi;

/**
 * Created by frankgriesinger on 10.12.2016.
 */
public class PlatformMonitorController extends GenericApiController<PlatformMonitor, PlatformMonitorDto, PlatformMonitorDto, PlatformMonitorDto> {
    ScalingEngine se;
    private JPAApi jpaApi;
    private ModelService<MonitorInstance> monitorInstanceModelService;
    private ModelService<PlatformMonitor> platformMonitorModelService;

    @Inject
    public PlatformMonitorController(FrontendUserService frontendUserService,
                                     ModelService<Tenant> tenantModelService, ModelService<PlatformMonitor> modelService,
                                     TypeLiteral<PlatformMonitor> typeLiteral, ModelDtoConversionService conversionService,
                                     JPAApi jpaApi, ModelService<MonitorInstance> monitorInstanceModelService, ScalingEngine se){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
        this.jpaApi = jpaApi;
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.platformMonitorModelService = modelService;
        this.se = se;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformMonitorController.get(id).absoluteURL(request());
    }

    @Override
    protected void postPost(PlatformMonitor entity) {
        super.postPost(entity);

        se.doMonitor(entity);
    }
}
