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

package cloud.colosseum;

import cloud.colosseum.ColosseumVirtualMachineTemplate;
import cloud.colosseum.ColosseumVirtualMachineTemplateBuilder;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;
import models.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.02.15.
 */
public class BaseColosseumVirtualMachineTemplate implements ColosseumVirtualMachineTemplate {

    private final Image image;
    private final Hardware hardware;
    private final Location location;
    private final Cloud cloud;
    private final CloudCredential cloudCredential;

    BaseColosseumVirtualMachineTemplate(Cloud cloud, CloudCredential cloudCredential, Image image,
        Hardware hardware, Location location) {

        // everything needs to be not null.
        checkNotNull(image);
        checkNotNull(hardware);
        checkNotNull(location);
        checkNotNull(cloud);
        checkNotNull(cloudCredential);

        //check that the image, the hardware and the location are in the cloud
        checkArgument(image.getCloud().equals(cloud));
        checkArgument(location.getCloud().equals(cloud));
        checkArgument(hardware.getCloud().equals(cloud));

        //check that the credential is correct
        checkArgument(cloudCredential.getCloud().equals(cloud));
        checkArgument(image.getCloudCredentials().contains(cloudCredential));
        checkArgument(location.getCloudCredentials().contains(cloudCredential));
        checkArgument(hardware.getCloudCredentials().contains(cloudCredential));

        //check that the location is correct
        checkArgument(image.getLocations().contains(location));
        checkArgument(hardware.getLocations().contains(location));
        checkArgument(location.isAssignable());

        this.image = image;
        this.hardware = hardware;
        this.location = location;
        this.cloud = cloud;
        this.cloudCredential = cloudCredential;
    }

    public static ColosseumVirtualMachineTemplateBuilder builder() {
        return new ColosseumVirtualMachineTemplateBuilder();
    }

    @Override public String getImageId() {
        return IdScopeByLocations.from(location.getCloudUuid(), image.getCloudUuid())
            .getIdWithLocation();
    }

    @Override public String getHardwareFlavorId() {
        return IdScopeByLocations.from(location.getCloudUuid(), hardware.getCloudUuid())
            .getIdWithLocation();
    }

    @Override public String getLocationId() {
        return location.getCloudUuid();
    }

    @Override public String getCloudUuid() {
        return cloud.getUuid();
    }

    @Override public String getCloudCredentialUuid() {
        return cloudCredential.getUuid();
    }
}
