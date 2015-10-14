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

package dtos.generic;

import dtos.validation.validators.NotNullOrEmptyValidator;

/**
 * Created by daniel on 10.08.15.
 */
public abstract class RemoteDto extends ValidatableDto {

    private String remoteId;
    private String cloudProviderId;


    @Override public void validation() {
        //validator(String.class).validate(remoteId).withValidator(new NotNullOrEmptyValidator());
        //validator(String.class).validate(cloudProviderId)
        //    .withValidator(new NotNullOrEmptyValidator());
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getCloudProviderId() {
        return cloudProviderId;
    }

    public void setCloudProviderId(String cloudProviderId) {
        this.cloudProviderId = cloudProviderId;
    }
}
