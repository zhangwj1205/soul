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

package org.apache.shenyu.admin.utils;

import org.apache.shenyu.admin.model.result.AdminResult;
import org.apache.shenyu.common.exception.CommonErrorCode;

/**
 * ResultUtil.
 */
public final class ResultUtil {
    
    private ResultUtil() {
    }
    
    /**
     * ok.
     *
     * @param <T> response body type
     * @return admin result
     */
    public static <T> AdminResult<T> ok() {
        return ok(null);
    }
    
    /**
     * ok.
     *
     * @param data response body
     * @param <T>  response body type
     * @return admin result
     */
    public static <T> AdminResult<T> ok(final T data) {
        return ok(data, "ok");
    }
    
    /**
     * ok.
     *
     * @param data    response body
     * @param message response message
     * @param <T>     response body type
     * @return admin result
     */
    public static <T> AdminResult<T> ok(final T data, final String message) {
        return new AdminResult<>(CommonErrorCode.SUCCESSFUL, message, data);
    }
    
    /**
     * error.
     * @param message response message
     * @param <T>     response body type
     * @return admin result
     */
    public static <T> AdminResult<T> error(final String message) {
        return new AdminResult<>(CommonErrorCode.ERROR, message, null);
    }
}
