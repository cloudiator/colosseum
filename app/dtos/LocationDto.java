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

package dtos;

import dtos.generic.impl.NamedDto;

import java.util.List;

public class LocationDto extends NamedDto {

    protected List<Long> locationCodes;

    protected Long parent;

    protected String locationScope;

    public LocationDto() {
        super();
    }

    public LocationDto(String name, List<Long> locationCodes, Long parent, String locationScope) {
        super(name);
        this.locationCodes = locationCodes;
        this.parent = parent;
        this.locationScope = locationScope;
    }

    public List<Long> getLocationCodes() {
        return locationCodes;
    }

    public void setLocationCodes(List<Long> locationCodes) {
        this.locationCodes = locationCodes;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getLocationScope() {
        return locationScope;
    }

    public void setLocationScope(String locationScope) {
        this.locationScope = locationScope;
    }
}
