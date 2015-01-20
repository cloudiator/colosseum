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

package dtos.builders;

import dtos.VirtualMachineDto;

public class VirtualMachineDtoBuilder {
    private String name;
    private Long cloud;
    private Long cloudImage;
    private Long cloudHardware;
    private Long cloudLocation;

    public VirtualMachineDtoBuilder name(String name) {
        this.name = name;
        return this;
    }

    public VirtualMachineDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public VirtualMachineDtoBuilder cloudImage(Long cloudImage) {
        this.cloudImage = cloudImage;
        return this;
    }

    public VirtualMachineDtoBuilder cloudHardware(Long cloudHardware) {
        this.cloudHardware = cloudHardware;
        return this;
    }

    public VirtualMachineDtoBuilder cloudLocation(Long cloudLocation) {
        this.cloudLocation = cloudLocation;
        return this;
    }

    public VirtualMachineDto build() {
        return new VirtualMachineDto(name, cloud, cloudImage, cloudHardware, cloudLocation);
    }
}