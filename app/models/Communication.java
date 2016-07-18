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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.generic.Model;

/**
 * Created by daniel on 07.01.15.
 */
@Entity public class Communication extends Model {

    @OneToOne(optional = false) @JoinColumn(name = "requiredCommunication") private PortRequired
        requiredPort;
    @ManyToOne(optional = false) private PortProvided providedPort;

    /**
     * Empty constructor for hibernate.
     */
    protected Communication() {

    }

    public Communication(PortRequired requiredPort, PortProvided providedPort) {
        this.requiredPort = requiredPort;
        this.providedPort = providedPort;
    }

    public PortRequired getRequiredPort() {
        return requiredPort;
    }

    public PortProvided getProvidedPort() {
        return providedPort;
    }

    public ApplicationComponent getSource() {
        return getRequiredPort().getApplicationComponent();
    }

    public ApplicationComponent getTarget() {
        return getProvidedPort().getApplicationComponent();
    }

    public boolean isMandatory() {
        return requiredPort.isMandatory();
    }

    public boolean isReflexive() {
        return requiredPort.getApplicationComponent()
            .equals(providedPort.getApplicationComponent());
    }
}
