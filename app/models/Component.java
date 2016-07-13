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
import models.generic.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.12.14.
 */
@Entity public abstract class Component extends Model {

    @OneToMany(mappedBy = "component") private List<ApplicationComponent> applicationComponents;
    @Column(unique = true, nullable = false) private String name;

    /**
     * Empty constructor for hibernate.
     */
    protected Component() {
    }

    public Component(String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }

    public List<ApplicationComponent> getApplicationComponents() {
        return applicationComponents;
    }

    public void setApplicationComponents(List<ApplicationComponent> applicationComponents) {
        this.applicationComponents = applicationComponents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", getId()).add("name", name).toString();
    }
}
