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

import models.Hardware;
import models.Image;
import models.Location;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.02.15.
 */
public class ColloseumVirtualMachineTemplate {

    private final Image image;
    private final Hardware hardware;
    private final Location location;

    ColloseumVirtualMachineTemplate(Image image, Hardware hardware, Location location) {

        // everything needs to be not null.
        checkNotNull(image);
        checkNotNull(hardware);
        checkNotNull(location);

        // check that all three are located in the same cloud
        checkArgument(image.getCloud().equals(hardware.getCloud()));
        checkArgument(hardware.getCloud().equals(location.getCloud()));

        //todo: check if hardware and image are available in the location.
        this.image = image;
        this.hardware = hardware;
        this.location = location;
    }

    public static ColloseumVirtualMachineTemplateBuilder builder() {
        return new ColloseumVirtualMachineTemplateBuilder();
    }

    public String cloudHardware() {
        return hardware.getUuid();
    }

    public String cloudImage() {
        return image.getUuid();
    }

    public String cloudLocation() {
        return location.getUuid();
    }
}
