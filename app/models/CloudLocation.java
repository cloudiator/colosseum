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

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Represents the relation between a cloud and a generic location, by specifing
 * the cloud-dependant cloudUuid.
 */
@Entity
public class CloudLocation extends Model {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The cloud where this location is available.
     */
    @ManyToOne(optional = false)
    private Cloud cloud;

    /**
     * The location.
     */
    @ManyToOne(optional = false)
    private Location location;

    @OneToMany(mappedBy = "cloudLocation")
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * The cloud-dependent unique identifier.
     */
    private String cloudUuid;

    /**
     * Empty constructor needed for hibernate.
     */
    public CloudLocation() {

    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        checkNotNull(location);
        this.location = location;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String uuid) {
        checkNotNull(uuid);
        this.cloudUuid = uuid;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }
}
