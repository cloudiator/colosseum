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
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity public class Api extends Model {

    @Column(nullable = false) private String internalProviderName;
    @Column(unique = true, nullable = false) private String name;
    @OneToMany(mappedBy = "api") private List<Cloud> clouds;

    /**
     * Empty constructor for hibernate.
     */
    protected Api() {
    }

    public Api(String name, String internalProviderName) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        checkNotNull(internalProviderName);
        checkArgument(!internalProviderName.isEmpty());
        this.name = name;
        this.internalProviderName = internalProviderName;
    }

    public String getInternalProviderName() {
        return internalProviderName;
    }

    public void setInternalProviderName(String internalProviderName) {
        checkNotNull(internalProviderName);
        checkArgument(!internalProviderName.isEmpty());
        this.internalProviderName = internalProviderName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }
}
