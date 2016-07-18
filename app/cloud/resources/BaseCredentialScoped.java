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

import com.google.common.base.MoreObjects;

import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 02.11.15.
 */
public abstract class BaseCredentialScoped implements CredentialScoped {

    private final String cloud;
    private final String credential;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    public BaseCredentialScoped(String cloud, String credential,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {

        checkNotNull(cloud, "Cloud must not be null.");
        checkArgument(!cloud.isEmpty(), "Cloud must not be empty.");
        checkNotNull(credential, "Credential must not be empty.");
        checkArgument(!credential.isEmpty(), "Credential must not be empty.");
        checkNotNull(cloudModelService, "CloudModelService must not be null.");
        checkNotNull(cloudCredentialModelService, "CloudCredentialModelService must not be null.");

        this.cloud = cloud;
        this.credential = credential;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public CloudCredential credential() {
        CloudCredential ret = cloudCredentialModelService.getByUuid(credential);
        checkState(ret != null, String
            .format("CloudCredential with UUID = %s could not be found in database", credential));
        return ret;
    }

    @Override public Cloud cloud() {
        Cloud ret = cloudModelService.getByUuid(cloud);
        checkState(ret != null,
            String.format("Cloud with UUID = %s could not be found in database", cloud));
        return ret;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("cloud", cloud).add("credential", credential)
            .toString();
    }
}
