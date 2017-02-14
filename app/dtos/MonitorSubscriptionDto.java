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

import com.google.inject.Inject;
import com.google.inject.Provider;

import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import de.uniulm.omi.cloudiator.persistance.entities.Monitor;
import de.uniulm.omi.cloudiator.persistance.entities.FilterType;
import de.uniulm.omi.cloudiator.persistance.entities.SubscriptionType;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by Frank on 02.08.2015.
 */
public class MonitorSubscriptionDto extends ValidatableDto {

    private Long monitor;
    private String endpoint;
    private SubscriptionType type;
    private FilterType filterType;
    private Double filterValue;

    public MonitorSubscriptionDto() {
        super();
    }

    public MonitorSubscriptionDto(Long monitor, String endpoint, SubscriptionType type,
        FilterType filterType, Double filterValue) {
        this.monitor = monitor;
        this.endpoint = endpoint;
        this.type = type;
        this.filterType = filterType;
        this.filterValue = filterValue;
    }

    @Override public void validation() {
        validator(Long.class).validate(monitor)
            .withValidator(new ModelIdValidator<>(References.monitorService.get()));
    }

    public static class References {
        @Inject public static Provider<ModelService<Monitor>> monitorService;
    }

    public Long getMonitor() {
        return monitor;
    }

    public void setMonitor(Long monitor) {
        this.monitor = monitor;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public Double getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(Double filterValue) {
        this.filterValue = filterValue;
    }
}
