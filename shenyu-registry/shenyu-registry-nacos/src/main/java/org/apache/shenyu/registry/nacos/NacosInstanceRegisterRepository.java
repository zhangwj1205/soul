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

package org.apache.shenyu.registry.nacos;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.shenyu.common.constant.Constants;
import org.apache.shenyu.common.exception.ShenyuException;
import org.apache.shenyu.registry.api.ShenyuInstanceRegisterRepository;
import org.apache.shenyu.registry.api.config.RegisterConfig;
import org.apache.shenyu.registry.api.entity.InstanceEntity;
import org.apache.shenyu.spi.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The type Nacos instance register repository.
 */
@Join
public class NacosInstanceRegisterRepository implements ShenyuInstanceRegisterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosInstanceRegisterRepository.class);

    private static final String NAMESPACE = "nacosNameSpace";

    private NamingService namingService;

    private String groupName;

    @Override
    public void init(final RegisterConfig config) {
        Properties properties = config.getProps();
        Properties nacosProperties = new Properties();
        this.groupName = properties.getProperty("groupName", "DEFAULT_GROUP");
        String serverAddr = config.getServerLists();
        nacosProperties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        nacosProperties.put(PropertyKeyConst.NAMESPACE, properties.getProperty(NAMESPACE, ""));
        nacosProperties.put(PropertyKeyConst.USERNAME, properties.getProperty(PropertyKeyConst.USERNAME, ""));
        nacosProperties.put(PropertyKeyConst.PASSWORD, properties.getProperty(PropertyKeyConst.PASSWORD, ""));
        nacosProperties.put(PropertyKeyConst.ACCESS_KEY, properties.getProperty(PropertyKeyConst.ACCESS_KEY, ""));
        nacosProperties.put(PropertyKeyConst.SECRET_KEY, properties.getProperty(PropertyKeyConst.SECRET_KEY, ""));
        try {
            this.namingService = NamingFactory.createNamingService(nacosProperties);
        } catch (NacosException e) {
            throw new ShenyuException(e);
        }
    }

    @Override
    public void persistInstance(final InstanceEntity instance) {
        try {
            Instance inst = new Instance();
            inst.setWeight(1.0d);
            inst.setEphemeral(true);
            inst.setIp(instance.getHost());
            inst.setPort(instance.getPort());
            inst.setInstanceId(buildInstanceNodeName(instance));
            inst.setServiceName(instance.getAppName());
            namingService.registerInstance(instance.getAppName(), groupName, inst);
            LOGGER.info("nacos client register success: {}", inst);
        } catch (NacosException e) {
            throw new ShenyuException(e);
        }
    }

    @Override
    public List<InstanceEntity> selectInstances(final String selectKey) {
        return getInstanceRegisterDTOS(selectKey);
    }

    private String buildInstanceNodeName(final InstanceEntity instance) {
        String host = instance.getHost();
        int port = instance.getPort();
        return String.join(Constants.COLONS, host, Integer.toString(port));
    }

    private List<InstanceEntity> getInstanceRegisterDTOS(final String selectKey) {
        List<InstanceEntity> result = new ArrayList<>();
        try {
            List<Instance> instances = namingService.selectInstances(selectKey, groupName, true);
            instances.forEach(instance -> result.add(convertFromInstance(instance)));
        } catch (Exception e) {
            LOGGER.error("getInstanceRegisterDTOS error", e);
        }
        return result;
    }

    private InstanceEntity convertFromInstance(final Instance instance) {
        InstanceEntity instanceEntity = new InstanceEntity();
        instanceEntity.setPort(instance.getPort());
        instanceEntity.setHost(instance.getIp());
        instanceEntity.setAppName(instance.getServiceName());
        instanceEntity.setUri(getURI(instance.getMetadata(), instance));
        return instanceEntity;
    }

    private URI getURI(final Map<String, String> metadata, final Instance instance) {
        boolean secure = false;
        if (metadata.containsKey("secure")) {
            secure = Boolean.parseBoolean(metadata.get("secure"));
        }
        String scheme = secure ? "https" : "http";
        int port = instance.getPort();
        if (port <= 0) {
            port = secure ? 443 : 80;
        }
        String uri = String.format("%s://%s:%s", scheme, instance.getIp(), port);
        return URI.create(uri);
    }

    @Override
    public void close() {
        try {
            namingService.shutDown();
        } catch (NacosException e) {
            throw new ShenyuException(e);
        }
    }
}
