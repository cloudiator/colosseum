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


import cloud.DecoratedId;
import de.uniulm.omi.cloudiator.sword.api.domain.Resource;

/**
 * Created by daniel on 12.03.15.
 */
public class AbstractCredentialScopedResource<T extends Resource>
    implements CredentialScoped, RemoteResource {

    private final String cloud;
    private final String credential;
    private final String id;
    private final T resource;


    public AbstractCredentialScopedResource(T resource, String cloud, String credential) {
        this.resource = resource;
        this.id = resource.id();
        this.cloud = cloud;
        this.credential = credential;
    }

    @Override public String cloud() {
        return cloud;
    }

    @Override public String credential() {
        return credential;
    }

    @Override public String id() {
        return DecoratedId.of(credential, cloud, id).colosseumId();
    }

    @Override public String name() {
        return resource.name();
    }

    @Override public String cloudProviderId() {
        return id;
    }
}
