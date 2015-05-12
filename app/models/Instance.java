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
@Entity public class Instance extends Model {

    @ManyToOne(optional = false) private ApplicationComponent applicationComponent;
    @ManyToOne(optional = false) private ApplicationInstance applicationInstance;

    @OneToMany(mappedBy = "provider") private List<CommunicationChannel>
        providedCommunicationChannels;
    @OneToMany(mappedBy = "consumer") private List<CommunicationChannel>
        consumedCommunicationChannels;

    @ManyToOne private VirtualMachine virtualMachine;

    /**
     * Empty constructor for hibernate.
     */
    private Instance() {
    }

    public Instance(ApplicationComponent applicationComponent,
        ApplicationInstance applicationInstance) {
        this.applicationComponent = applicationComponent;
        this.applicationInstance = applicationInstance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        checkNotNull(applicationComponent);
        this.applicationComponent = applicationComponent;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        checkNotNull(virtualMachine);
        this.virtualMachine = virtualMachine;
    }

    public List<CommunicationChannel> getProvidedCommunicationChannels() {
        return providedCommunicationChannels;
    }

    public void setProvidedCommunicationChannels(
        List<CommunicationChannel> providedCommunicationChannels) {
        this.providedCommunicationChannels = providedCommunicationChannels;
    }

    public List<CommunicationChannel> getConsumedCommunicationChannels() {
        return consumedCommunicationChannels;
    }

    public void setConsumedCommunicationChannels(
        List<CommunicationChannel> consumedCommunicationChannels) {
        this.consumedCommunicationChannels = consumedCommunicationChannels;
    }

    public ApplicationInstance getApplicationInstance() {
        return applicationInstance;
    }

    public void setApplicationInstance(ApplicationInstance applicationInstance) {
        this.applicationInstance = applicationInstance;
    }
}
