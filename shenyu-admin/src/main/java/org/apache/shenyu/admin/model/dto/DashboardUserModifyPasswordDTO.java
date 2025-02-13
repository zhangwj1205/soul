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

package org.apache.shenyu.admin.model.dto;

import org.apache.shenyu.admin.model.constant.RegConstant;
import org.apache.shenyu.admin.utils.FailI18nMessage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * this is dashboard user from by web front.
 */
public class DashboardUserModifyPasswordDTO implements Serializable {
    
    /**
     * primary key.
     */
    private String id;
    
    /**
     * user name.
     */
    private String userName;
    
    /**
     * user password.
     */
    @NotBlank
    @Pattern(regexp = RegConstant.PASSWORD_RULE, message = '{' + FailI18nMessage.PASSWORD_MUST + '}')
    private String password;
    
    /**
     * user password.
     */
    @NotBlank
    private String oldPassword;
    
    public DashboardUserModifyPasswordDTO() {
    }
    
    public DashboardUserModifyPasswordDTO(final String id, final String userName, final String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
    
    public DashboardUserModifyPasswordDTO(final String id, final String userName, final String password, final String oldPassword) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.oldPassword = oldPassword;
    }
    
    /**
     * Gets the value of id.
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }
    
    /**
     * Sets the id.
     *
     * @param id id
     */
    public void setId(final String id) {
        this.id = id;
    }
    
    /**
     * Gets the value of userName.
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Sets the userName.
     *
     * @param userName userName
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }
    
    /**
     * Gets the value of password.
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password.
     *
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }
    
    /**
     * get oldPassword.
     *
     * @return old password
     */
    public String getOldPassword() {
        return oldPassword;
    }
    
    /**
     * set oldPassword.
     *
     * @param oldPassword old password
     */
    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
