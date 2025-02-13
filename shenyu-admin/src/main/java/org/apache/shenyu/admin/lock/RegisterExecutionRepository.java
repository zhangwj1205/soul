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

package org.apache.shenyu.admin.lock;

/**
 * The interface Register execution repository.
 * Deprecated: this class is deprecated and will be removed in the next major version.
 * @since 2.6.1
 * @deprecated Please use {@link org.springframework.integration.jdbc.lock.DefaultLockRepository}.
 */
@Deprecated
public interface RegisterExecutionRepository {
    /**
     * Return the lock for the client register global allowing for the lock to be acquired or released. Caution:
     * care should be made not to allow for a deadlock situation. If you acquire a lock make sure you
     * release it when you are done. The general pattern for safely doing work against a locked
     * client register follows:
     * <pre>
     * RegisterExecutionLock lock = repository.getLock(key);
     * lock.lock();
     * try {
     *     // do register  work
     *   } finally {
     *     lock.unlock();
     *   }
     * </pre>
     * @param key the key
     * @return registerExecutionLock
     */
    RegisterExecutionLock getLock(String key);

}
