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

import java.lang.annotation.*;

/**
 * Annotation for {@link Schedulable}, stating
 * that the schedulable should be executed in a stable context.
 * <p>
 * Stable context means, that the execution service will no longer stop
 * the execution if it encounters an {@link Exception}.
 * <p>
 * Note that levels above Exception ({@link Throwable} or {@link Error}) will still
 * halt the execution of the schedulable.
 * <p>
 * The annotation is inherited as "workaround", since otherwise the annotation is not present
 * in the Guice AOP Proxies {@see https://github.com/google/guice/issues/201}.
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @Inherited public @interface Stable {
}
