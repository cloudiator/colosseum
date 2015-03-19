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

import models.generic.NamedModel;

import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity public class Api extends NamedModel {

    private String internalProviderName;

    /**
     * Empty constructor for hibernate.
     */
    private Api() {

    }

    public Api(String name, String internalProviderName) {
        super(name);
        checkNotNull(internalProviderName);
        checkArgument(!internalProviderName.isEmpty());
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
}
