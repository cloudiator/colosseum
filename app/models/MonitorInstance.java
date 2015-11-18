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

package models;

import models.generic.ModelWithExternalReference;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity public class MonitorInstance extends ModelWithExternalReference {

    @ManyToOne(optional = false) private Monitor monitor;
    private String apiEndpoint;
    @ManyToOne(optional = true) @Nullable private IpAddress ipAddress;
    @ManyToOne(optional = true) @Nullable private VirtualMachine virtualMachine;
    @ManyToOne(optional = true) @Nullable private Component component;

    /**
     * Empty constructor for hibernate.
     */
    protected MonitorInstance() {
    }

    public MonitorInstance(Monitor monitor, String apiEndpoint, @Nullable IpAddress ipAddress,
        @Nullable VirtualMachine virtualMachine, @Nullable Component component) {
        this.apiEndpoint = apiEndpoint;
        this.monitor = monitor;
        this.ipAddress = ipAddress;
        this.virtualMachine = virtualMachine;
        this.component = component;
    }

    @Nullable public Monitor getMonitor() {
        return monitor;
    }

    @Nullable public IpAddress getIpAddress() {
        return ipAddress;
    }

    @Nullable public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    @Nullable public Component getComponent() {
        return component;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }
}
