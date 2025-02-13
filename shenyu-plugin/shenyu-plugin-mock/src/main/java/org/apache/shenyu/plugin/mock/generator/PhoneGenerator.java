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

package org.apache.shenyu.plugin.mock.generator;

import org.apache.shenyu.plugin.mock.api.MockRequest;
import org.apache.shenyu.plugin.mock.util.MockUtil;
import org.apache.shenyu.spi.Join;

import java.util.List;

/**
 * 11-digit mobile number generator.
 */
@Join
public class PhoneGenerator implements Generator<String> {

    @Override
    public String getName() {
        return "phone";
    }

    @Override
    public String doGenerate(final List<String> params, final String rule, final MockRequest mockRequest) {
        return MockUtil.phone();
    }

    @Override
    public int getParamSize() {
        return 0;
    }

    @Override
    public boolean match(final String rule) {
        return rule.matches("^phone$");
    }

    @Override
    public String[] getPrefixAndSuffix() {
        return new String[]{"\"", "\""};
    }
}
