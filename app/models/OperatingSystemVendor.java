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

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import models.api.CredentialStore;
import models.generic.Model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 04.11.14.
 */
@Entity public class OperatingSystemVendor extends Model implements CredentialStore {

    @OneToMany(mappedBy = "operatingSystemVendor") private List<OperatingSystem> operatingSystems;

    @Column(unique = true, nullable = false) private String name;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private OperatingSystemVendorType
        operatingSystemVendorType;
    @Nullable private String defaultLoginName;
    @Nullable private String defaultPassword;

    /**
     * Empty constructor for hibernate.
     */
    protected OperatingSystemVendor() {
    }

    public OperatingSystemVendor(String name, OperatingSystemVendorType operatingSystemVendorType,
        @Nullable String defaultLoginName, @Nullable String defaultLoginPassword) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
        checkNotNull(operatingSystemVendorType);
        this.operatingSystemVendorType = operatingSystemVendorType;
        if (defaultLoginName != null) {
            checkArgument(!defaultLoginName.isEmpty());
        }
        this.defaultLoginName = defaultLoginName;
        if (defaultLoginPassword != null) {
            checkArgument(!defaultLoginPassword.isEmpty());
        }
        this.defaultPassword = defaultLoginPassword;
    }

    public OperatingSystemVendorType operatingSystemVendorType() {
        return operatingSystemVendorType;
    }

    public List<OperatingSystem> operatingSystemsUsedFor() {
        return ImmutableList.copyOf(operatingSystems);
    }

    public String getName() {
        return name;
    }

    @Override public Optional<? extends CredentialStore> getParent() {
        return Optional.empty();
    }

    @Override public Optional<String> getLoginNameCandidate() {
        return Optional.ofNullable(defaultLoginName);
    }

    @Override public Optional<String> getLoginPasswordCandidate() {
        return Optional.ofNullable(defaultPassword);
    }
}
