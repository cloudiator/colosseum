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

package cloud;

import cloud.resources.SecurityGroupInLocation;
import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import de.uniulm.omi.cloudiator.sword.api.domain.SecurityGroup;
import de.uniulm.omi.cloudiator.sword.api.domain.SecurityGroupRule;
import de.uniulm.omi.cloudiator.sword.api.extensions.SecurityGroupService;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by daniel on 20.07.16.
 */
public class MultiCloudSecurityGroupService implements SecurityGroupService {

    private final String cloudId;
    private final String cloudCredentialId;
    private final SecurityGroupService delegate;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    MultiCloudSecurityGroupService(String cloudId, String cloudCredentialId,
        SecurityGroupService delegate, ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.cloudId = cloudId;
        this.cloudCredentialId = cloudCredentialId;
        this.delegate = delegate;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public Set<SecurityGroup> listSecurityGroups() {
        return delegate.listSecurityGroups().stream().map(
            securityGroup -> new SecurityGroupInLocation(securityGroup, cloudId, cloudCredentialId,
                cloudModelService, cloudCredentialModelService)).collect(Collectors.toSet());
    }

    @Override public SecurityGroup createSecurityGroup(String name, Location location) {
        return new SecurityGroupInLocation(delegate.createSecurityGroup(name, location), cloudId,
            cloudCredentialId, cloudModelService, cloudCredentialModelService);
    }

    @Override public SecurityGroup addRule(SecurityGroupRule rule, SecurityGroup securityGroup) {
        return new SecurityGroupInLocation(delegate.addRule(rule, securityGroup), cloudId,
            cloudCredentialId, cloudModelService, cloudCredentialModelService);
    }
}
