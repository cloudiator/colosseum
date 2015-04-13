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

@Entity public class LocationOffer extends Model {

    @OneToMany(mappedBy = "locationOffer") private List<Location> locations;

    @ManyToOne @Nullable private LocationOffer parent;

    @OneToMany(mappedBy = "parent") private List<LocationOffer> children;

    @Column(updatable = false) @Enumerated(EnumType.STRING) private LocationScope locationScope;

    @Column(updatable = false) private boolean isAssignable;

    @Nullable @ManyToOne private GeoLocation geoLocation;

    /**
     * Empty constructor for hibernate.
     */
    private LocationOffer() {
    }

    public LocationOffer(@Nullable LocationOffer parent, LocationScope locationScope,
        boolean isAssignable, @Nullable GeoLocation geoLocation) {
        this.parent = parent;
        this.locationScope = locationScope;
        this.isAssignable = isAssignable;
        this.geoLocation = geoLocation;
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Nullable public LocationOffer getParent() {
        return parent;
    }

    public List<LocationOffer> getChildren() {
        return children;
    }

    public LocationScope getLocationScope() {
        return locationScope;
    }

    public boolean isAssignable() {
        return isAssignable;
    }

    @Nullable public GeoLocation getGeoLocation() {
        return geoLocation;
    }
}
