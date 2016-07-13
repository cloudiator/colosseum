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

import models.generic.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 03.08.15.
 */
@Entity public abstract class Port extends Model {

    @Column(updatable = false, unique = true, nullable = false) private String name;
    @ManyToOne(optional = false) private ApplicationComponent applicationComponent;

    /**
     * Default constructor for hibernate.
     */
    protected Port() {
    }

    public Port(String name, ApplicationComponent applicationComponent) {

        checkNotNull(name);
        checkArgument(!name.isEmpty());
        checkNotNull(applicationComponent);

        this.name = name;
        this.applicationComponent = applicationComponent;
    }

    public String name() {
        return name;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public abstract Set<Communication> getAttachedCommunications();
}
