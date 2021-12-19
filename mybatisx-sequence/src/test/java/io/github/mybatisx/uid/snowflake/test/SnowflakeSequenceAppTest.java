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
import io.github.mybatisx.uid.snowflake.distributor.AdvanceDistributor;
import io.github.mybatisx.uid.snowflake.distributor.Distributor;
import io.github.mybatisx.uid.snowflake.distributor.MacDistributor;
import io.github.mybatisx.uid.snowflake.distributor.MacMilliDistributor;
import io.github.mybatisx.uid.snowflake.distributor.MacSecDistributor;
import io.github.mybatisx.uid.snowflake.distributor.MilliDistributor;
import io.github.mybatisx.uid.snowflake.distributor.SecDistributor;
import io.github.mybatisx.uid.snowflake.twitter.DefaultSequence;
import io.github.mybatisx.uid.snowflake.twitter.SequenceConfig;
import io.github.mybatisx.uid.snowflake.twitter.SequenceCore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class SnowflakeSequenceAppTest {

    static final Logger log = LoggerFactory.getLogger(SnowflakeSequenceAppTest.class);

    @Test
    public void test1() {
        final Distributor db = new MilliDistributor(3L, 2L);
        final Config config = new SequenceConfig(Policy.DEFAULT, db);
        final Core core = new SequenceCore(config);
        final SnowflakeSequence ss = new DefaultSequence(core);
        final long uid = ss.nextId();
        final Unique unique = ss.parse(uid);
        for (int i = 0; i < 30; i++) {
            log.info("{}", ss.nextId());
        }
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

    @Test
    public void test2() {
        final Distributor db = new SecDistributor(2L, 3L);
        final Config config = new SequenceConfig(Policy.DEFAULT, db);
        final Core core = new SequenceCore(config);
        final SnowflakeSequence ss = new DefaultSequence(core);
        final long uid = ss.nextId();
        final Unique unique = ss.parse(uid);
        for (int i = 0; i < 30; i++) {
            log.info("{}", ss.nextId());
        }
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

    @Test
    public void test3() {
        final Distributor db = MacMilliDistributor.of();
        final Config config = SequenceConfig.of(Policy.DEFAULT, db);
        final Core core = SequenceCore.of(config);
        final SnowflakeSequence ss = DefaultSequence.of(core);
        final long uid = ss.nextId();
        final Unique unique = ss.parse(uid);
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

    @Test
    public void test4() {
        final Distributor db = MacSecDistributor.of();
        final Config config = SequenceConfig.of(Policy.DEFAULT, db);
        final Core core = SequenceCore.of(config);
        final SnowflakeSequence ss = DefaultSequence.of(core);
        final long uid = ss.nextId();
        final Unique unique = ss.parse(uid);
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

    @Test
    public void test5() {
        final Distributor db = MacDistributor.of(28, 11, 11, 13);
        final Config config = SequenceConfig.of(Policy.DEFAULT, db);
        final Core core = SequenceCore.of(config);
        final SnowflakeSequence ss = DefaultSequence.of(core);
        final long uid = ss.nextId();
        final Unique unique = ss.parse(uid);
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

    @Test
    public void test6() {
        final Distributor db = AdvanceDistributor.of(28, 11, 11, 13, 4L, 5L);
        final Config config = SequenceConfig.of(Policy.DEFAULT, db);
        final Core core = SequenceCore.of(config);
        final SnowflakeSequence ss = DefaultSequence.of(core);
        final long uid = ss.nextId();
        final Unique unique = ss.parse(uid);
        log.info("uid: {}", uid);
        log.info("uid info: {}", unique);
    }

}
