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


import de.uniulm.omi.cloudiator.sword.service.ServiceBuilder;
import models.Cloud;
import models.CloudCredential;

/**
 * Created by daniel on 10.03.15.
 */
public class SwordFactory implements CloudServiceFactory {

    private CloudService createCloudService(String providerName, String endpoint, String username,
        String password, String cloud, String credential) {
        return new SwordCloudService(
            ServiceBuilder.newServiceBuilder(providerName).endpoint(endpoint)
                .credentials(username, password).nodeGroup("geagzjkue").build(), cloud, credential);
    }

    @Override public CloudService from(CloudCredential cloudCredential) {
        return createCloudService(
            cloudCredential.getCloud().getCloudApi().getApi().getInternalProviderName(),
            cloudCredential.getCloud().getCloudApi().getEndpoint(), cloudCredential.getUser(),
            cloudCredential.getSecret(), cloudCredential.getCloud().getUuid(),
            cloudCredential.getUuid());
    }

    @Override public CloudService from(Cloud cloud) {
        return null;
    }

    @Override public CloudService from() {
        return null;
    }
}
