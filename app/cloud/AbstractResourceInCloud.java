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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.03.15.
 */
public abstract class AbstractResourceInCloud<T extends Resource> implements ResourceInCloud {

    protected final Resource resource;
    protected final String cloud;
    protected final String credential;

    public AbstractResourceInCloud(T resource, String cloud, String credential) {

        checkNotNull(resource);
        checkNotNull(cloud);
        checkArgument(!cloud.isEmpty());
        checkNotNull(credential);
        checkArgument(!credential.isEmpty());

        this.resource = resource;
        this.cloud = cloud;
        this.credential = credential;
    }

    @Override public String cloud() {
        return cloud;
    }

    @Override public String id() {
        return resource.id();
    }

    @Override public String name() {
        return resource.name();
    }

    @Override public String credential() {
        return credential;
    }
}
