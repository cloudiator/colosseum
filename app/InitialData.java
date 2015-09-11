/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.google.inject.Inject;
import models.*;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by daniel on 25.11.14.
 */
public class InitialData {

    private final FrontendUserService frontendUserService;
    private final ModelService<Tenant> tenantModelService;
    private final ModelService<OperatingSystem> operatingSystemModelService;
    private final ModelService<OperatingSystemVendor> operatingSystemVendorModelService;
	private final ModelService<CloudCredential> cloudCredentialModelService; 
	private final ModelService<Api> apiModelService;
	private final ModelService<Cloud> cloudModelService;
	private final ModelService<CloudProperty> cloudPropertyModelService;
	
    private static final String DEFAULT_GROUP = "admin";

    @Inject public InitialData(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService,
        ModelService<OperatingSystem> operatingSystemModelService,
        ModelService<OperatingSystemVendor> operatingSystemVendorModelService,
        ModelService<CloudCredential> cloudCredentialModelService, 
        ModelService<Api> apiModelService,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudProperty> cloudPropertyModelService
    		) {
        this.frontendUserService = frontendUserService;
        this.tenantModelService = tenantModelService;
        this.operatingSystemModelService = operatingSystemModelService;
        this.operatingSystemVendorModelService = operatingSystemVendorModelService;
		this.cloudCredentialModelService = cloudCredentialModelService;
		this.apiModelService = apiModelService;        
		this.cloudModelService = cloudModelService;
		this.cloudPropertyModelService = cloudPropertyModelService;
    }

    public void loadInitialData() {
        /**
         * Creates a default system user and a default tenant.
         */
        if (frontendUserService.getAll().isEmpty()) {
            Tenant tenant = null;
            for (Tenant storedTenant : tenantModelService.getAll()) {
                if (DEFAULT_GROUP.equals(storedTenant.getName())) {
                    tenant = storedTenant;
                    break;
                }
            }
            if (tenant == null) {
                tenant = new Tenant(DEFAULT_GROUP);
                tenantModelService.save(tenant);
            }

            FrontendUser frontendUser =
                new FrontendUser("John", "Doe", "admin", "john.doe@example.com");
            frontendUserService.save(frontendUser);
            tenant.getFrontendUsers().add(frontendUser);
            tenantModelService.save(tenant);

        }

        if (operatingSystemVendorModelService.getAll().isEmpty()) {

            //load ubuntu
            OperatingSystemVendor ubuntu =
                new OperatingSystemVendor("Ubuntu", OperatingSystemVendorType.NIX, "ubuntu", null);
            operatingSystemVendorModelService.save(ubuntu);

            //ubuntu 14.04 amd64
            OperatingSystem ubuntu1404amd64 =
                new OperatingSystem(ubuntu, OperatingSystemArchitecture.AMD64, "14.04");
            operatingSystemModelService.save(ubuntu1404amd64);

            //ubuntu 14.04.2 amd64
            OperatingSystem ubuntu14042amd64 =
                new OperatingSystem(ubuntu, OperatingSystemArchitecture.AMD64, "14.04.2");
            operatingSystemModelService.save(ubuntu14042amd64);

            //load windows
            OperatingSystemVendor windows =
                new OperatingSystemVendor("Windows", OperatingSystemVendorType.WINDOWS,
                    "Administrator", "Admin1");
            operatingSystemVendorModelService.save(windows);

            //Windows Server 2012 R2
            OperatingSystem windowsServer2012R2 =
                new OperatingSystem(windows, OperatingSystemArchitecture.AMD64, "Server 2012 R2");
            operatingSystemModelService.save(windowsServer2012R2);
        }

        // Initiate Molpro Setup
        if(cloudModelService.getAll().isEmpty()){
    		Api api = new Api("omistack-beta-api", "openstack-nova");
    		apiModelService.save(api);
    		
    		Cloud cloud = new Cloud("omistack-beta", "http://omistack-beta.e-technik.uni-ulm.de:5000/v2.0", api);
    		cloudModelService.save(cloud);
    		
    		CloudProperty cloudPropertyFloatingIp = new CloudProperty("sword.openstack.floatingIpPool", "extnet2", cloud);
    		cloudPropertyModelService.save(cloudPropertyFloatingIp);
    		
    		Tenant tenant = tenantModelService.getAll().get(0);
    		CloudCredential cc = new CloudCredential(cloud, tenant, "cactos:cactos-tools", "2ab89636ba");
    		cloudCredentialModelService.save(cc);
    	}        

    }
}
