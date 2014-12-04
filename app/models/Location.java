/*
 * Copyright (c) 2014 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
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

import javax.persistence.*;
import java.util.List;

/**
 * The model class location.
 * <p>
 * Represents a location of the cloud, e.g. a data center, us-west....
 *
 * @author Daniel Baur
 */
@Entity
public class Location extends NamedModel {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The cloud locations related to this location (ManyToMany)
     */
    @OneToMany(mappedBy = "location")
    private List<CloudLocation> cloudLocations;

    @ManyToOne
    private Location parent;

    @OneToMany(mappedBy = "parent")
    private List<Location> children;

    @ManyToMany
    private List<LocationCode> locationCodes;

    @Enumerated(EnumType.STRING)
    private LocationScope locationScope;

    /**
     * Empty constructor. Needed for hibernate.
     */
    public Location() {

    }

    public List<CloudLocation> getCloudLocations() {
        return cloudLocations;
    }

    public void setCloudLocations(List<CloudLocation> cloudLocations) {
        this.cloudLocations = cloudLocations;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public List<Location> getChildren() {
        return children;
    }

    public void setChildren(List<Location> children) {
        this.children = children;
    }

    public List<LocationCode> getLocationCodes() {
        return locationCodes;
    }

    public void setLocationCodes(List<LocationCode> locationCodes) {
        this.locationCodes = locationCodes;
    }

    public LocationScope getLocationScope() {
        return locationScope;
    }

    public void setLocationScope(LocationScope locationScope) {
        this.locationScope = locationScope;
    }
}
