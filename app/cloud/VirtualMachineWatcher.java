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


import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.VirtualMachine;
import models.service.impl.generic.BaseModelService;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by daniel on 11.03.15.
 */
public class VirtualMachineWatcher extends AbstractWatcher<VirtualMachine> {

    public VirtualMachineWatcher(BaseModelService<VirtualMachine> modelService,
        ComputeService computeService) {
        super(modelService, computeService);
    }

    @Override protected Collection<Problem<VirtualMachine>> check(ComputeService computeService,
        BaseModelService<VirtualMachine> modelService) {
        LinkedList<Problem<VirtualMachine>> problems = new LinkedList<>();

        for (VirtualMachine local : modelService.getAll()) {

        }
        return null;


    }
}
