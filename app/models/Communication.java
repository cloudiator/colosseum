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

/**
 * Created by daniel on 07.01.15.
 */
@Entity public class Communication extends Model {

    @ManyToOne(optional = false) private PortRequired inboundPort;
    @ManyToOne(optional = false) private PortProvided outboundPort;

    /**
     * Empty constructor for hibernate.
     */
    protected Communication() {

    }

    public Communication(PortRequired inboundPort, PortProvided outboundPort) {
        this.inboundPort = inboundPort;
        this.outboundPort = outboundPort;
    }

    public PortRequired getInboundPort() {
        return inboundPort;
    }

    public PortProvided getOutboundPort() {
        return outboundPort;
    }
}
