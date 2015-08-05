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

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 04.11.14.
 */
@Entity public class OperatingSystemVendor extends Model {

    @OneToMany(mappedBy = "operatingSystemVendor") private List<OperatingSystem> operatingSystems;

    @Column(unique = true, nullable = false) private String name;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private OperatingSystemVendorType
        operatingSystemVendorType;
    @Nullable private String defaultUserName;
    @Nullable private String defaultPassword;

    /**
     * Empty constructor for hibernate.
     */
    protected OperatingSystemVendor() {
    }

    public OperatingSystemVendor(String name, OperatingSystemVendorType operatingSystemVendorType,
        @Nullable String defaultUserName, @Nullable String defaultPassword) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
        checkNotNull(operatingSystemVendorType);
        this.operatingSystemVendorType = operatingSystemVendorType;
        if (defaultUserName != null) {
            checkArgument(!defaultUserName.isEmpty());
        }
        this.defaultUserName = defaultUserName;
    }

    public OperatingSystemVendorType getOperatingSystemVendorType() {
        return operatingSystemVendorType;
    }

    public void setOperatingSystemVendorType(OperatingSystemVendorType operatingSystemVendorType) {
        this.operatingSystemVendorType = operatingSystemVendorType;
    }

    public List<OperatingSystem> getOperatingSystems() {
        return operatingSystems;
    }

    public void setOperatingSystems(List<OperatingSystem> operatingSystems) {
        this.operatingSystems = operatingSystems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable public String getDefaultUserName() {
        return defaultUserName;
    }

    @Nullable public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(@Nullable String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
}
