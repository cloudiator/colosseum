/*
 * Copyright (c) 2014-2016 University of Ulm
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

import de.uniulm.omi.cloudiator.sword.api.domain.SecurityGroup;
import de.uniulm.omi.cloudiator.sword.api.domain.SecurityGroupRule;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import java.util.Set;

/**
 * Created by daniel on 20.07.16.
 */
public class SecurityGroupInLocation extends ResourceWithCredential implements SecurityGroup {

    private final SecurityGroup securityGroup;

    public SecurityGroupInLocation(SecurityGroup securityGroup, String cloud, String credential,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(securityGroup, cloud, credential, cloudModelService, cloudCredentialModelService);
        this.securityGroup = securityGroup;
    }

    @Override public Set<SecurityGroupRule> rules() {
        return securityGroup.rules();
    }
}
