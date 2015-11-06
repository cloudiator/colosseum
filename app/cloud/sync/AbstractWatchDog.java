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

package cloud.sync;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.Schedulable;
import components.execution.SimpleBlockingQueue;
import play.Logger;
import play.db.jpa.Transactional;
import util.Loggers;

import java.util.Optional;
import java.util.Set;

/**
 * Created by daniel on 04.11.15.
 */
public abstract class AbstractWatchDog<T> implements Schedulable {

    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_SYNC);
    private final SimpleBlockingQueue<Problem> problemQueue;
    private final Set<ProblemDetector<T>> detectors;

    @Inject protected AbstractWatchDog(
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue,
        Set<ProblemDetector<T>> detectors) {
        this.problemQueue = problemQueue;
        this.detectors = detectors;
    }

    @Transactional(readOnly = true) @Override public void run() {

        LOGGER.info(String.format("%s is starting watching.", this));

        Iterable<T> toWatch = toWatch();

        if (Iterables.size(toWatch) > 0) {
            LOGGER
                .debug(String.format("%s is watching %s entities.", this, Iterables.size(toWatch)));
            for (T t : toWatch()) {
                LOGGER.trace(String.format("%s is starting to watch %s", this, t));
                for (ProblemDetector<T> problemDetector : detectors) {
                    LOGGER.trace(String
                        .format("%s is applying problem detector %s on %s", this, problemDetector,
                            t));
                    final Optional<Problem> problem = problemDetector.apply(t);
                    if (problem.isPresent()) {
                        LOGGER.debug(String.format("%s found problem %s, reporting to queue.", this,
                            problem.get()));
                        problemQueue.add(problem.get());
                    }
                }
            }
        } else {
            LOGGER.debug(String.format("%s has nothing to watch", this));
        }

        LOGGER.info(String.format("%s finished watching", this));
    }

    /**
     * @return a iterable of objects to watch.
     */
    protected abstract Iterable<T> toWatch();



}
