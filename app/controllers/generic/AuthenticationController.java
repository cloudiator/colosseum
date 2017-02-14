/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers.generic;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import controllers.security.TenantAwareAuthenticator;
import de.uniulm.omi.cloudiator.persistance.entities.Cloud;
import de.uniulm.omi.cloudiator.persistance.entities.CloudCredential;
import de.uniulm.omi.cloudiator.persistance.entities.FrontendUser;
import de.uniulm.omi.cloudiator.persistance.entities.Tenant;
import de.uniulm.omi.cloudiator.persistance.repositories.FrontendUserService;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;
import play.mvc.Controller;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 03.07.15.
 */
public class AuthenticationController extends Controller {

    private final FrontendUserService frontendUserService;
    private final ModelService<Tenant> tenantModelService;

    @Inject public AuthenticationController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService) {

        checkNotNull(frontendUserService);
        checkNotNull(tenantModelService);

        this.frontendUserService = frontendUserService;
        this.tenantModelService = tenantModelService;
    }

    private String[] getAuth() {
        String[] auth = request().username().split(TenantAwareAuthenticator.SEPARATOR);
        checkState(auth.length == 2, "Received illegal auth information.");
        return auth;
    }

    protected FrontendUser getUser() {
        String mail = getAuth()[1];
        FrontendUser frontendUser = frontendUserService.getByMail(mail);
        checkState(frontendUser != null, "The logged-in user does not exist in the db.");
        return frontendUser;
    }

    protected Tenant getActiveTenant() {
        String tenantString = getAuth()[0];

        Tenant tenant = null;
        //todo: replace with method in service.
        List<Tenant> tenants = tenantModelService.getAll();
        for (Tenant tenantInDb : tenants) {
            if (tenantInDb.getName().equals(tenantString)) {
                tenant = tenantInDb;
                break;
            }
        }

        checkState(tenant != null, "The used tenant does not exist in the db.");
        return tenant;
    }

    @Nullable protected CloudCredential getCloudCredential(Cloud cloud) {
        Tenant tenant = getActiveTenant();
        for (CloudCredential cloudCredential : tenant.getCloudCredentials()) {
            if (cloudCredential.getCloud().equals(cloud)) {
                return cloudCredential;
            }
        }
        return null;
    }
}
