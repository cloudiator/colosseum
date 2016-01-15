/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package cloud;

import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.07.15.
 */
public class DecoratingPublicIpService implements PublicIpService {

    private final PublicIpService publicIpService;

    public DecoratingPublicIpService(PublicIpService publicIpService) {
        this.publicIpService = publicIpService;
    }

    @Override public String addPublicIp(String virtualMachineId) throws PublicIpException {
        checkNotNull(virtualMachineId);
        return this.publicIpService.addPublicIp(SlashEncodedId.of(virtualMachineId).swordId());
    }

    @Override public void removePublicIp(String virtualMachineId, String ip)
        throws PublicIpException {
        //todo: implement
        throw new UnsupportedOperationException();
    }
}
