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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by daniel on 07.01.15.
 */
@Entity public class Communication extends Model {

    @ManyToOne(optional = false) private ApplicationComponent provider;
    @ManyToOne(optional = false) private ApplicationComponent consumer;
    @OneToMany(mappedBy = "communication") private List<CommunicationChannel> communicationChannels;
    @Column(nullable = false) private Integer port;

    /**
     * Empty constructor for hibernate.
     */
    protected Communication() {
    }

    public ApplicationComponent getProvider() {
        return provider;
    }

    public void setProvider(ApplicationComponent provider) {
        this.provider = provider;
    }

    public ApplicationComponent getConsumer() {
        return consumer;
    }

    public void setConsumer(ApplicationComponent consumer) {
        this.consumer = consumer;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<CommunicationChannel> getCommunicationChannels() {
        return communicationChannels;
    }

    public void setCommunicationChannels(List<CommunicationChannel> communicationChannels) {
        this.communicationChannels = communicationChannels;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
