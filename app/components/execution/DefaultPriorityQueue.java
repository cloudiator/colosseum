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

import com.google.inject.Singleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by daniel on 04.05.15.
 */
@Singleton public class DefaultPriorityQueue<T extends Prioritized> implements PriorityQueue<T> {

    private BlockingQueue<T> queue;

    public DefaultPriorityQueue() {
        this.queue = new PriorityBlockingQueue<>(10,
            (first, second) -> Integer.compare(first.getPriority(), second.getPriority()));
    }

    @Override public void add(T t) {
        this.queue.add(t);
    }

    @Override public T take() throws InterruptedException {
        return this.queue.take();
    }
}
