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

import de.uniulm.omi.executionware.api.domain.HardwareFlavor;

/**
 * Created by daniel on 12.03.15.
 */
public class HardwareInCloudAndLocation extends AbstractResourceInCloudAndLocation<HardwareFlavor> implements HardwareFlavor {


    public HardwareInCloudAndLocation(HardwareFlavor resource, String cloud) {
        super(resource, cloud);
    }

    private HardwareFlavor getHardware() {
        return (HardwareFlavor) this.resource;
    }

    @Override
    public int numberOfCores() {
        return getHardware().numberOfCores();
    }

    @Override
    public int mbRam() {
        return getHardware().mbRam();
    }
}
