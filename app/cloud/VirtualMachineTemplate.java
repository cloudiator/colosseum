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

package cloud;

import models.CloudHardware;
import models.CloudImage;
import models.CloudLocation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplate {

    private final CloudImage cloudImage;
    private final CloudHardware cloudHardware;
    private final CloudLocation cloudLocation;

    VirtualMachineTemplate(CloudImage cloudImage, CloudHardware cloudHardware, CloudLocation cloudLocation) {

        // everything needs to be not null.
        checkNotNull(cloudImage);
        checkNotNull(cloudHardware);
        checkNotNull(cloudLocation);

        // check that all three are located in the same cloud
        checkArgument(cloudImage.getCloud().equals(cloudHardware.getCloud()));
        checkArgument(cloudHardware.getCloud().equals(cloudLocation.getCloud()));

        //todo: check if hardware and image are available in the location.

        this.cloudImage = cloudImage;
        this.cloudHardware = cloudHardware;
        this.cloudLocation = cloudLocation;
    }

    public static VirtualMachineTemplateBuilder builder() {
        return new VirtualMachineTemplateBuilder();
    }

    public CloudImage getCloudImage() {
        return cloudImage;
    }

    public CloudHardware getCloudHardware() {
        return cloudHardware;
    }

    public CloudLocation getCloudLocation() {
        return cloudLocation;
    }
}
