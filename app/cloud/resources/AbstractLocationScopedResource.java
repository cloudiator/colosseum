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

import cloud.DecoratedId;
import de.uniulm.omi.cloudiator.sword.api.domain.Resource;
import de.uniulm.omi.cloudiator.sword.api.util.IdScopedByLocation;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

/**
 * Created by daniel on 20.05.15.
 */
public class AbstractLocationScopedResource<T extends Resource>
    extends AbstractCredentialScopedResource<T>
    implements CredentialScoped, LocationScoped, RemoteResource {

    private final String location;
    private final String id;

    public AbstractLocationScopedResource(T resource, String cloud, String credential) {
        super(resource, cloud, credential);
        IdScopedByLocation idScopedByLocation = IdScopeByLocations.from(resource.id());
        this.id = idScopedByLocation.getId();
        this.location = idScopedByLocation.getLocationId();
    }

    @Override public String id() {
        return DecoratedId.of(credential(), cloud(), location, id).colosseumId();
    }

    @Override public String location() {
        return DecoratedId.of(credential(), cloud(), location, id).colosseumLocation();
    }
}
