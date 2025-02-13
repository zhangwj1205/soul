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

package org.apache.shenyu.registry.etcd;

import io.etcd.jetcd.Watch;
import io.etcd.jetcd.watch.WatchEvent;
import org.apache.shenyu.common.constant.Constants;
import org.apache.shenyu.common.utils.GsonUtils;
import org.apache.shenyu.registry.api.ShenyuInstanceRegisterRepository;
import org.apache.shenyu.registry.api.config.RegisterConfig;
import org.apache.shenyu.registry.api.entity.InstanceEntity;
import org.apache.shenyu.registry.api.path.InstancePathConstants;
import org.apache.shenyu.spi.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Etcd instance register repository.
 */
@Join
public class EtcdInstanceRegisterRepository implements ShenyuInstanceRegisterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdInstanceRegisterRepository.class);

    private EtcdClient client;

    private final Map<String, List<InstanceEntity>> watcherInstanceRegisterMap = new HashMap<>();

    @Override
    public void init(final RegisterConfig config) {
        Properties props = config.getProps();
        long timeout = Long.parseLong(props.getProperty("etcdTimeout", "3000"));
        long ttl = Long.parseLong(props.getProperty("etcdTTL", "5"));
        client = new EtcdClient(config.getServerLists(), ttl, timeout);
    }

    @Override
    public void persistInstance(final InstanceEntity instance) {
        String instanceNodeName = buildInstanceNodeName(instance);
        String instancePath = InstancePathConstants.buildInstanceParentPath(instance.getAppName());
        String realNode = InstancePathConstants.buildRealNode(instancePath, instanceNodeName);
        String nodeData = GsonUtils.getInstance().toJson(instance);
        client.putEphemeral(realNode, nodeData);
        LOGGER.info("etcd client register success: {}", nodeData);
    }

    @Override
    public List<InstanceEntity> selectInstances(final String selectKey) {
        final String watchKey = InstancePathConstants.buildInstanceParentPath(selectKey);
        final Function<Map<String, String>, List<InstanceEntity>> getInstanceRegisterFun = childrenList ->
                childrenList.values().stream().map(x -> {
                    InstanceEntity instanceEntity = GsonUtils.getInstance().fromJson(x, InstanceEntity.class);
                    instanceEntity.setUri(getURI(x, instanceEntity.getPort(), instanceEntity.getHost()));
                    return instanceEntity;
                }).collect(Collectors.toList());
        if (watcherInstanceRegisterMap.containsKey(selectKey)) {
            return getInstanceRegisterFun.apply(client.getKeysMapByPrefix(watchKey));
        }
        Map<String, String> serverNodes = client.getKeysMapByPrefix(watchKey);
        this.client.watchKeyChanges(watchKey, Watch.listener(response -> {
            for (WatchEvent event : response.getEvents()) {
                String value = event.getKeyValue().getValue().toString(StandardCharsets.UTF_8);
                String path = event.getKeyValue().getKey().toString(StandardCharsets.UTF_8);
                switch (event.getEventType()) {
                    case PUT:
                        serverNodes.put(path, value);
                        LOGGER.info("watch key {} updated, value is {}", watchKey, value);
                        continue;
                    case DELETE:
                        serverNodes.remove(path);
                        LOGGER.info("watch key {} deleted, key is {}", watchKey, path);
                        continue;
                    default:
                }
            }
            watcherInstanceRegisterMap.put(selectKey, getInstanceRegisterFun.apply(serverNodes));
        }));
        final List<InstanceEntity> instanceEntities = getInstanceRegisterFun.apply(serverNodes);
        watcherInstanceRegisterMap.put(selectKey, instanceEntities);
        return instanceEntities;
    }

    private URI getURI(final String instanceRegisterJsonStr, final int port, final String host) {
        String scheme = (instanceRegisterJsonStr.contains("https") || instanceRegisterJsonStr.contains("HTTPS")) ? "https" : "http";
        String uri = String.format("%s://%s:%s", scheme, host, port);
        return URI.create(uri);
    }

    private String buildInstanceNodeName(final InstanceEntity instance) {
        String host = instance.getHost();
        int port = instance.getPort();
        return String.join(Constants.COLONS, host, Integer.toString(port));
    }

    @Override
    public void close() {
        client.close();
    }
}
