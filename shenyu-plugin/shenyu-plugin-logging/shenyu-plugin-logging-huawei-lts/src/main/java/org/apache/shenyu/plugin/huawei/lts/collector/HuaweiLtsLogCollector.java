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

package org.apache.shenyu.plugin.huawei.lts.collector;

import org.apache.shenyu.plugin.huawei.lts.client.HuaweiLtsLogCollectClient;
import org.apache.shenyu.plugin.huawei.lts.config.HuaweiLogCollectConfig;
import org.apache.shenyu.plugin.huawei.lts.handler.LoggingHuaweiLtsPluginDataHandler;
import org.apache.shenyu.plugin.logging.common.collector.AbstractLogCollector;
import org.apache.shenyu.plugin.logging.common.collector.LogCollector;
import org.apache.shenyu.plugin.logging.common.entity.ShenyuRequestLog;
import org.apache.shenyu.plugin.logging.desensitize.api.matcher.KeyWordMatch;

/**
 * Huawei lts log collector，depend a LogConsumeClient for consume logs.
 */
public class HuaweiLtsLogCollector extends AbstractLogCollector<HuaweiLtsLogCollectClient, ShenyuRequestLog, HuaweiLogCollectConfig.HuaweiLtsLogConfig> {

    private static final LogCollector<ShenyuRequestLog> INSTANCE = new HuaweiLtsLogCollector();

    /**
     * get LogCollector instance.
     *
     * @return LogCollector instance
     */
    public static LogCollector<ShenyuRequestLog> getInstance() {
        return INSTANCE;
    }

    @Override
    protected HuaweiLtsLogCollectClient getLogConsumeClient() {
        return LoggingHuaweiLtsPluginDataHandler.getHuaweiLtsLogCollectClient();
    }

    @Override
    protected HuaweiLogCollectConfig.HuaweiLtsLogConfig getLogCollectConfig() {
        return HuaweiLogCollectConfig.INSTANCE.getHuaweiLogCollectConfig();
    }

    @Override
    protected void desensitizeLog(final ShenyuRequestLog log, final KeyWordMatch keyWordMatch, final String desensitizeAlg) {

    }
}
