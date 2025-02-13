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

package org.apache.shenyu.admin.model.bean;

import java.util.List;

/**
 * DocInfo.
 */
public class DocInfo {

    private String title;

    private String clusterName;

    private String contextPath;

    private String docMd5;

    private List<DocModule> docModuleList;

    /**
     * getTitle.
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * setTitle.
     *
     * @param title title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * getClusterName.
     *
     * @return String
     */
    public String getClusterName() {
        return clusterName;
    }

    /**
     * setServiceId.
     *
     * @param clusterName clusterName
     */
    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }

    /**
     * get contextPath.
     *
     * @return contextPath
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * set contextPath.
     *
     * @param contextPath contextPath
     */
    public void setContextPath(final String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * getDocMd5.
     * @return docMd5
     */
    public String getDocMd5() {
        return docMd5;
    }

    /**
     * setDocMd5.
     * @param docMd5 docMd5
     */
    public void setDocMd5(final String docMd5) {
        this.docMd5 = docMd5;
    }

    /**
     * getDocModuleList.
     *
     * @return List
     */
    public List<DocModule> getDocModuleList() {
        return docModuleList;
    }

    /**
     * setDocModuleList.
     *
     * @param docModuleList docModuleList
     */
    public void setDocModuleList(final List<DocModule> docModuleList) {
        this.docModuleList = docModuleList;
    }
}
