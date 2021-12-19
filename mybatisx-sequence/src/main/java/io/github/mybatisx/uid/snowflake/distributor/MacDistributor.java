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
package io.github.mybatisx.uid.snowflake.distributor;

import io.github.mybatisx.uid.Mac;

/**
 * 分配器
 * <p>
 * 根据Mac地址自动分配机器标识和数据中心标识
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class MacDistributor extends AbstractDistributor {

    private static final long serialVersionUID = -6171965018323288428L;

    public MacDistributor(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits) {
        this(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits, 0L);
    }

    public MacDistributor(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits,
                          long epochTimestamp) {
        super(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits,
                Mac.getMacWorkerId(workerIdBits, dataCenterIdBits), Mac.getMacDataCenterId(dataCenterIdBits),
                epochTimestamp);
    }

    @Override
    public String toString() {
        return "MacDistributor{" +
                "timestampBits=" + timestampBits +
                ", dataCenterIdBits=" + dataCenterIdBits +
                ", workerIdBits=" + workerIdBits +
                ", sequenceBits=" + sequenceBits +
                ", workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                ", epochTimestamp=" + epochTimestamp +
                '}';
    }

    public static MacDistributor of(final int timestampBits, final int dataCenterIdBits, final int workerIdBits,
                                    final int sequenceBits) {
        return new MacDistributor(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits);
    }

    public static MacDistributor of(final int timestampBits, final int dataCenterIdBits, final int workerIdBits,
                                    final int sequenceBits, final long epochTimestamp) {
        return new MacDistributor(timestampBits, dataCenterIdBits, workerIdBits, sequenceBits, epochTimestamp);
    }
}
