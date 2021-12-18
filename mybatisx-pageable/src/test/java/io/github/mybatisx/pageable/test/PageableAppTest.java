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
package io.github.mybatisx.pageable.test;

import com.alibaba.fastjson.JSON;
import io.github.mybatisx.pageable.AdvDataPageRequest;
import io.github.mybatisx.pageable.AdvPageRequest;
import io.github.mybatisx.pageable.DataPageRequest;
import io.github.mybatisx.pageable.PageRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public class PageableAppTest {

    private static final Logger log = LoggerFactory.getLogger(PageableAppTest.class);

    @Test
    public void test() {
        final int records = 303;
        final int page = 8;
        final PageRequest pr1 = PageRequest.of(page);
        pr1.setRecords(records);
        final AdvPageRequest apr1 = AdvPageRequest.of(page);
        apr1.setRecords(records);
        final DataPageRequest<String> dpr1 = DataPageRequest.of(page);
        dpr1.setRecords(records);
        dpr1.addAll(Arrays.asList("a", "b", "c", "d", "e", "f"));
        final AdvDataPageRequest<String> adpr1 = AdvDataPageRequest.of(page);
        adpr1.setRecords(records);
        adpr1.setDisplay(10);
        adpr1.addAll(Arrays.asList("a", "b", "c", "d", "e", "f"));
        log.info("pr1: {}", JSON.toJSONString(pr1));
        log.info("pr1: {}", JSON.toJSONString(apr1));
        log.info("dpr1: {}", JSON.toJSONString(dpr1));
        log.info("adpr1: {}", JSON.toJSONString(adpr1));
    }
}
