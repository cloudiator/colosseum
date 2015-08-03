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
 * Created by daniel on 12.12.14.
 */
@Entity public class ApplicationComponent extends Model {

    @ManyToOne(optional = false) private Application application;

    @ManyToOne(optional = false) private Component component;

    @OneToMany(mappedBy = "applicationComponent") private List<Instance> instances;

    @ManyToOne(optional = false) private VirtualMachineTemplate virtualMachineTemplate;

    @OneToMany(mappedBy = "applicationComponent") private List<Port> ports;

    /**
     * Empty constructor for hibernate.
     */
    protected ApplicationComponent() {
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        checkNotNull(application);
        this.application = application;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        checkNotNull(component);
        this.component = component;
    }


    public VirtualMachineTemplate getVirtualMachineTemplate() {
        return virtualMachineTemplate;
    }

    public void setVirtualMachineTemplate(VirtualMachineTemplate virtualMachineTemplate) {
        this.virtualMachineTemplate = virtualMachineTemplate;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
}
