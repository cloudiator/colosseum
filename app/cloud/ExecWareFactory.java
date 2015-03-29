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


import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.service.ServiceBuilder;
import models.Cloud;
import models.CloudCredential;

/**
 * Created by daniel on 10.03.15.
 */
public class ExecWareFactory implements CloudServiceFactory {

    private ComputeService createComputeService(String providerName, String endpoint,
        String username, String password, String nodeGroupName) {
        return ServiceBuilder.newServiceBuilder("cloudProviderName").endpoint("endpoint")
            .credentials("username", "password").nodeGroup("nodeGroupName").build();
    }

    @Override public ComputeService from(CloudCredential cloudCredential) {
        return null;
    }

    @Override public MultiCloudComputeService from(Cloud cloud) {
        return null;
    }

    @Override public MultiCloudComputeService from() {
        return null;
    }
}
