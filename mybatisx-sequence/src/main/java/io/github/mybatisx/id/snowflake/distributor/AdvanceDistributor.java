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
package io.github.mybatisx.id.snowflake.distributor;

/**
 * 自定义分配器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class AdvanceDistributor extends AbstractDistributor {

    private static final long serialVersionUID = 7854689925686805873L;

    public AdvanceDistributor(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits,
                              long workerId, long dataCenterId) {
        this(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits, workerId, dataCenterId, 0L);
    }

    public AdvanceDistributor(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits,
                              long workerId, long dataCenterId, long epochTimestamp) {
        super(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits, workerId, dataCenterId, epochTimestamp);
    }

    @Override
    public String toString() {
        return "AdvanceDistributor{" +
                "timestampBits=" + timestampBits +
                ", dataCenterIdBits=" + dataCenterIdBits +
                ", workerIdBits=" + workerIdBits +
                ", sequenceBits=" + sequenceBits +
                ", workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                ", epochTimestamp=" + epochTimestamp +
                '}';
    }

    public static AdvanceDistributor of(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits,
                                        long workerId, long dataCenterId) {
        return new AdvanceDistributor(timestampBits, dataCenterIdBits, workerIdBits,
                sequenceBits, workerId, dataCenterId);
    }

    public static AdvanceDistributor of(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits,
                                        long workerId, long dataCenterId, long epochTimestamp) {
        return new AdvanceDistributor(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits,
                workerId, dataCenterId, epochTimestamp);
    }
}
