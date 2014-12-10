/*
 * Copyright (c) 2014 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the relation between a cloud and a generic hardware, by specifing
 * the cloud-dependant uuid.
 */
@Entity
public class CloudHardware extends Model {

    /**
     * Empty constructor for Hibernate.
     */
    public CloudHardware() {
    }

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The cloud where this hardware is available.
     */
    @ManyToOne
    private Cloud cloud;

    /**
     * The hardware.
     */
    @ManyToOne
    private Hardware hardware;

    /**
     * The cloud-dependant unique identifier.
     */
    private String uuid;

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        checkNotNull(hardware);
        this.hardware = hardware;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        checkNotNull(uuid);
        checkArgument(!uuid.isEmpty(), "Empty String for uuid provided");
        this.uuid = uuid;
    }
}
