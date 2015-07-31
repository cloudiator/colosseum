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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import play.Logger;

/**
 * Created by daniel on 28.07.15.
 */
public class StableRunnableInterceptor implements MethodInterceptor {

    @Override public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        if (!methodInvocation.getMethod().getName().equals("run")) {
            return methodInvocation.proceed();
        }

        Logger
            .debug("Running " + methodInvocation.getThis().getClass() + " in stable mode.");
        try {
            return methodInvocation.proceed();
        } catch (Exception e) {
            Logger.error("StableRunner encountered error. Ignoring....", e);
        }

        return null;
    }
}
