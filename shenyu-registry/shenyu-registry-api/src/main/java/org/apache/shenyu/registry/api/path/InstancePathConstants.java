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

package org.apache.shenyu.registry.api.path;

/**
 * zookeeper register center.
 */
public class InstancePathConstants {
    
    /**
     * root path of zookeeper register center.
     */
    public static final String ROOT_PATH = "/shenyu/register";
    
    /**
     * constants of separator.
     */
    private static final String SEPARATOR = "/";

    /**
     * Dot separator.
     */
    private static final String DOT_SEPARATOR = ".";
    
    /**
     * Build instance parent path string.
     * build child path of "/shenyu/register/instance/
     *
     * @return the string
     */
    public static String buildInstanceParentPath() {
        return String.join(SEPARATOR, ROOT_PATH, "instance");
    }

    /**
     * Build instance parent path string.
     * build child path of "/shenyu/register/instance/serviceName
     *
     * @param serviceName serviceName
     * @return the string
     */
    public static String buildInstanceParentPath(final String serviceName) {
        return String.join(SEPARATOR, ROOT_PATH, "instance", serviceName);
    }

    /**
     * Build real node string.
     *
     * @param nodePath the node path
     * @param nodeName the node name
     * @return the string
     */
    public static String buildRealNode(final String nodePath, final String nodeName) {
        return String.join(SEPARATOR, nodePath, nodeName);
    }
}
