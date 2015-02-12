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
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * The model class hardware.
 * <p>
 * Represents a specific hardware configuration provided by the cloud.
 *
 * @author Daniel Baur
 */
@Entity
public class Hardware extends Model {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Number of computing units.
     */
    private Integer numberOfCpu;

    /**
     * The amount of ram.
     * <p>
     * Integrates machine_memory from Template.
     */
    private Long mbOfRam;

    /**
     * The amount of disk space available locally on the machine.
     */
    private Long localDiskSpace;

    /**
     * The cloud hardware where this hardware is used (ManyToMany)
     */
    @OneToMany(mappedBy = "hardware")
    private List<CloudHardware> cloudHardware;

    @OneToMany(mappedBy = "hardware")
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * Empty constructor needed by hibernate.
     */
    public Hardware() {
    }

    public Integer getNumberOfCpu() {
        return numberOfCpu;
    }

    public void setNumberOfCpu(Integer numberOfCpu) {
        checkNotNull(numberOfCpu);
        this.numberOfCpu = numberOfCpu;
    }


    public Long getMbOfRam() {
        return mbOfRam;
    }

    public void setMbOfRam(Long mbOfRam) {
        checkNotNull(mbOfRam);
        this.mbOfRam = mbOfRam;
    }

    public Long getLocalDiskSpace() {
        return localDiskSpace;
    }

    public void setLocalDiskSpace(Long localDiskSpace) {
        checkNotNull(localDiskSpace);
        this.localDiskSpace = localDiskSpace;
    }

    public List<CloudHardware> getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(List<CloudHardware> cloudHardware) {
        checkNotNull(cloudHardware);
        this.cloudHardware = cloudHardware;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }
}
