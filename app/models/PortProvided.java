/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 03.08.15.
 */
@Entity public class PortProvided extends Port {

    @Column private Integer port;
    @OneToMany(mappedBy = "providedPort") List<Communication> providedCommunications;

    /**
     * Empty constructor for hibernate.
     */
    protected PortProvided() {
    }

    @Override public Set<Communication> getAttachedCommunications() {
        if (providedCommunications == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(providedCommunications);
    }
    
    public PortProvided(String name, ApplicationComponent applicationComponent, int port) {
        super(name, applicationComponent);
        checkNotNull(port);
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
