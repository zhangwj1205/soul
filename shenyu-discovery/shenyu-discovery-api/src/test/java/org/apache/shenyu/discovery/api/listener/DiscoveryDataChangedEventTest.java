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

package org.apache.shenyu.discovery.api.listener;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * The Test Case For {@link DiscoveryDataChangedEvent}.
 */
public class DiscoveryDataChangedEventTest {

    private final DiscoveryDataChangedEvent.Event event = DiscoveryDataChangedEvent.Event.IGNORED;

    @Test
    public void testDiscoveryDataChangedEvent() {
        String key = "key";
        String value = "value";
        DiscoveryDataChangedEvent discoveryDataChangedEvent = new DiscoveryDataChangedEvent(key, value, event);
        Assertions.assertEquals(key, discoveryDataChangedEvent.getKey());
        Assertions.assertEquals(value, discoveryDataChangedEvent.getValue());
        Assertions.assertEquals(event, discoveryDataChangedEvent.getEvent());
    }

}
