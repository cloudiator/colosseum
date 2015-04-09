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

public class HardwarePropertiesDto extends ValidatableDto {

    protected Integer numberOfCpu;
    protected Long mbOfRam;
    protected Long localDiskSpace;

    public HardwarePropertiesDto() {
        super();
    }

    @Override public void validation() {

    }

    public HardwarePropertiesDto(Integer numberOfCpu, Long mbOfRam, Long localDiskSpace) {
        this.numberOfCpu = numberOfCpu;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }

    public Integer getNumberOfCpu() {
        return numberOfCpu;
    }

    public void setNumberOfCpu(Integer numberOfCpu) {
        this.numberOfCpu = numberOfCpu;
    }

    public Long getMbOfRam() {
        return mbOfRam;
    }

    public void setMbOfRam(Long mbOfRam) {
        this.mbOfRam = mbOfRam;
    }

    public Long getLocalDiskSpace() {
        return localDiskSpace;
    }

    public void setLocalDiskSpace(Long localDiskSpace) {
        this.localDiskSpace = localDiskSpace;
    }

//    @Override public List<ValidationError> validateNotNull() {
//        List<ValidationError> errors = new ArrayList<>();
//
//        if (this.numberOfCpu <= 0) {
//            errors.add(new ValidationError("hardware", "NumberOfCpu must be greater 0"));
//        }
//
//        if (this.mbOfRam <= 0) {
//            errors.add(new ValidationError("hardware", "MbOfRam must be greater 0"));
//        }
//
//        if (this.mbOfRam <= 0) {
//            errors.add(new ValidationError("hardware", "LocalDiskSpace must be greater 0"));
//        }
//
//        return errors;
//
//    }
}
