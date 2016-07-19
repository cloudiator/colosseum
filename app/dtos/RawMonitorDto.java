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

import dtos.generic.MonitorDto;
import dtos.validation.validators.ModelIdValidator;
import models.Application;
import models.Cloud;
import models.Component;
import models.Instance;
import models.Schedule;
import models.SensorConfigurations;
import models.SensorDescription;
import models.service.ModelService;

public class RawMonitorDto extends MonitorDto {

    private Long application;
    private Long component;
    private Long componentInstance;
    private Long cloud;
    private Long sensorDescription;
    private Long schedule;
    private Long sensorConfigurations;


    public RawMonitorDto() {
        super();
    }

    @Override public void validation() {
        super.validation();

        validator(Long.class).validate(sensorDescription)
            .withValidator(new ModelIdValidator<>(References.sensorDescriptionService.get()));
        validator(Long.class).validate(schedule)
            .withValidator(new ModelIdValidator<>(References.scheduleService.get()));
        validator(Long.class).validate(sensorConfigurations, "sensorConfigurations")
                .withValidator(new ModelIdValidator<>(References.sensorConfigurationsService.get()));
    }

    public static class References {
        @Inject public static Provider<ModelService<Application>> applicationAddressService;
        @Inject public static Provider<ModelService<Component>> componentService;
        @Inject public static Provider<ModelService<Instance>> instanceService;
        @Inject public static Provider<ModelService<Cloud>> cloudService;
        @Inject public static Provider<ModelService<SensorDescription>> sensorDescriptionService;
        @Inject public static Provider<ModelService<Schedule>> scheduleService;
        @Inject public static Provider<ModelService<SensorConfigurations>> sensorConfigurationsService;
    }

    public Long getApplication() {
        return application;
    }

    public void setApplication(Long application) {
        this.application = application;
    }

    public Long getComponent() {
        return component;
    }

    public void setComponent(Long component) {
        this.component = component;
    }

    public Long getComponentInstance() {
        return componentInstance;
    }

    public void setComponentInstance(Long componentInstance) {
        this.componentInstance = componentInstance;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getSensorDescription() {
        return sensorDescription;
    }

    public void setSensorDescription(Long sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public Long getSchedule() {
        return schedule;
    }

    public void setSchedule(Long schedule) {
        this.schedule = schedule;
    }

    public Long getSensorConfigurations() {
        return sensorConfigurations;
    }

    public void setSensorConfigurations(Long sensorConfigurations) {
        this.sensorConfigurations = sensorConfigurations;
    }
}
