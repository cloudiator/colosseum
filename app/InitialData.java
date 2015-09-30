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
    private static final String DEFAULT_GROUP = "admin";

    @Inject public InitialData(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService,
        ModelService<OperatingSystem> operatingSystemModelService,
        ModelService<OperatingSystemVendor> operatingSystemVendorModelService) {
        this.frontendUserService = frontendUserService;
        this.tenantModelService = tenantModelService;
        this.operatingSystemModelService = operatingSystemModelService;
        this.operatingSystemVendorModelService = operatingSystemVendorModelService;
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
                new OperatingSystem(OperatingSystemArchitecture.AMD64, ubuntu, "14.04");
            operatingSystemModelService.save(ubuntu1404amd64);

            //ubuntu 14.04.2 amd64
            OperatingSystem ubuntu14042amd64 =
                new OperatingSystem(OperatingSystemArchitecture.AMD64, ubuntu, "14.04.2");
            operatingSystemModelService.save(ubuntu14042amd64);

            //load windows
            OperatingSystemVendor windows =
                new OperatingSystemVendor("Windows", OperatingSystemVendorType.WINDOWS,
                    "Administrator", "Admin1");
            operatingSystemVendorModelService.save(windows);

            //Windows Server 2012 R2
            OperatingSystem windowsServer2012R2 =
                new OperatingSystem(OperatingSystemArchitecture.AMD64, windows, "Server 2012 R2");
            operatingSystemModelService.save(windowsServer2012R2);
        }


    }
}
