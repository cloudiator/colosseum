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
import com.google.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import util.logging.Loggers;

/**
 * Created by daniel on 24.07.15.
 */
public class TransactionalRunnableInterceptor implements MethodInterceptor {

    private static final Logger.ALogger LOGGER = Loggers.of(Loggers.EXECUTION);
    private final Provider<JPAApi> jpaApi;

    @Inject public TransactionalRunnableInterceptor(Provider<JPAApi> jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        if (!methodInvocation.getMethod().getName().equals("run")) {
            return methodInvocation.proceed();
        }

        final boolean readOnly =
            methodInvocation.getMethod().getAnnotation(Transactional.class).readOnly();
        Object ret;
        LOGGER
            .debug("Running " + methodInvocation.getThis().getClass() + " in transaction context.");

        try {
            ret = jpaApi.get().withTransaction("default", readOnly, methodInvocation::proceed);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        LOGGER.debug(
            "Finished " + methodInvocation.getThis().getClass() + " in transaction context.");
        return ret;
    }
}
