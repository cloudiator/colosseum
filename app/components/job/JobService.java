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

package components.job;

import models.*;

/**
 * Created by daniel on 03.07.15.
 */
public interface JobService {

    void newVirtualMachineJob(VirtualMachine virtualMachine, Tenant tenant);

    void newInstanceJob(Instance instance, Tenant tenant);

    void newDeleteVirtualMachineJob(VirtualMachine virtualMachine, Tenant tenant);

    void newDeleteInstanceJob(Instance instance, Tenant tenant);

    // Return value for the moment, until we integrate it in asynchronous queue;
    void newPlatformInstanceJob(PlatformInstance platformInstance, Tenant tenant);
}
