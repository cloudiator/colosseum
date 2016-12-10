package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
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
    private JPAApi jpaApi;
    private ModelService<MonitorInstance> monitorInstanceModelService;
    private ModelService<PlatformMonitor> platformMonitorModelService;

    @Inject
    public PlatformMonitorController(FrontendUserService frontendUserService,
                                     ModelService<Tenant> tenantModelService, ModelService<PlatformMonitor> modelService,
                                     TypeLiteral<PlatformMonitor> typeLiteral, ModelDtoConversionService conversionService,
                                     JPAApi jpaApi, ModelService<MonitorInstance> monitorInstanceModelService){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
        this.jpaApi = jpaApi;
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.platformMonitorModelService = modelService;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformMonitorController.get(id).absoluteURL(request());
    }

    @Override
    protected void postPost(PlatformMonitor entity) {
        super.postPost(entity);

        // After the entity was created successfully, enact the actual instantiation of the
        // action instance:
        //TODO bind to tenant...
        //jobService.newExecuteActionInstanceJob(entity, getActiveTenant());

        /** Intermediate implementation of scale out of component : **/
        // Add a new monitor instance for each platform instance found on this filter
        // to the local visor as Push Monitor, i.e. a telnet sensor
        final String protocol = "http";
        final String ip = "127.0.0.1";
        final int port = 31415;
        final ClientController<Monitor> controller;
        //get the controller for the cloud entity

        //TODO: create monitor instance here:
        String idMonitorInstance = "";
        MonitorInstance mi = new MonitorInstance(platformMonitorModelService.getById(entity.getId()),
                "",
                null,
                null,
                null);
        monitorInstanceModelService.save(mi);
        mi = monitorInstanceModelService.getAll().get(monitorInstanceModelService.getAll().size()-1); // TODO check if this works
        idMonitorInstance = mi.getId().toString(); // TODO check if this works

        controller =
                ClientBuilder.getNew()
                        // the base url
                        .url(protocol + "://" + ip + ":" + port)
                        // the entity to get the controller for.
                        .build(Monitor.class);

        PushMonitor monitor = (new PushMonitorBuilder())
                .metricName("aggregation") // TODO: should be paas_metric?
                .addMonitorContext("monitorinstance", idMonitorInstance).build();

        //create a new Monitor
        monitor = (PushMonitor) controller.create(monitor);
        Integer newPort = monitor.getPort();

        mi.setPort(newPort);
        monitorInstanceModelService.save(mi);
    }
}
