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
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.OperatingSystemVendorType;
import models.api.CredentialStore;
import models.generic.RemoteResourceInLocation;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Image extends RemoteResourceInLocation implements CredentialStore {

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

    public Image(@Nullable String remoteId, @Nullable String providerId, @Nullable String swordId,
        Cloud cloud, @Nullable Location location, String name,
        @Nullable OperatingSystem operatingSystem, @Nullable String defaultLoginUsername,
        @Nullable String defaultLoginPassword) {
        super(remoteId, providerId, swordId, cloud, null, location);

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


    @Override public Optional<? extends CredentialStore> getParent() {
        if (operatingSystem().isPresent()) {
            return Optional.of(operatingSystem().get().operatingSystemVendor());
        }
        return Optional.empty();
    }

    @Override public Optional<String> getLoginNameCandidate() {
        return Optional.ofNullable(defaultLoginUsername);
    }

    @Override public Optional<String> getLoginPasswordCandidate() {
        return Optional.ofNullable(defaultLoginPassword);
    }
}
