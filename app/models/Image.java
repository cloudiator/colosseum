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
import de.uniulm.omi.cloudiator.common.os.LoginNameSupplier;
import de.uniulm.omi.cloudiator.lance.lca.container.ContainerType;
import models.generic.RemoteResourceInLocation;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Image extends RemoteResourceInLocation implements LoginNameSupplier {

    /**
     * Own attributes
     */
    @Column(updatable = false, nullable = false) private String name;
    @Nullable private String loginUsernameOverride;
    @Nullable private String loginPasswordOverride;

    /**
     * Owned relations
     */
    @ManyToOne(optional = false) private OperatingSystem operatingSystem;

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
        Cloud cloud, @Nullable Location location, String name, OperatingSystem operatingSystem,
        @Nullable String loginUsernameOverride, @Nullable String loginPasswordOverride) {
        super(remoteId, providerId, swordId, cloud, null, location);

        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;

        this.operatingSystem = operatingSystem;

        this.loginUsernameOverride = loginUsernameOverride;
        this.loginPasswordOverride = loginPasswordOverride;
    }

    public String name() {
        return name;
    }

    public OperatingSystem operatingSystem() {
        return operatingSystem;
    }

    public ContainerType containerType() {
        //todo: temporary switch case until new operating system logic is integrated into lance
        switch (operatingSystem().operatingSystemFamily().operatingSystemType()) {
            case LINUX:
            case UNIX:
                return ContainerType.DOCKER;
            case WINDOWS:
                return ContainerType.PLAIN;
            default:
                throw new IllegalStateException(String
                    .format("Operating System Type %s is currently not supported for deployments.",
                        operatingSystem.operatingSystemFamily().operatingSystemType()));
        }
    }

    public List<VirtualMachineTemplate> virtualMachineTemplatesUsedFor() {
        return ImmutableList.copyOf(virtualMachineTemplates);
    }

    @Override public String loginName() {
        if (loginUsernameOverride != null) {
            return loginUsernameOverride;
        }
        return operatingSystem.operatingSystemFamily().loginName();
    }

    @Nullable public Optional<String> getLoginPasswordOverride() {
        return Optional.ofNullable(loginPasswordOverride);
    }
}
