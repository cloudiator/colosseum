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

package cloud.resources;

import cloud.SlashEncodedId;
import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import de.uniulm.omi.cloudiator.sword.api.domain.LocationScope;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 28.05.15.
 */
public class LocationInCloud extends BaseCredentialScoped implements Location {

    private final Location location;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;


    public LocationInCloud(Location location, String cloud, String credential,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(cloud, credential, cloudModelService, cloudCredentialModelService);

        checkNotNull(location, "Location must not be null.");

        this.location = location;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public LocationScope locationScope() {
        return location.locationScope();
    }

    @Override public boolean isAssignable() {
        return location.isAssignable();
    }

    @Override public Optional<Location> parent() {
        if (location.parent().isPresent()) {
            return Optional.of(new LocationInCloud(location.parent().get(), cloud().getUuid(),
                credential().getUuid(), cloudModelService, cloudCredentialModelService));
        }
        return Optional.empty();
    }

    @Override public String id() {
        return SlashEncodedId.of(credential(), cloud(), location).userId();
    }

    @Override public String name() {
        return location.name();
    }

    @Override public String cloudId() {
        return SlashEncodedId.of(credential(), cloud(), location).cloudId();
    }

    @Override public String cloudProviderId() {
        return SlashEncodedId.of(credential(), cloud(), location).swordId();
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id()).add("name", name()).toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LocationInCloud))
            return false;

        LocationInCloud that = (LocationInCloud) o;

        return id().equals(that.id());
    }

    @Override public int hashCode() {
        return id().hashCode();
    }
}
