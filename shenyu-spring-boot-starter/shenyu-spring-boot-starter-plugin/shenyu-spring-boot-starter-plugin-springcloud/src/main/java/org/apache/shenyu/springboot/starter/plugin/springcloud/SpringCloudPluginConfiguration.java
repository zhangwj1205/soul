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

package org.apache.shenyu.springboot.starter.plugin.springcloud;

import org.apache.shenyu.common.config.ShenyuConfig;
import org.apache.shenyu.plugin.api.ShenyuPlugin;
import org.apache.shenyu.plugin.api.context.ShenyuContextDecorator;
import org.apache.shenyu.plugin.base.handler.PluginDataHandler;
import org.apache.shenyu.plugin.springcloud.SpringCloudPlugin;
import org.apache.shenyu.plugin.springcloud.context.SpringCloudShenyuContextDecorator;
import org.apache.shenyu.plugin.springcloud.handler.SpringCloudPluginDataHandler;
import org.apache.shenyu.plugin.springcloud.listener.SpringCloudHeartBeatListener;
import org.apache.shenyu.plugin.springcloud.loadbalance.ShenyuSpringCloudServiceChooser;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * The type Spring cloud plugin configuration.
 */
@Configuration
@ConditionalOnProperty(value = {"shenyu.plugins.spring-cloud.enabled"}, havingValue = "true", matchIfMissing = true)
public class SpringCloudPluginConfiguration {

    /**
     * shenyu springcloud loadbalancer.
     *
     * @return {@linkplain ShenyuSpringCloudServiceChooser}
     */
    @Bean
    public ShenyuSpringCloudServiceChooser shenyuSpringCloudLoadBalancerClient() {
        return new ShenyuSpringCloudServiceChooser();
    }

    /**
     * init springCloud plugin.
     *
     * @param serviceChooser service chooser
     * @return {@linkplain SpringCloudPlugin}
     */
    @Bean
    public ShenyuPlugin springCloudPlugin(final ObjectProvider<ShenyuSpringCloudServiceChooser> serviceChooser) {
        return new SpringCloudPlugin(serviceChooser.getIfAvailable());
    }

    /**
     * Spring cloud shenyu context decorator.
     *
     * @return the shenyu context decorator
     */
    @Bean
    public ShenyuContextDecorator springCloudShenyuContextDecorator() {
        return new SpringCloudShenyuContextDecorator();
    }

    /**
     * Spring cloud plugin data handler.
     *
     * @param shenyuConfig the shenyu config
     * @param env the Environment
     * @return the plugin data handler
     */
    @Bean
    public PluginDataHandler springCloudPluginDataHandler(final ShenyuConfig shenyuConfig, final Environment env) {
        return new SpringCloudPluginDataHandler(shenyuConfig.getSpringCloudCache(), env);
    }
    
    /**
     * Spring cloud heart beat listener.
     *
     * @param shenyuConfig the shenyu config
     * @return the spring cloud heartbeat listener {@linkplain SpringCloudHeartBeatListener}
     */
    @Bean
    public SpringCloudHeartBeatListener springCloudHeartBeatListener(final ShenyuConfig shenyuConfig) {
        return new SpringCloudHeartBeatListener(shenyuConfig.getSpringCloudCache());
    }

}
