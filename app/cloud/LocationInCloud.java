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

package cloud;



import de.uniulm.omi.cloudiator.sword.api.domain.Location;

/**
 * Created by daniel on 12.03.15.
 */
public class LocationInCloud implements Location, ResourceInCloud {

    private String cloud;
    private String credential;
    private Location location;

    public LocationInCloud(Location location, String cloud, String credential) {
        this.location = location;
        this.cloud = cloud;
        this.credential = credential;
    }

    @Override public boolean isAssignable() {
        return location.isAssignable();
    }

    @Override public String id() {
        return CloudCredentialLocationId.of(location.id(), cloud, credential).id();
    }

    @Override public String name() {
        return location.name();
    }

    @Override public String cloud() {
        return cloud;
    }

    @Override public String credential() {
        return credential;
    }
}
