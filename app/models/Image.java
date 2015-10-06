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
import models.generic.RemoteResourceInLocation;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Image extends RemoteResourceInLocation {

    /**
     * Own attributes
     */
    @Column(updatable = false, nullable = false) private String name;
    @Nullable private String defaultLoginUsername;
    @Nullable private String defaultLoginPassword;

    /**
     * Owned relations
     */
    @Nullable @ManyToOne(optional = true) private OperatingSystem operatingSystem;

    /**
     * Foreign relations
     */
    @OneToMany(mappedBy = "image", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * Empty constructor for hibernate.
     */
    protected Image() {
    }

    public Image(@Nullable String remoteId, @Nullable String cloudProviderId, Cloud cloud, Location location,
        String name, @Nullable OperatingSystem operatingSystem,
        @Nullable String defaultLoginUsername, @Nullable String defaultLoginPassword) {
        super(remoteId, cloudProviderId, cloud, location);

        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;

        this.operatingSystem = operatingSystem;

        if (defaultLoginUsername != null) {
            checkArgument(!defaultLoginUsername.isEmpty());
        }
        this.defaultLoginUsername = defaultLoginUsername;

        if (defaultLoginPassword != null) {
            checkArgument(!defaultLoginPassword.isEmpty());
        }
        this.defaultLoginPassword = defaultLoginPassword;
    }

    public Collection<String> getLoginNameCandidates() {
        Collection<String> loginNameCandidates;
        if (operatingSystem != null) {
            loginNameCandidates = operatingSystem.operatingSystemVendor().getLoginNameCandidates();
        } else {
            loginNameCandidates = new Stack<>();
        }
        if (defaultLoginUsername != null) {
            loginNameCandidates.add(defaultLoginUsername);
        }
        return loginNameCandidates;
    }

    public Collection<String> getLoginPasswordCandidates() {
        Collection<String> loginPasswordCandidates;
        if (operatingSystem != null) {
            loginPasswordCandidates =
                operatingSystem.operatingSystemVendor().getLoginPasswordCandidates();
        } else {
            loginPasswordCandidates = new Stack<>();
        }
        if (defaultLoginPassword != null) {
            loginPasswordCandidates.add(defaultLoginPassword);
        }
        return loginPasswordCandidates;
    }


    public String name() {
        return name;
    }

    public Optional<OperatingSystem> operatingSystem() {
        return Optional.ofNullable(operatingSystem);
    }

    public List<VirtualMachineTemplate> virtualMachineTemplatesUsedFor() {
        return ImmutableList.copyOf(virtualMachineTemplates);
    }

    public OperatingSystemVendorType operatingSystemVendorTypeOrDefault() {
        if (operatingSystem().isPresent()) {
            return operatingSystem().get().operatingSystemVendor().operatingSystemVendorType();
        }
        return OperatingSystemVendorType.DEFAULT_VENDOR_TYPE;
    }


}
