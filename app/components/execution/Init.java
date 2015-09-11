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

package components.execution;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 24.07.15.
 */
@Singleton public class Init {

    @Inject public Init(ExecutionService executionService, Set<Runnable> runnables,
        Set<Schedulable> schedulables) {

        checkNotNull(executionService);
        checkNotNull(runnables);
        checkNotNull(schedulables);

//        for (Runnable runnable : runnables) {
//            executionService.execute(runnable);
//        }
//        for (Schedulable schedulable : schedulables) {
//            executionService.schedule(schedulable);
//        }
    }
}
