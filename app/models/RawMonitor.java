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

import java.util.Optional;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import components.scalability.internal.RawMonitorTsdbLocator;
import components.scalability.internal.TsdbLocator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity public class RawMonitor extends MetricMonitor {

    /* filter */
    @ManyToOne(optional = true) private Application application;
    @ManyToOne(optional = true) private Component component;
    @ManyToOne(optional = true) private Instance componentInstance;
    @ManyToOne(optional = true) private Cloud cloud;
    @ManyToOne(optional = true) private SensorDescription sensorDescription;

    @Nullable
    @ManyToOne(optional = true) private SensorConfigurations sensorConfigurations;

    /**
     * Empty constructor for hibernate.
     */
    protected RawMonitor() {
    }

    public RawMonitor(Schedule schedule, Application application, Component component,
        Instance componentInstance, Cloud cloud, SensorDescription sensorDescription,
        SensorConfigurations sensorConfigurations) {
        super(schedule);
        this.application = application;
        this.component = component;
        this.componentInstance = componentInstance;
        this.cloud = cloud;
        checkNotNull(sensorDescription);
        this.sensorDescription = sensorDescription;
        this.sensorConfigurations = sensorConfigurations;
    }

    public Application getApplication() {
        return application;
    }

    public Component getComponent() {
        return component;
    }

    public Instance getComponentInstance() {
        return componentInstance;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public SensorDescription getSensorDescription() {
        return sensorDescription;
    }

    @Override protected TsdbLocator getTsdbLocator() {
        return new RawMonitorTsdbLocator(this);
    }

    @Nullable
    public Optional<SensorConfigurations> getSensorConfigurations() {
        return Optional.ofNullable(sensorConfigurations);
    }
}
