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

package org.apache.shenyu.plugin.logging.elasticsearch.collector;

import org.apache.shenyu.plugin.logging.common.client.LogConsumeClient;
import org.apache.shenyu.plugin.logging.common.collector.AbstractLogCollector;
import org.apache.shenyu.plugin.logging.common.entity.ShenyuRequestLog;
import org.apache.shenyu.plugin.logging.elasticsearch.client.ElasticSearchLogCollectClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * The Test Case For DefaultLogCollector.
 */
public final class ElasticSearchLogCollectorTest {

    private final ShenyuRequestLog shenyuRequestLog = new ShenyuRequestLog();

    @BeforeEach
    public void setUp() {
        shenyuRequestLog.setClientIp("0.0.0.0");
        shenyuRequestLog.setPath("org/apache/shenyu/plugin/logging");
    }

    @Test
    public void testAbstractLogCollector() throws Exception {
        ElasticSearchLogCollector.getInstance().start();
        Field field1 = AbstractLogCollector.class.getDeclaredField("started");
        field1.setAccessible(true);
        Assertions.assertEquals(field1.get(ElasticSearchLogCollector.getInstance()).toString(), "true");
        ElasticSearchLogCollector.getInstance().collect(shenyuRequestLog);
        ElasticSearchLogCollector.getInstance().close();
        Field field2 = AbstractLogCollector.class.getDeclaredField("started");
        field2.setAccessible(true);
        Assertions.assertEquals(field2.get(ElasticSearchLogCollector.getInstance()).toString(), "false");
    }

    @Test
    public void testGetLogConsumeClient() {
        LogConsumeClient logConsumeClient = new ElasticSearchLogCollector().getLogConsumeClient();
        Assertions.assertEquals(ElasticSearchLogCollectClient.class, logConsumeClient.getClass());
    }
}
