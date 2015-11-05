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

package models.generic;

import models.Cloud;
import models.CloudCredential;
import models.Location;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Optional;

/**
 * Created by daniel on 22.09.15.
 */
@Entity public abstract class RemoteResourceInLocation extends RemoteResourceInCloud {

    @ManyToOne private Location location;

    /**
     * No-arg constructor for hibernate.
     */
    protected RemoteResourceInLocation() {
    }

    public RemoteResourceInLocation(Cloud cloud, Location location) {
        super(cloud);
        this.location = location;
    }

    public RemoteResourceInLocation(@Nullable String remoteId, @Nullable String cloudProviderId,
        Cloud cloud, @Nullable CloudCredential owner, @Nullable Location location) {
        super(remoteId, cloudProviderId, cloud, owner);
        this.location = location;
    }

    public Optional<Location> location() {
        return Optional.ofNullable(location);
    }

}
