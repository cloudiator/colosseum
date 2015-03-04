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

import dtos.generic.impl.ValidatableDto;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplateDto extends ValidatableDto {

    public long cloud;
    public long cloudImage;
    public long cloudLocation;
    public long cloudHardware;

    public VirtualMachineTemplateDto() {
    }

    public VirtualMachineTemplateDto(long cloud, long cloudImage, long cloudLocation, long cloudHardware) {
        this.cloud = cloud;
        this.cloudImage = cloudImage;
        this.cloudLocation = cloudLocation;
        this.cloudHardware = cloudHardware;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        return super.validateNotNull();
    }
}
