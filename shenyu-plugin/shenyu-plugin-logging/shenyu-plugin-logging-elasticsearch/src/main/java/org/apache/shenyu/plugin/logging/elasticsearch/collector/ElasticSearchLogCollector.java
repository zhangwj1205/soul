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

import org.apache.shenyu.plugin.logging.common.collector.AbstractLogCollector;
import org.apache.shenyu.plugin.logging.common.collector.LogCollector;
import org.apache.shenyu.plugin.logging.common.entity.ShenyuRequestLog;
import org.apache.shenyu.plugin.logging.desensitize.api.matcher.KeyWordMatch;
import org.apache.shenyu.plugin.logging.elasticsearch.client.ElasticSearchLogCollectClient;
import org.apache.shenyu.plugin.logging.elasticsearch.config.ElasticSearchLogCollectConfig;
import org.apache.shenyu.plugin.logging.elasticsearch.handler.LoggingElasticSearchPluginDataHandler;

/**
 * elastic search log collector，depend a LogConsumeClient for consume logs.
 */
public class ElasticSearchLogCollector extends AbstractLogCollector<ElasticSearchLogCollectClient, ShenyuRequestLog, ElasticSearchLogCollectConfig.ElasticSearchLogConfig> {

    private static final LogCollector<ShenyuRequestLog> INSTANCE = new ElasticSearchLogCollector();

    /**
     * get LogCollector instance.
     *
     * @return LogCollector instance
     */
    public static LogCollector<ShenyuRequestLog> getInstance() {
        return INSTANCE;
    }

    @Override
    protected ElasticSearchLogCollectClient getLogConsumeClient() {
        return LoggingElasticSearchPluginDataHandler.getElasticSearchLogCollectClient();
    }

    @Override
    protected ElasticSearchLogCollectConfig.ElasticSearchLogConfig getLogCollectConfig() {
        return ElasticSearchLogCollectConfig.INSTANCE.getElasticSearchLogConfig();
    }

    /**
     * desensitize log.
     *
     * @param log            log entity
     * @param keyWordMatch   keyWordMatch
     * @param desensitizeAlg data desensitize algorithm
     */
    @Override
    protected void desensitizeLog(final ShenyuRequestLog log, final KeyWordMatch keyWordMatch, final String desensitizeAlg) {
    }
}
