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

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplateDto extends ValidatableDto {

    protected long cloud;
    protected long cloudImage;
    protected long cloudLocation;
    protected long cloudHardware;

    public VirtualMachineTemplateDto() {
        super();
    }

    @Override public void validation() {

    }

    public VirtualMachineTemplateDto(long cloud, long cloudImage, long cloudLocation,
        long cloudHardware) {
        this.cloud = cloud;
        this.cloudImage = cloudImage;
        this.cloudLocation = cloudLocation;
        this.cloudHardware = cloudHardware;
    }

    public long getCloud() {
        return cloud;
    }

    public void setCloud(long cloud) {
        this.cloud = cloud;
    }

    public long getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(long cloudImage) {
        this.cloudImage = cloudImage;
    }

    public long getCloudLocation() {
        return cloudLocation;
    }

    public void setCloudLocation(long cloudLocation) {
        this.cloudLocation = cloudLocation;
    }

    public long getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(long cloudHardware) {
        this.cloudHardware = cloudHardware;
    }
}
