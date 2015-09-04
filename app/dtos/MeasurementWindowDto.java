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

import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 10.08.2015.
 */
public class MeasurementWindowDto extends ValidatableDto {

    private Long measurements;

    public MeasurementWindowDto() {
        super();
    }

    public MeasurementWindowDto(Long measurements) {
        this.measurements = measurements;
    }

    @Override public void validation() {
        //TODO
    }

    public static class References {
    }

    public Long getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Long measurements) {
        this.measurements = measurements;
    }
}