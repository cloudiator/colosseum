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
import com.google.common.collect.ImmutableSet;
import de.uniulm.omi.cloudiator.sword.api.domain.LocationScope;
import models.generic.RemoteResourceInCloud;
import models.generic.RemoteResourceInLocation;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity public class Location extends RemoteResourceInCloud {

    @Nullable @ManyToOne(optional = true) private GeoLocation geoLocation;

    @Column(updatable = false, nullable = false) private String name;

    @ManyToOne @Nullable private Location parent;

    @OneToMany(mappedBy = "parent") private List<Location> children;

    @Nullable @Column(updatable = false) @Enumerated(EnumType.STRING) private LocationScope
        locationScope;

    @Column(nullable = false, updatable = false) private Boolean isAssignable;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<RemoteResourceInLocation> remoteResources;

    /**
     * Empty constructor for hibernate.
     */
    protected Location() {
    }

    public Location(@Nullable String remoteId, @Nullable String cloudProviderId, Cloud cloud,
        String name, @Nullable GeoLocation geoLocation, @Nullable Location parent,
        @Nullable LocationScope locationScope, Boolean isAssignable) {
        super(remoteId, cloudProviderId, cloud, null);
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
        this.geoLocation = geoLocation;
        this.parent = parent;
        this.locationScope = locationScope;
        this.isAssignable = isAssignable;
    }

    public Optional<GeoLocation> geoLocation() {
        return Optional.ofNullable(geoLocation);
    }

    public Optional<Location> getParent() {
        return Optional.ofNullable(parent);
    }

    public Set<Location> hierachy() {
        final ImmutableSet.Builder<Location> builder = ImmutableSet.builder();
        Location location = this;
        do {
            builder.add(location);
            location = location.getParent().orElse(null);
        } while (location != null);
        return builder.build();
    }

    public List<Location> children() {
        return ImmutableList.copyOf(children);
    }

    public Optional<LocationScope> locationScope() {
        return Optional.ofNullable(locationScope);
    }

    public boolean isAssignable() {
        return isAssignable;
    }

    public List<VirtualMachineTemplate> virtualMachineTemplatesUsedFor() {
        return ImmutableList.copyOf(virtualMachineTemplates);
    }

    public List<RemoteResourceInLocation> remoteResourcesUsedFor() {
        return ImmutableList.copyOf(remoteResources);
    }
}
