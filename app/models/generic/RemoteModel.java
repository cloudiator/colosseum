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

package models.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by daniel on 12.05.15.
 */
@MappedSuperclass public abstract class RemoteModel extends Model {

    private RemoteState remoteState;
    @Column(unique = true, nullable = true) private String remoteId;
    @Column(updatable = false) private String cloudProviderId;

    public RemoteState getRemoteState() {
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        if (this.remoteId != null) {
            throw new IllegalStateException(
                "Changing the remoteId of a RemoteModel is not allowed.");
        }
        this.remoteId = remoteId;
    }

    /**
     * Empty constructor for hibernate
     */
    protected RemoteModel() {
    }

    public RemoteModel(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getCloudProviderId() {
        return cloudProviderId;
    }

    public void setCloudProviderId(String cloudProviderId) {
        this.cloudProviderId = cloudProviderId;
    }
}
