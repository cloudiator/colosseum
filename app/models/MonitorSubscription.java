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
import models.scalability.FilterType;
import models.scalability.SubscriptionType;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Frank on 02.08.2015.
 */
@Entity public class MonitorSubscription extends Model {

    @ManyToOne(optional = false) private Monitor monitor;
    @Column(unique = false, nullable = false) private String endpoint;
    @Enumerated(EnumType.STRING) private SubscriptionType type;
    @Enumerated(EnumType.STRING) private FilterType filterType;
    @Column(unique = false, nullable = false) private Double filterValue;

    /**
     * Empty constructor for hibernate.
     */
    protected MonitorSubscription() {
    }

    public MonitorSubscription(Monitor monitor, String endpoint, SubscriptionType type,
        FilterType filterType, Double filterValue) {
        checkNotNull(monitor);
        checkNotNull(endpoint);
        checkNotNull(type);
        checkNotNull(filterType);
        checkNotNull(filterValue);
        this.monitor = monitor;
        this.endpoint = endpoint;
        this.type = type;
        this.filterType = filterType;
        this.filterValue = filterValue;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
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
