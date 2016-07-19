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

package cloud.resources;


import com.google.common.base.MoreObjects;

import de.uniulm.omi.cloudiator.sword.api.domain.Resource;

import cloud.SlashEncodedId;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.03.15.
 */
public abstract class ResourceWithCredential extends BaseLocationScoped implements Resource {

    private final Resource resource;

    public ResourceWithCredential(Resource resource, String cloud, String credential,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(resource, cloud, credential, cloudModelService, cloudCredentialModelService);

        checkNotNull(resource, "Resource must not be null");

        this.resource = resource;
    }

    @Override public String id() {
        return SlashEncodedId.of(credential(), cloud(), resource).userId();
    }

    @Override public String providerId() {
        return resource.providerId();
    }

    @Override public String cloudId() {
        return SlashEncodedId.of(credential(), cloud(), resource).cloudId();
    }

    @Override public String name() {
        return resource.name();
    }

    @Override public String swordId() {
        return SlashEncodedId.of(credential(), cloud(), resource).swordId();
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id()).add("name", name()).toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ResourceWithCredential))
            return false;

        ResourceWithCredential that = (ResourceWithCredential) o;

        return id().equals(that.id());
    }

    @Override public int hashCode() {
        return id().hashCode();
    }
}
