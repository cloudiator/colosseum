/*
 * Copyright (c) 2014-2016 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package api.dto;

import api.validation.validators.NotNullOrEmptyValidator;

public class SensorDescriptionDto extends ValidatableDto {

    private String className;
    private String metricName;
    private Boolean isVmSensor;

    public SensorDescriptionDto() {
        super();
    }

    public SensorDescriptionDto(String className, String metricName, Boolean isVmSensor) {
        this.className = className;
        this.metricName = metricName;
        this.isVmSensor = isVmSensor;
    }

    @Override public void validation() {
        validator(String.class).validate(this.className)
            .withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(this.metricName)
            .withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(this.className)
            .withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(this.metricName)
            .withValidator(new NotNullOrEmptyValidator());
    }

    public static class References {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Boolean getIsVmSensor() {
        return isVmSensor;
    }

    public void setIsVmSensor(Boolean isVmSensor) {
        this.isVmSensor = isVmSensor;
    }
}
