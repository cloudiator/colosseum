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

package cloud.sword.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import de.uniulm.omi.cloudiator.sword.api.domain.LocationScoped;

import java.util.Optional;

import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 02.11.15.
 */
public abstract class BaseLocationScoped extends BaseCredentialScoped implements LocationScoped {

    private final LocationScoped locationScoped;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    public BaseLocationScoped(LocationScoped locationScoped, String cloud, String credential,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(cloud, credential, cloudModelService, cloudCredentialModelService);

        checkNotNull(locationScoped, "LocationScoped must not be null.");

        this.locationScoped = locationScoped;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;

    }

    @Override public Optional<Location> location() {
        if (locationScoped.location().isPresent()) {
            return Optional
                .of(new LocationInCloud(locationScoped.location().get(), cloud().getUuid(),
                    credential().getUuid(), cloudModelService, cloudCredentialModelService));
        }
        return Optional.empty();
    }
}
