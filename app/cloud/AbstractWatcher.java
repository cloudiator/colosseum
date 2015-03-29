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

import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.generic.Model;
import models.service.impl.generic.BaseModelService;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by daniel on 11.03.15.
 */
public abstract class AbstractWatcher<T extends Model> implements Watcher<T>, Runnable {

    private final BaseModelService<T> modelService;
    private final ComputeService computeService;
    private final Collection<Problem<T>> detectedProblems;


    @Inject
    public AbstractWatcher(BaseModelService<T> modelService, ComputeService computeService) {
        this.modelService = modelService;
        this.computeService = computeService;
        detectedProblems = new LinkedList<>();
    }

    protected abstract Collection<Problem<T>> check(ComputeService computeService,
        BaseModelService<T> modelService);

    @Override public void run() {
        detectedProblems.addAll(check(this.computeService, this.modelService));
    }

    @Override public Collection<Problem<T>> getProblems() {
        return this.detectedProblems;
    }
}
