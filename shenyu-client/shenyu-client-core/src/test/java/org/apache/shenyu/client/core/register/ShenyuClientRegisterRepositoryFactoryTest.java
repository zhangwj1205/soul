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

package org.apache.shenyu.client.core.register;

import org.apache.shenyu.register.client.api.ShenyuClientRegisterRepository;
import org.apache.shenyu.register.common.config.ShenyuRegisterCenterConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.shenyu.client.core.register.ShenyuClientRegisterRepositoryFactory.getRepositoryMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShenyuClientRegisterRepositoryFactoryTest {

    @Mock
    private ShenyuClientRegisterRepository repository;

    @Mock
    private ShenyuRegisterCenterConfig config;

    @Test
    public void testNewInstanceExistingRegisterType() {

        when(config.getRegisterType()).thenReturn("existingType");

        ShenyuClientRegisterRepositoryFactory.getRepositoryMap().put("existingType", repository);

        ShenyuClientRegisterRepository returnedRepository = ShenyuClientRegisterRepositoryFactory.newInstance(config);

        Assertions.assertEquals(repository, returnedRepository);
        Assertions.assertEquals(1, getRepositoryMap().size());
    }
}

