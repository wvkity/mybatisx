/*
 * Copyright (c) 2021-Now, wvkity(wvkity@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.mybatisx.result.test;

import com.alibaba.fastjson.JSON;
import io.github.mybatisx.result.Status;
import io.github.mybatisx.result.core.MultiResult;
import io.github.mybatisx.result.core.PlainResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public class ResultAppTest {

    public static final Logger log = LoggerFactory.getLogger(ResultAppTest.class);

    @Test
    public void stdResultTest() {
        final PlainResult<Object> sr1 = PlainResult.create().with(PlainResult::setData, 1L).build();
        final PlainResult<String> sr2 = PlainResult.ok("success");
        final PlainResult<String> sr3 = PlainResult.failure(Status.FAILURE);
        final PlainResult<Object> sr4 = PlainResult.failure(Status.SERVER_ERROR.getCode(), "系统繁忙，请稍后再试");
        final PlainResult<Object> sr5 = PlainResult.create()
                .with(PlainResult::setData, 1L)
                .with(PlainResult::setCode, -500)
                .with(PlainResult::setCode, 400500)
                .with(PlainResult::setMsg, "任务失败")
                .build();
        log.info("sr1结果: {}", JSON.toJSONString(sr1));
        log.info("sr2结果: {}", JSON.toJSONString(sr2));
        log.info("sr3结果: {}", JSON.toJSONString(sr3));
        log.info("sr4结果: {}", JSON.toJSONString(sr4));
        log.info("sr5结果: {}", JSON.toJSONString(sr5));
    }

    @Test
    public void multiResultTest() {
        final MultiResult mr1 = MultiResult.create().build();
        mr1.put("s1", "admin").put("v1", 1L).put("v2", "a").array("array", 1L, 2L, 3L);
        mr1.withArray("array", 4L, 5L);
        mr1.map("map", "k1", 1L).withMap("map", "k2", 2L);
        log.info("mr1结果: {}", JSON.toJSONString(mr1));
        log.info("获取字符串值: {}", mr1.getString("s1"));
        log.info("获取Long值: {}", mr1.getLong("v1"));
        log.info("获取map值: {}", mr1.getMap("map"));
        // log.info("获取Integer值: {}", mr1.getInt("v2"));
    }
}
