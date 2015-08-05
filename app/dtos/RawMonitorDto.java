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
import models.*;
import models.service.ModelService;

import java.util.List;

public class RawMonitorDto extends ModelWithExternalReferenceDto {

    private Long application;
    private Long component;
    private Long componentInstance;
    private Long cloud;
    private Long sensorDescription;
    private Long schedule;

    public RawMonitorDto() {
        super();
    }

    public RawMonitorDto(List<String> externalReferences, Long application, Long component, Long componentInstance, Long cloud,
        Long sensorDescription, Long schedule) {
        super(externalReferences);
        this.application = application;
        this.component = component;
        this.componentInstance = componentInstance;
        this.cloud = cloud;
        this.sensorDescription = sensorDescription;
        this.schedule = schedule;
    }

    @Override public void validation() {
        validator(Long.class).validate(sensorDescription)
            .withValidator(new ModelIdValidator<>(References.sensorDescriptionService.get()));
        validator(Long.class).validate(schedule)
            .withValidator(new ModelIdValidator<>(References.scheduleService.get()));
    }

    public static class References extends ModelWithExternalReferenceDto.References {
        @Inject public static Provider<ModelService<Application>> applicationAddressService;
        @Inject public static Provider<ModelService<Component>> componentService;
        @Inject public static Provider<ModelService<Instance>> instanceService;
        @Inject public static Provider<ModelService<Cloud>> cloudService;
        @Inject public static Provider<ModelService<SensorDescription>> sensorDescriptionService;
        @Inject public static Provider<ModelService<Schedule>> scheduleService;
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
}
