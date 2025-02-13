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

package org.apache.shenyu.admin.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.shenyu.admin.mapper.ShenyuDictMapper;
import org.apache.shenyu.admin.model.dto.BatchCommonDTO;
import org.apache.shenyu.admin.model.dto.ShenyuDictDTO;
import org.apache.shenyu.admin.model.entity.ShenyuDictDO;
import org.apache.shenyu.admin.model.page.CommonPager;
import org.apache.shenyu.admin.model.page.PageParameter;
import org.apache.shenyu.admin.model.query.ShenyuDictQuery;
import org.apache.shenyu.admin.model.result.ConfigImportResult;
import org.apache.shenyu.admin.model.vo.ShenyuDictVO;
import org.apache.shenyu.admin.service.impl.ShenyuDictServiceImpl;
import org.apache.shenyu.admin.service.publish.DictEventPublisher;
import org.apache.shenyu.common.utils.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

/**
 * Test cases for ShenyuDictService.
 */
@ExtendWith(MockitoExtension.class)
public final class ShenyuDictServiceTest {

    @InjectMocks
    private ShenyuDictServiceImpl shenyuDictService;

    @Mock
    private ShenyuDictMapper shenyuDictMapper;
    
    @Mock
    private DictEventPublisher publisher;

    @Test
    public void testFindByType() {
        ShenyuDictDO shenyuDictDO = buildShenyuDictDO();
        given(this.shenyuDictMapper.selectByQuery(any())).willReturn(Collections.singletonList(shenyuDictDO));
        List<ShenyuDictVO> shenyuDictVOList = this.shenyuDictService.list("rule");
        assertEquals(1, shenyuDictVOList.size());
        assertEquals(shenyuDictDO.getId(), shenyuDictVOList.get(0).getId());
    }

    @Test
    public void testFindById() {
        ShenyuDictDO shenyuDictDO = buildShenyuDictDO();
        given(this.shenyuDictMapper.selectById(eq("123"))).willReturn(shenyuDictDO);
        ShenyuDictVO shenyuDictVO = this.shenyuDictService.findById("123");
        assertNotNull(shenyuDictVO);
        assertEquals(shenyuDictDO.getId(), shenyuDictVO.getId());
    }

    @Test
    public void testCreateOrUpdate() {
        ShenyuDictDTO insertShenyuDictDTO = buildShenyuDictDTO();
        given(this.shenyuDictMapper.insertSelective(any())).willReturn(1);
        assertThat(this.shenyuDictService.createOrUpdate(insertShenyuDictDTO), greaterThan(0));
        final String id = UUIDUtils.getInstance().generateShortUuid();
        ShenyuDictDTO updateShenyuDictDTO = buildShenyuDictDTO(id);
        given(this.shenyuDictMapper.updateByPrimaryKeySelective(any())).willReturn(1);
        given(this.shenyuDictMapper.selectById(id)).willReturn(new ShenyuDictDO());
        assertThat(this.shenyuDictService.createOrUpdate(updateShenyuDictDTO), greaterThan(0));
    }

    @Test
    public void testDeleteShenyuDicts() {

        List idList = Collections.singletonList("123");
        given(shenyuDictMapper.deleteByIdList(idList)).willReturn(1);
        int count = shenyuDictService.deleteShenyuDicts(idList);
        assertThat(count, greaterThan(0));
    }

    private ShenyuDictDO buildSaveShenyuDictDO() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String id = UUIDUtils.getInstance().generateShortUuid();
        return ShenyuDictDO.builder()
                .id(id)
                .sort(1)
                .desc("test")
                .dictCode("t_dict_" + Math.random())
                .dictName("t_d_v")
                .enabled(false)
                .type("rule")
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    @Test
    public void testBatchEnabled() {
        BatchCommonDTO batchCommonDTO = new BatchCommonDTO();
        batchCommonDTO.setEnabled(false);
        Integer idNullResult = this.shenyuDictService.enabled(batchCommonDTO.getIds(), false);
        assertThat(idNullResult, comparesEqualTo(0));
        batchCommonDTO.setIds(new ArrayList<>());
        Integer idEmptyResult = this.shenyuDictService.enabled(batchCommonDTO.getIds(), false);
        assertThat(idEmptyResult, comparesEqualTo(0));
        batchCommonDTO.setIds(Collections.singletonList("123"));
        given(this.shenyuDictMapper.enabled(eq(batchCommonDTO.getIds()), eq(batchCommonDTO.getEnabled()))).willReturn(1);
        assertThat(this.shenyuDictService.enabled(batchCommonDTO.getIds(), batchCommonDTO.getEnabled()), greaterThan(0));
    }

    @Test
    public void testListByPage() {
        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(5);
        pageParameter.setTotalCount(10);
        pageParameter.setTotalPage(pageParameter.getTotalCount() / pageParameter.getPageSize());
        ShenyuDictQuery shenyuDictQuery = new ShenyuDictQuery("1", "t", "t_n", pageParameter);
        List<ShenyuDictDO> shenyuDictDOList = IntStream.range(0, 10).mapToObj(i -> buildShenyuDictDO()).collect(Collectors.toList());
        given(this.shenyuDictMapper.selectByQuery(shenyuDictQuery)).willReturn(shenyuDictDOList);
        final CommonPager<ShenyuDictVO> pluginDOCommonPager = this.shenyuDictService.listByPage(shenyuDictQuery);
        assertEquals(pluginDOCommonPager.getDataList().size(), shenyuDictDOList.size());
    }

    @Test
    public void testListAllData() {
        List<ShenyuDictDO> shenyuDictDOList = IntStream.range(0, 10).mapToObj(i -> buildShenyuDictDO()).collect(Collectors.toList());
        given(this.shenyuDictMapper.selectByQuery(any())).willReturn(shenyuDictDOList);
        List<ShenyuDictVO> shenyuDictVOList = shenyuDictService.listAllData();
        assertEquals(shenyuDictVOList.size(), shenyuDictDOList.size());
    }

    @Test
    public void testImportData() {
        List<ShenyuDictDO> shenyuDictDOList = Collections.singletonList(buildShenyuDictDO("haha"));
        given(this.shenyuDictMapper.selectByQuery(any())).willReturn(shenyuDictDOList);
        List<ShenyuDictDTO> shenyuDictDTOList = Collections.singletonList(buildShenyuDictDTO(null, "lala"));
        ConfigImportResult configImportResult = shenyuDictService.importData(shenyuDictDTOList);

        assertNotNull(configImportResult);
        assertEquals(configImportResult.getSuccessCount(), shenyuDictDTOList.size());
    }

    private ShenyuDictDTO buildShenyuDictDTO() {
        return buildShenyuDictDTO("");
    }

    private ShenyuDictDTO buildShenyuDictDTO(final String id) {
        ShenyuDictDTO shenyuDictDTO = new ShenyuDictDTO();
        if (StringUtils.isNotBlank(id)) {
            shenyuDictDTO.setId(id);
        }
        shenyuDictDTO.setDesc("test");
        shenyuDictDTO.setSort(1);
        shenyuDictDTO.setDesc("test");
        shenyuDictDTO.setDictCode("t_dict_1");
        shenyuDictDTO.setDictName("t_d_v");
        shenyuDictDTO.setEnabled(false);
        shenyuDictDTO.setType("rule");
        return shenyuDictDTO;
    }

    private ShenyuDictDTO buildShenyuDictDTO(final String id, final String name) {
        ShenyuDictDTO shenyuDictDTO = new ShenyuDictDTO();
        if (StringUtils.isNotBlank(id)) {
            shenyuDictDTO.setId(id);
        }
        shenyuDictDTO.setDesc("test");
        shenyuDictDTO.setSort(1);
        shenyuDictDTO.setDesc("test");
        shenyuDictDTO.setDictCode("t_dict_1");
        shenyuDictDTO.setDictName("t_d_v" + name);
        shenyuDictDTO.setEnabled(false);
        shenyuDictDTO.setType("rule");
        return shenyuDictDTO;
    }

    private ShenyuDictDO buildShenyuDictDO() {
        ShenyuDictDO shenyuDictDO = ShenyuDictDO.buildShenyuDictDO(buildShenyuDictDTO());
        Optional.ofNullable(shenyuDictDO).ifPresent(it -> {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            it.setDateCreated(now);
            it.setDateUpdated(now);
        });
        return shenyuDictDO;
    }

    private ShenyuDictDO buildShenyuDictDO(final String name) {
        ShenyuDictDO shenyuDictDO = ShenyuDictDO.buildShenyuDictDO(buildShenyuDictDTO("", name));
        Optional.ofNullable(shenyuDictDO).ifPresent(it -> {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            it.setDateCreated(now);
            it.setDateUpdated(now);
        });
        return shenyuDictDO;
    }
}
