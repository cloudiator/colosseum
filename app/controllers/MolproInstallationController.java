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

import com.google.inject.Inject;

import components.job.JobService;
import controllers.generic.AuthenticationController;
import controllers.security.SecuredSessionOrToken;
//import forms.ComponentConfigurationForm;
import models.Application;
import models.ApplicationInstance;
import models.Cloud;
import models.Hardware;
import models.Image;
import models.Instance;
import models.Location;
import models.MolproComputation;
import models.Tenant;
import models.VirtualMachine;
import models.VirtualMachineTemplate;
import models.service.FrontendUserService;
import models.service.ModelService;
//import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(SecuredSessionOrToken.class)
public class MolproInstallationController extends AuthenticationController {

	private final ModelService<Application> applicationModelService;
	private final ModelService<VirtualMachine> virtualMachineModelService;
	private final ModelService<ApplicationInstance> applicationInstanceModelService;
	private final ModelService<Instance> instanceModelService;
	
	
	private final JobService jobService;
	@Inject
	public MolproInstallationController(
			ModelService<Application> applicationModelService,
			ModelService<VirtualMachine> virtualMachineModelService,
			JobService jobService,
			FrontendUserService frontendUserService,
	        ModelService<Tenant> tenantModelService,
	        ModelService<ApplicationInstance> applicationInstanceModelService,
	        ModelService<Instance> instanceModelService
			){
		super(frontendUserService, tenantModelService);
		this.applicationModelService = applicationModelService;
		this.virtualMachineModelService = virtualMachineModelService;
		this.jobService = jobService;
		this.applicationInstanceModelService = applicationInstanceModelService;
		this.instanceModelService = instanceModelService;
	}

    /**
     * Creates a Molpro Installation by the provided idApplication
     *
     *
     * @return the rendered view
     */
    @Transactional
    public Result createMolpro(Long idApplication) {
        Application application = applicationModelService.getById(idApplication);
        VirtualMachineTemplate vmt = application.getApplicationComponents().get(0).getVirtualMachineTemplate();

        // FIRST: start virtual machine
        String name = "vm-" + application.getName();
		Cloud cloud = vmt.getCloud(); 
		String cloudUuid = null; // @Nullable 
        Hardware hardware = vmt.getHardware(); // @Nullable  
        Image image = vmt.getImage(); // @Nullable  
        Location location = vmt.getLocation(); // @Nullable 
        String generatedLoginUsername = null; // @Nullable  
        String generatedLoginPassword = null; // @Nullable 
        VirtualMachine vm = new VirtualMachine(
				name, 
				cloud, 
				cloudUuid,
				hardware, 
				image, 
				location,
				generatedLoginUsername, 
				generatedLoginPassword
          );
        virtualMachineModelService.save(vm);
        vm.addCloudCredential(getCloudCredential(cloud));
        virtualMachineModelService.save(vm);
        jobService.newVirtualMachineJob(vm, getActiveTenant());        
        
        // SECOND: start application
        ApplicationInstance appinstance = new ApplicationInstance(application);
        applicationInstanceModelService.save(appinstance);
        Instance instance = new Instance(application.getApplicationComponents().get(0), appinstance);
        instanceModelService.save(instance);
        instance.setVirtualMachine(vm);
        instanceModelService.save(instance);
        jobService.newInstanceJob(instance, getActiveTenant());

        // FINALLY: change molproComputation state
        MolproComputation molproComputation = MolproComputation.findByApplication(application);
        molproComputation.started = true;
        molproComputation.save();

        flash("success", "The installation was successfully created.");
        return redirect(routes.MolproComputationController.index());
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
