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
import models.*;

import javax.annotation.Nullable;

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
    private final Optional<TemplateOptions> templateOptions;

    BaseColosseumVirtualMachineTemplate(Cloud cloud, CloudCredential cloudCredential, Image image,
        Hardware hardware, Location location, @Nullable TemplateOptions templateOptions) {


        // everything needs to be not null.
        checkNotNull(image);
        checkNotNull(hardware);
        checkNotNull(location);
        checkNotNull(cloud);
        checkNotNull(cloudCredential);

        //check that the image, the hardware and the location are in the cloud
        checkArgument(image.cloud().equals(cloud));
        checkArgument(location.cloud().equals(cloud));
        checkArgument(hardware.cloud().equals(cloud));

        //check that the credential is correct
        checkArgument(cloudCredential.getCloud().equals(cloud));
        checkArgument(image.cloudCredentials().contains(cloudCredential));
        checkArgument(location.cloudCredentials().contains(cloudCredential));
        checkArgument(hardware.cloudCredentials().contains(cloudCredential));

        //check that the location is correct
        if (image.location().isPresent()) {
            checkArgument(image.location().get().equals(location));
        }
        if (hardware.location().isPresent()) {
            checkArgument(hardware.location().get().equals(location));
        }

        checkArgument(location.isAssignable());

        //check that all cloudprovider ids are set
        checkArgument(location.cloudProviderId().isPresent());
        checkArgument(hardware.cloudProviderId().isPresent());
        checkArgument(image.cloudProviderId().isPresent());

        this.image = image;
        this.hardware = hardware;
        this.location = location;
        this.cloud = cloud;
        this.cloudCredential = cloudCredential;
        this.templateOptions = Optional.fromNullable(templateOptions);
    }

    public static ColosseumVirtualMachineTemplateBuilder builder() {
        return new ColosseumVirtualMachineTemplateBuilder();
    }

    @Override public String imageId() {
        return image.cloudProviderId().get();
    }

    @Override public String hardwareFlavorId() {
        return hardware.cloudProviderId().get();
    }

    @Override public String locationId() {
        return location.cloudProviderId().get();
    }

    @Override public Optional<TemplateOptions> templateOptions() {
        return templateOptions;
    }

    @Override public String cloudUuid() {
        return cloud.getUuid();
    }

    @Override public String cloudCredentialUuid() {
        return cloudCredential.getUuid();
    }
}
