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

import dtos.generic.ValidatableDto;

/**
 * Created by daniel on 09.04.15.
 */
public class GeoLocationDto extends ValidatableDto {

    private String region;
    private String city;
    private String country;
    private String iso3166;
    private Float locationLatitude;
    private Float LocationLongitude;

    @Override public void validation() {
        //todo implement validation
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIso3166() {
        return iso3166;
    }

    public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
    }

    public Float getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Float locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Float getLocationLongitude() {
        return LocationLongitude;
    }

    public void setLocationLongitude(Float locationLongitude) {
        LocationLongitude = locationLongitude;
    }
}
