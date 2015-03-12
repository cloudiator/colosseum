package models;/*
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

import models.generic.Model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 10.03.15.
 */
public class CloudVirtualMachine extends Model {

    @ManyToOne(optional = false)
    private Cloud cloud;

    @OneToOne(optional = false)
    private VirtualMachine virtualMachine;

    @Column(updatable = false, nullable = false)
    private String cloudUuid;

    /**
     * Empty constructor for hibernate.
     */
    private CloudVirtualMachine() {
    }

    private CloudVirtualMachine(Cloud cloud, VirtualMachine virtualMachine, String cloudUuid) {
        checkNotNull(cloud);
        checkNotNull(virtualMachine);
        checkNotNull(cloudUuid);
        checkArgument(!cloudUuid.isEmpty());

        this.cloud = cloud;
        this.virtualMachine = virtualMachine;
        this.cloudUuid = cloudUuid;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        checkNotNull(virtualMachine);
        this.virtualMachine = virtualMachine;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        checkNotNull(cloudUuid);
        checkArgument(!cloudUuid.isEmpty());
        this.cloudUuid = cloudUuid;
    }
}
