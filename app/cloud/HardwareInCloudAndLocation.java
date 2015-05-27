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



import cloud.resources.AbstractCredentialScopedResource;
import cloud.sword.SwordHardware;
import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;

/**
 * Created by daniel on 12.03.15.
 */
public class HardwareInCloudAndLocation extends AbstractCredentialScopedResource<SwordHardware>
    implements HardwareFlavor {

    private final SwordHardware swordHardware;

    public HardwareInCloudAndLocation(SwordHardware resourceInLocation, String cloud,
        String credential) {
        super(resourceInLocation, cloud, credential);
        this.swordHardware = resourceInLocation;
    }

    @Override public int numberOfCores() {
        return swordHardware.numberOfCores();
    }

    @Override public long mbRam() {
        return swordHardware.mbRam();
    }
}
