/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.common.concurrent;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Can completely solve the OOM problem caused by {@link java.util.concurrent.LinkedBlockingQueue},
 * does not depend on {@link java.lang.instrument.Instrumentation} and is easier to use than
 * {@link org.apache.shenyu.common.concurrent.MemoryLimitedLinkedBlockingQueue}.
 */
public class MemorySafeLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    private static final long serialVersionUID = 8032578371749960142L;

    private int maxFreeMemory;

    private Rejector<E> rejector;

    public MemorySafeLinkedBlockingQueue(final int maxFreeMemory) {
        super(Integer.MAX_VALUE);
        this.maxFreeMemory = maxFreeMemory;
        //default as DiscardPolicy to ensure compatibility with the old version
        this.rejector = new DiscardPolicy<>();
    }

    public MemorySafeLinkedBlockingQueue(final Collection<? extends E> c,
                                         final int maxFreeMemory) {
        super(c);
        this.maxFreeMemory = maxFreeMemory;
        //default as DiscardPolicy to ensure compatibility with the old version
        this.rejector = new DiscardPolicy<>();
    }

    /**
     * set the max free memory.
     *
     * @param maxFreeMemory the max free memory
     */
    public void setMaxFreeMemory(final int maxFreeMemory) {
        this.maxFreeMemory = maxFreeMemory;
    }

    /**
     * get the max free memory.
     *
     * @return the max free memory limit
     */
    public int getMaxFreeMemory() {
        return maxFreeMemory;
    }

    /**
     * set the rejector.
     *
     * @param rejector the rejector
     */
    public void setRejector(final Rejector<E> rejector) {
        this.rejector = rejector;
    }

    /**
     * determine if there is any remaining free memory.
     *
     * @return true if has free memory
     */
    public boolean hasRemainedMemory() {
        return MemoryLimitCalculator.maxAvailable() > maxFreeMemory;
    }

    @Override
    public void put(final E e) throws InterruptedException {
        if (hasRemainedMemory()) {
            super.put(e);
        }
        rejector.reject(e, this);
    }

    @Override
    public boolean offer(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        if (!hasRemainedMemory()) {
            rejector.reject(e, this);
            return false;
        }
        return super.offer(e, timeout, unit);
    }

    @Override
    public boolean offer(final E e) {
        if (!hasRemainedMemory()) {
            rejector.reject(e, this);
            return false;
        }
        return super.offer(e);
    }
}
