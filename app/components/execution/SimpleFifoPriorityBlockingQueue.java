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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * A simple {@link BlockingQueue} implementation returning entries based
 * on their priority given by {@link Prioritized}.
 * <p>
 * If two objects have the same priority (are equal with respect to their
 * compareTo ({@link Comparable}) method) this queue ensures that FIFO
 * will be applied as tie-breaker.
 *
 * @param <T>
 */
public class SimpleFifoPriorityBlockingQueue<T extends Comparable<? super T>>
    implements SimpleBlockingQueue<T> {

    private BlockingQueue<FiFoEntry<T>> queue;

    public SimpleFifoPriorityBlockingQueue() {
        this.queue = new PriorityBlockingQueue<>();
    }

    private FiFoEntry<T> decorateFiFo(T prioritized) {
        return FiFoEntry.of(prioritized);
    }

    @Override public void add(T t) {
        this.queue.add(decorateFiFo(t));
    }

    @Override public T take() throws InterruptedException {
        return this.queue.take().getEntry();
    }
}
