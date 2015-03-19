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

import models.generic.NamedModel;

import javax.persistence.*;
import java.util.List;

@Entity public class Cloud extends NamedModel {

    @OneToOne(mappedBy = "cloud", cascade = CascadeType.REMOVE, optional = false) private CloudApi
        cloudApi;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Image> images;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Location> locations;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Hardware> hardware;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<CloudVirtualMachine>
        virtualMachines;

    /**
     * Empty constructor. Needed by hibernate.
     */
    private Cloud() {
    }

    public Cloud(String name) {
        super(name);
    }

    public CloudApi getCloudApi() {
        return cloudApi;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public List<CloudVirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }
}
