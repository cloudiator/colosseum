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
import models.FrontendUser;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;
import play.db.jpa.JPAApi;

/**
 * Created by daniel on 25.11.14.
 */
class InitialData {

    private final FrontendUserService frontendUserService;
    private final ModelService<Tenant> tenantModelService;
    private static final String DEFAULT_GROUP = "admin";
    private final JPAApi jpaApi;

    @Inject public InitialData(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, JPAApi jpaApi) {
        this.frontendUserService = frontendUserService;
        this.tenantModelService = tenantModelService;
        this.jpaApi = jpaApi;
    }

    void load() {
        jpaApi.withTransaction(this::loadInitialData);
    }

    /**
     * Creates a default system user and a default tenant.
     */
    private void loadInitialData() {

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
                //tenantModelService.save(tenant);
            }

            FrontendUser frontendUser =
                new FrontendUser("John", "Doe", "admin", "john.doe@example.com");
            frontendUserService.save(frontendUser);
            tenant.addFrontendUser(frontendUser);
            tenantModelService.save(tenant);

        }
    }
}
