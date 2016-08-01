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
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * Created by daniel on 03.08.15.
 */
@Entity public class PortRequired extends Port {

    private @OneToOne(mappedBy = "requiredPort") Communication requiredCommunication;
    private @Nullable @Lob String updateAction;
    private @Nullable Boolean isMandatory;

    /**
     * Default constructor for hibernate.
     */
    protected PortRequired() {
    }

    @Override public Set<Communication> getAttachedCommunications() {
        return Collections.singleton(requiredCommunication);
    }

    public PortRequired(String name, ApplicationComponent applicationComponent,
        @Nullable String updateAction, @Nullable Boolean isMandatory) {
        super(name, applicationComponent);
        this.updateAction = updateAction;
    }

    @Nullable public Communication communication() {
        return requiredCommunication;
    }

    public Optional<String> updateAction() {
        return Optional.ofNullable(updateAction);
    }

    public boolean isMandatory() {
        if (isMandatory == null) {
            return false;
        }
        return isMandatory;
    }
}
