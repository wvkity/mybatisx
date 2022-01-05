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
package io.github.mybatisx.id.snowflake.twitter;

import io.github.mybatisx.id.snowflake.Unique;
import io.github.mybatisx.id.snowflake.Parser;
import io.github.mybatisx.id.snowflake.core.Core;

/**
 * 唯一ID解析器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class SequenceParser implements Parser {

    /**
     * ID生成核心接口
     */
    private final Core core;

    public SequenceParser(Core core) {
        this.core = core;
    }

    @Override
    public Unique parse(long id) {
        final Core _$core = this.core;
        final long totalBits = SequenceCore.DEFAULT_MAX_BITS;
        final long signBits = _$core.getSignBits();
        final long timestampBits = _$core.getTimestampBits();
        final long workerIdBits = _$core.getWorkerIdBits();
        final long dataCenterIdBits = _$core.getDataCenterIdBits();
        final long sequenceBits = _$core.getSequenceBits();
        final long sequence = (id << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        final long workerId = (id << (timestampBits + dataCenterIdBits + signBits)) >>> (totalBits - workerIdBits);
        final long dataCenterId = (id << (timestampBits + signBits)) >>> (totalBits - dataCenterIdBits);
        final long deltaTime = id >>> (workerIdBits + dataCenterIdBits + sequenceBits);
        return new Unique(id, (_$core.getEpochTimestamp() + deltaTime), dataCenterId, workerId, sequence, _$core.getTimeUnit());
    }

    public static SequenceParser of(final Core core) {
        return new SequenceParser(core);
    }
}
