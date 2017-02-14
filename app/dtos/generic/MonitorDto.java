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

package dtos.generic;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.List;

import dtos.ModelWithExternalReferenceDto;
import de.uniulm.omi.cloudiator.persistance.entities.MonitorInstance;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by Frank on 25.08.2015.
 */
public abstract class MonitorDto extends ModelWithExternalReferenceDto {

    private List<Long> monitorInstances;

    public MonitorDto() {
        super();
    }

    @Override public void validation() {
        super.validation();

        // TODO List?
        //validator(Long.class).validate(monitorInstances)
        //        .withValidator(new ModelIdValidator<>(References.monitorInstanceService.get()));
    }

    public static class References {
        @Inject public static Provider<ModelService<MonitorInstance>> monitorInstanceService;
    }

    public List<Long> getMonitorInstances() {
        return monitorInstances;
    }

    public void setMonitorInstances(List<Long> monitorInstances) {
        this.monitorInstances = monitorInstances;
    }
}
