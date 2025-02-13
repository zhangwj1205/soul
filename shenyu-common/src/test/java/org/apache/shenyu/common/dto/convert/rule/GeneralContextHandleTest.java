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

package org.apache.shenyu.common.dto.convert.rule;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

/**
 * Test case for GeneralContextHandle.
 */
public class GeneralContextHandleTest {
    
    @Test
    public void testGetterSetter() {
        GeneralContextHandle handle = new GeneralContextHandle();
        handle.setGeneralContextKey("contextKey");
        handle.setGeneralContextType("contextType");
        handle.setGeneralContextValue("contextValue");
        
        assertThat(handle.getGeneralContextKey(), is("contextKey"));
        assertThat(handle.getGeneralContextType(), is("contextType"));
        assertThat(handle.getGeneralContextValue(), is("contextValue"));
    }
    
    @Test
    public void testEqualsAndHashCode() {
        GeneralContextHandle handle1 = new GeneralContextHandle("type", "key", "val");
        GeneralContextHandle handle2 = new GeneralContextHandle("type", "key", "val");
        
        assertThat(ImmutableSet.of(handle1, handle2), hasSize(1));
    }
    
}
