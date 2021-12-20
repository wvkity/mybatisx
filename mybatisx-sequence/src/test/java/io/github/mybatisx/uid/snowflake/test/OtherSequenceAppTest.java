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
package io.github.mybatisx.uid.snowflake.test;

import io.github.mybatisx.uid.snowflake.SnowflakeSequence;
import io.github.mybatisx.uid.snowflake.Unique;
import io.github.mybatisx.uid.snowflake.core.Config;
import io.github.mybatisx.uid.snowflake.core.Core;
import io.github.mybatisx.uid.snowflake.core.Policy;
import io.github.mybatisx.uid.snowflake.distributor.MilliDistributor;
import io.github.mybatisx.uid.snowflake.twitter.AtomicSequence;
import io.github.mybatisx.uid.snowflake.twitter.LocalCacheSequence;
import io.github.mybatisx.uid.snowflake.twitter.SequenceConfig;
import io.github.mybatisx.uid.snowflake.twitter.SequenceCore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wvkity
 * @created 2021/12/20
 * @since 1.0.0
 */
public class OtherSequenceAppTest {

    private static final Logger log = LoggerFactory.getLogger(OtherSequenceAppTest.class);

    @Test
    public void test1() {
        final Config config = SequenceConfig.of(Policy.CACHE, MilliDistributor.of(1L, 3L));
        final Core core = SequenceCore.of(config);
        final SnowflakeSequence ss = LocalCacheSequence.of(core, 8);
        final long uid = ss.nextId();
        for (int i = 0; i < 8; i++) {
            ss.nextId();
        }
        final Unique unique = ss.parse(uid);
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

    @Test
    public void test2() {
        final Config config = SequenceConfig.of(Policy.CACHE, MilliDistributor.of(1L, 3L));
        final Core core = SequenceCore.of(config);
        final SnowflakeSequence ss = AtomicSequence.of(core);
        System.out.println(ss.setUseClock(true));
        final long uid = ss.nextId();
        for (int i = 0; i < 8; i++) {
            log.info("{}", ss.nextId());
        }
        final Unique unique = ss.parse(uid);
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }
}
