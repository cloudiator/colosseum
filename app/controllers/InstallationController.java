/*
 * Copyright (c) 2014 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package controllers;

import java.rmi.registry.Registry;

import controllers.security.SecuredSessionOrToken;
//import forms.ComponentConfigurationForm;
import models.Application;
//import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(SecuredSessionOrToken.class)
public class InstallationController extends Controller {

    /**
     * A form for the installation model.
     */
//    static Form<InstallationForm> installationForm = Form
//            .form(InstallationForm.class);

    /**
     * A form for the assignment of a component installation.
     */
//    static Form<ComponentConfigurationForm> serviceInstallationForm = Form
//            .form(ComponentConfigurationForm.class);

    /**
     * Index action for the controller.
     * <p/>
     * Renders a view showing all installation entities.
     *
     * @return the rendered index view.
     */
    @Transactional(readOnly = true)
    public static Result index() {
        session("menuitemActive", "Installations");
        //return ok(views.html.installation.index.render(Installation.findAll()));
        return ok();
    }

    @Transactional(readOnly = true)
    public static Result show(Long id) {
//        Installation installation = Installation.findById(id);
//
//        if (installation == null) {
//            return notFound("Could not retrieve installation with id = " + id);
//        }
//
//        return ok(views.html.installation.show.render(installation));
    	return ok();
    }

    /**
     * New action for a installation object.
     * <p/>
     * Renders a form for installation objects.
     *
     * @return the rendered new view.
     */
    @Transactional(readOnly = true)
    public static Result newInstallation() {
//
//        return ok(views.html.installation.newInstallation
//                .render(installationForm));
    	return ok();
    }

    /**
     * Validates the submitted new form and creates the related installation
     * object.
     * <p/>
     * If validation fails, the new form is shown again.
     *
     * @return the rendered view
     */
    @Transactional
    public static Result create() {
//        Form<InstallationForm> filledForm = installationForm.bindFromRequest();
//        if (filledForm.hasErrors()) {
//            return badRequest(views.html.installation.newInstallation
//                    .render(filledForm));
//        } else {
//            Installation installation = new Installation(filledForm.get());
//            installation.save();
//
//            List<Job> createdBootstrapJobs = new ArrayList<>();
//            //check if we need to bootstrap this cloud first
//            for (ApplicationComponent applicationComponent : Application.findById(filledForm.get().application.id).applicationComponents) {
//                if (!applicationComponent.component.componentConfiguration.cloud.isBootstrapped()) {
//                    Bootstrap bootstrap = Bootstrap.newBootstrap(applicationComponent.component.componentConfiguration.cloud);
//                    bootstrap.save();
//                    BootstrapJob job = new BootstrapJob();
//                    job.resourceId = bootstrap.id;
//                    job.setExecutor(CloudifyCli.class);
//                    job.save();
//                    createdBootstrapJobs.add(job);
//                    JobQueue.getInstance().placeJob(job.id);
//                }
//            }
//
//
//
//            //create an installation job.
//            InstallationJob job = new InstallationJob();
//            job.resourceId = installation.id;
//            job.setExecutor(CloudifyCli.class);
//            job.dependsOn = createdBootstrapJobs;
//            job.save();
//            JobQueue.getInstance().placeJob(job.id);
//
//            flash("success", "The installation was successfully created.");
//            return redirect(routes.InstallationController.index());
    		return ok();
//        }
    }

    /**
     * Creates a Molpro Installation by the provided idApplication
     *
     *
     * @return the rendered view
     */
    @Transactional
    public static Result createMolpro(Long idApplication) {
//        //todo: handle more than one needed VM
//        Application application = Application.findById(idApplication);
//        //get the physical host by CactoOpt and change the OpenStack driver property
//
//        String physicalHost = InstallationController.getPhysicalHost(application);
//        if(physicalHost == null){
//            throw new RuntimeException("No physical host was found by CactoOpt");
//        }
//        InstallationController.setPhysicalHostToCloudDriver(physicalHost, application);
//        //create installation
//        Installation installation = new Installation();
//        installation.application = application;
//        installation.save();
//
//        List<Job> createdBootstrapJobs = new ArrayList<>();
//        //check if we need to bootstrap this cloud first
//        for (ApplicationComponent applicationComponent : application.applicationComponents) {
//            if (!applicationComponent.component.componentConfiguration.cloud.isBootstrapped()) {
//                Bootstrap bootstrap = Bootstrap.newBootstrap(applicationComponent.component.componentConfiguration.cloud);
//                bootstrap.save();
//                BootstrapJob job = new BootstrapJob();
//                job.resourceId = bootstrap.id;
//                job.setExecutor(CloudifyCli.class);
//                job.save();
//                createdBootstrapJobs.add(job);
//                JobQueue.getInstance().placeJob(job.id);
//            }
//        }
//
//
//
//        //create an installation job.
//        InstallationJob job = new InstallationJob();
//        job.resourceId = installation.id;
//        job.setExecutor(CloudifyCli.class);
//        job.dependsOn = createdBootstrapJobs;
//        job.save();
//        JobQueue.getInstance().placeJob(job.id);
//
//        //change state of MolproComputation to started
//        MolproComputation molproComputation = MolproComputation.findByApplication(application);
//        molproComputation.started = true;
//        molproComputation.save();
//
//        flash("success", "The installation was successfully created.");
//        return redirect(routes.MolproJobController.index());
    	return ok();

    }

    @Transactional(readOnly = true)
    public static Result json(final Long id) {

//        final Installation installation = Installation.findById(id);
//
//        if (installation == null) {
//            return notFound("Could not find installation with id = " + id);
//        }
//
//        Representation representation = new RepresentationFactory()
//                .newRepresentation().withJsonResource(installation.getForm(), installation.id);
//        return ok(representation.toString(RepresentationFactory.HAL_JSON));
    	return ok();
    }

    @Transactional(readOnly = true)
    public static Result jsonList() {
//
//        Representation representation = new RepresentationFactory().newRepresentation();
//
//        //Map<Long, Object> map = new HashMap<Long,Object>();
//        representation.withLink("self", "/api/installation");
//
//        for (Installation installation : Installation.findAll()) {
//            representation.withRepresentation(String.valueOf(installation.id), new RepresentationFactory().newRepresentation().withJsonResource(installation.getForm(), installation.id));
//        }
//
//        //Representation representation = new RepresentationFactory().newRepresentation().withJsonResources(map,"/api/cloud");
//
//        return ok(representation.toString(RepresentationFactory.HAL_JSON));
    	
    	return ok();

    }

    @Transactional
    public static Result jsonNew() {

//        ReadableRepresentation representation = new RepresentationFactory().readRepresentation(request().body().asText());
//
//        InstallationForm installationForm = representation.toJsonResource(InstallationForm.class);
//        Installation installation = new Installation(installationForm);
//        installation.save();
//
//        return redirect(routes.InstallationController.json(installation.id));
    	
    	return ok();
    }

    @Transactional
    public static Result jsonUpdate(final Long id) {
//        final Installation installation = Installation.findById(id);
//
//        if (installation == null) {
//            return notFound();
//        }
//
//        ReadableRepresentation representation = new RepresentationFactory().readRepresentation(request().body().asText());
//
//        InstallationForm installationForm = representation.toJsonResource(InstallationForm.class);
//        installation.setForm(installationForm);
//        installation.save();
//
//        return redirect(routes.InstallationController.json(installation.id));
    	return ok();
    }

    /**
     * Calls the RemoteService of CaactoOpt to find the optimal physical host to start the VM.
     * Simplified for the CactosMolpro scenario: an application will one need one vm
     * @param application the molpro application
     * @return the nodeId of the optimal physical host
     */
    private static String getPhysicalHost(Application application){
        Registry registry;
        String host = null;
        /*
        int numberOfCores = application.applicationComponents.get(0).component.componentConfiguration.hardware.numberOfCpu;
        long memory = application.applicationComponents.get(0).component.componentConfiguration.hardware.mbOfRam;
        long disk = application.applicationComponents.get(0).component.componentConfiguration.hardware.localDiskSpace;
        try {
            registry = LocateRegistry.getRegistry("134.60.30.87", 1099);
            Logger.debug("looking up for RmiVmDeploymentService");
            VmDeploymentInterface deploymentService = (VmDeploymentInterface)registry.lookup("RmiVmDeploymentService");
            Logger.debug("Calling findOptimalHost");
            host =  deploymentService.findOptimalHost(application.displayName, numberOfCores, memory, disk);

            return host;
        } catch (RemoteException e) {
            Logger.error(e.getMessage());

        } catch (NotBoundException e) {
            Logger.error(e.getMessage());
        }*/
        host = "computenode12";
        return host;
    }

//    @Transactional
//    private static void setPhysicalHostToCloudDriver(String host, Application application){
//
//        application.applicationComponents.get(0).component.componentConfiguration.cloud.driver.custom = "\"openstack.compute.zone\":\":"+host+"\"";
//        Logger.info("#############################################");
//        Logger.info(application.applicationComponents.get(0).component.componentConfiguration.cloud.driver.custom);
//        Logger.info("#############################################");
//        application.applicationComponents.get(0).component.componentConfiguration.cloud.driver.save();
//    }


}
