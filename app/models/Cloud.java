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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import models.generic.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Cloud extends Model {

    @Column(unique = true, nullable = false) private String name;
    @Column(nullable = false) private String endpoint;
    @ManyToOne(optional = false) private Api api;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Image> images;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Location> locations;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Hardware> hardware;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<VirtualMachine>
        virtualMachines;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<CloudCredential>
        cloudCredentials;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<CloudProperty>
        cloudProperties;

    /**
     * Empty constructor. Needed by hibernate.
     */
    protected Cloud() {
    }

    public Cloud(String name, String endpoint, Api api) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        checkNotNull(endpoint);
        checkArgument(!endpoint.isEmpty());
        checkNotNull(api);
        this.name = name;
        this.endpoint = endpoint;
        this.api = api;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Api api() {
        return api;
    }

    public List<Image> images() {
        return images;
    }

    public List<Location> locations() {
        return ImmutableList.copyOf(locations);
    }

    public List<Hardware> hardware() {
        return hardware;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return ImmutableList.copyOf(virtualMachineTemplates);
    }

    public List<VirtualMachine> getVirtualMachines() {
        return ImmutableList.copyOf(virtualMachines);
    }

    public List<CloudCredential> getCloudCredentials() {
        return ImmutableList.copyOf(cloudCredentials);
    }

    public void addProperty(CloudProperty cloudProperty) {
        this.cloudProperties.add(cloudProperty);
    }

    public Map<String, String> properties() {
        Map<String, String> resultMap = Maps.newHashMapWithExpectedSize(cloudProperties.size());
        for (CloudProperty cloudProperty : cloudProperties) {
            resultMap.put(cloudProperty.key(), cloudProperty.value());
        }
        return resultMap;
    }
}
