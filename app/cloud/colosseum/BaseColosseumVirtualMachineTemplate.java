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

import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.domain.TemplateOptions;
import de.uniulm.omi.cloudiator.sword.core.util.Name;
import models.*;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.02.15.
 */
public class BaseColosseumVirtualMachineTemplate implements ColosseumVirtualMachineTemplate {

    private final String name;
    private final Image image;
    private final Hardware hardware;
    private final Location location;
    private final Cloud cloud;
    private final CloudCredential cloudCredential;
    @Nullable private final TemplateOptions templateOptions;

    BaseColosseumVirtualMachineTemplate(String name, Cloud cloud, CloudCredential cloudCredential,
        Image image, Hardware hardware, Location location,
        @Nullable TemplateOptions templateOptions) {


        // everything needs to be not null.
        checkNotNull(image, "image is required");
        checkNotNull(hardware, "hardware is required");
        checkNotNull(location, "location is required");
        checkNotNull(cloud, "cloud is required");
        checkNotNull(cloudCredential, "credential is required");

        //check that the image, the hardware and the location are in the cloud
        checkArgument(image.cloud().equals(cloud), "image not in cloud");
        checkArgument(location.cloud().equals(cloud), "location not in cloud");
        checkArgument(hardware.cloud().equals(cloud), "hardware not in cloud");

        //check that the credential is correct
        checkArgument(cloudCredential.getCloud().equals(cloud), "cloud credential not in cloud");
        checkArgument(image.cloudCredentials().contains(cloudCredential),
            "image not allowed for user");
        checkArgument(location.cloudCredentials().contains(cloudCredential),
            "location not allowed for user");
        checkArgument(hardware.cloudCredentials().contains(cloudCredential),
            "hardware not allowed for user");

        //check that the location is correct
        if (image.location().isPresent()) {
            checkArgument(location.hierachy().contains(image.location().get()),
                "image not available in location");
        }
        if (hardware.location().isPresent()) {
            checkArgument(location.hierachy().contains(hardware.location().get()),
                "hardware not available in location");
        }

        checkArgument(location.isAssignable(), "location not assignable");

        //check that all sword ids are set
        checkArgument(location.swordId().isPresent(), "location not bound");
        checkArgument(hardware.swordId().isPresent(), "hardware not bound");
        checkArgument(image.swordId().isPresent(), "image not bound");

        this.name = name;
        this.image = image;
        this.hardware = hardware;
        this.location = location;
        this.cloud = cloud;
        this.cloudCredential = cloudCredential;
        this.templateOptions = templateOptions;
    }

    public static ColosseumVirtualMachineTemplateBuilder builder() {
        return new ColosseumVirtualMachineTemplateBuilder();
    }

    @Override public String name() {
        return Name.of(name).uniqueName();
    }

    @Override public String imageId() {
        return image.swordId().get();
    }

    @Override public String hardwareFlavorId() {
        return hardware.swordId().get();
    }

    @Override public String locationId() {
        return location.swordId().get();
    }

    @Override public Optional<TemplateOptions> templateOptions() {
        return Optional.fromNullable(templateOptions);
    }

    @Override public String cloudUuid() {
        return cloud.getUuid();
    }

    @Override public String cloudCredentialUuid() {
        return cloudCredential.getUuid();
    }
}
