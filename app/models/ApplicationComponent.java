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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.uniulm.omi.cloudiator.lance.lca.container.ContainerType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.generic.Model;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.12.14.
 */
@Entity public class ApplicationComponent extends Model {

    @Enumerated(EnumType.STRING) @Nullable private ContainerType containerType;

    /**
     * Owned relations
     */
    @ManyToOne(optional = false) private Application application;
    @ManyToOne(optional = false) private Component component;
    @ManyToOne(optional = false) private VirtualMachineTemplate virtualMachineTemplate;

    /**
     * Foreign relations
     */
    @OneToMany(mappedBy = "applicationComponent") private List<Instance> instances;
    @OneToMany(mappedBy = "applicationComponent") private List<Port> ports;

    /**
     * Empty constructor for hibernate.
     */
    protected ApplicationComponent() {
    }

    public ApplicationComponent(Application application, Component component,
        VirtualMachineTemplate virtualMachineTemplate, @Nullable ContainerType containerType) {
        checkNotNull(application);
        this.application = application;
        checkNotNull(component);
        this.component = component;
        checkNotNull(virtualMachineTemplate);
        this.virtualMachineTemplate = virtualMachineTemplate;
        this.containerType = containerType;
    }

    public Application getApplication() {
        return application;
    }

    public Component getComponent() {
        return component;
    }

    public VirtualMachineTemplate getVirtualMachineTemplate() {
        return virtualMachineTemplate;
    }

    public List<Instance> getInstances() {
        return ImmutableList.copyOf(instances);
    }

    public List<Port> getPorts() {
        return ImmutableList.copyOf(ports);
    }

    public List<PortProvided> getProvidedPorts() {
        final List<PortProvided> providedPorts =
            getPorts().stream().filter(port -> port instanceof PortProvided)
                .map(port -> (PortProvided) port).collect(Collectors.toList());
        return ImmutableList.copyOf(providedPorts);
    }

    public List<PortRequired> getRequiredPorts() {
        final List<PortRequired> requiredPorts =
            getPorts().stream().filter(port -> port instanceof PortRequired)
                .map(port -> (PortRequired) port).collect(Collectors.toList());
        return ImmutableList.copyOf(requiredPorts);
    }

    public Set<Communication> communications() {
        return ports.stream().flatMap(port -> port.getAttachedCommunications().stream())
            .collect(Collectors.toSet());
    }

    public Set<Communication> getProvidedCommunications() {
        return getProvidedPorts().stream()
            .flatMap(port -> port.getAttachedCommunications().stream()).collect(Collectors.toSet());
    }

    public Set<Communication> getRequiredCommunications() {
        return getRequiredPorts().stream()
            .flatMap(port -> port.getAttachedCommunications().stream()).collect(Collectors.toSet());
    }

    public ContainerType containerTypeOrDefault() {
        if (containerType != null) {
            return containerType;
        }
        return virtualMachineTemplate.image().operatingSystemVendorTypeOrDefault().containerType();
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", getId()).add("application", application)
            .add("component", component).toString();
    }
}
