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


import de.uniulm.omi.cloudiator.sword.api.domain.Resource;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

/**
 * Created by daniel on 12.03.15.
 */
public class AbstractResourceInCloudAndLocation<T extends Resource>
    extends AbstractResourceInCloud<T> implements ResourceInCloudAndLocation {

    private final String location;

    public AbstractResourceInCloudAndLocation(T resource, String cloud) {
        super(resource, cloud);
        this.location = IdScopeByLocations.from(id()).getLocationId();
    }

    @Override public String location() {
        return location;
    }
}
