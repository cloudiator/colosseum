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
import play.Logger;
import play.db.jpa.JPAApi;
import util.logging.Loggers;

/**
 * Created by daniel on 25.11.14.
 */
class InitialData {

    private static Logger.ALogger LOGGER = Loggers.of(Loggers.SYSTEM);
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
        LOGGER.info("Starting to load initial data.");
        jpaApi.withTransaction(this::loadInitialData);
        LOGGER.info("Finished to load initial data.");
    }

    /**
     * Creates a default system user and a default tenant.
     */
    private void loadInitialData() {

        LOGGER.debug("Checking if default FrontendUser does exist...");
        if (frontendUserService.getAll().isEmpty()) {
            LOGGER.debug("No FrontendUser exists...");
            Tenant tenant = null;
            LOGGER.debug("Checking if default tenant (" + DEFAULT_GROUP + ") exists...");
            for (Tenant storedTenant : tenantModelService.getAll()) {
                if (DEFAULT_GROUP.equals(storedTenant.getName())) {
                    tenant = storedTenant;
                    break;
                }
            }
            if (tenant == null) {
                LOGGER.debug("Default Tenant is missing, creating it (" + DEFAULT_GROUP + ")");
                tenant = new Tenant(DEFAULT_GROUP);
            }

            FrontendUser frontendUser =
                new FrontendUser("John", "Doe", "admin", "john.doe@example.com");
            LOGGER.debug("Creating default frontendUser " + frontendUser);
            LOGGER.debug("Persisting changes.");
            frontendUserService.save(frontendUser);
            tenant.addFrontendUser(frontendUser);
            tenantModelService.save(tenant);
        }
    }
}
