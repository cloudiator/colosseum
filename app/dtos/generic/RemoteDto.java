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

import de.uniulm.omi.cloudiator.persistance.entities.RemoteState;

/**
 * Created by daniel on 10.08.15.
 */
public abstract class RemoteDto extends ValidatableDto {

    private String remoteId;
    private String providerId;
    private String swordId;
    private RemoteState remoteState;


    @Override public void validation() {
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getSwordId() {
        return swordId;
    }

    public void setSwordId(String swordId) {
        this.swordId = swordId;
    }

    public RemoteState getRemoteState() {
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }
}
