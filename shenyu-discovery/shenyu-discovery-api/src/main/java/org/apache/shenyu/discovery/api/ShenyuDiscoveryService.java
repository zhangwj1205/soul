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

package org.apache.shenyu.discovery.api;

import org.apache.shenyu.discovery.api.config.DiscoveryConfig;
import org.apache.shenyu.discovery.api.listener.DataChangedEventListener;
import org.apache.shenyu.spi.SPI;

import java.util.List;

/**
 * The interface for shenyu discovery service.
 */
@SPI
public interface ShenyuDiscoveryService {

    /**
     * Init shenyu discovery service .
     *
     * @param config the config
     */
    void init(DiscoveryConfig config);

    /**
     * Watch path , fire data changed event.
     *
     * @param key      the key
     * @param listener the listener
     */
    void watch(String key, DataChangedEventListener listener);

    /**
     * unwatch path.
     *
     * @param key key
     */
    void unwatch(String key);

    /**
     * Register data.
     *
     * @param key   the key
     * @param value the value
     */
    void register(String key, String value);

    /**
     * getData by key.
     *
     * @param key key
     * @return value
     */
    List<String> getRegisterData(String key);

    /**
     * exists.
     *
     * @param key key
     * @return Boolean
     */
    Boolean exists(String key);

    /**
     * shutdown.
     */
    void shutdown();

}
