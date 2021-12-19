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
 * 毫秒级分配器
 * <p>
 * 根据Mac地址自动分配机器标识和数据中心标识
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class MacMilliDistributor extends MilliDistributor {

    private static final long serialVersionUID = -6064855380066837558L;

    public MacMilliDistributor() {
        this(0L);
    }

    public MacMilliDistributor(long epochTimestamp) {
        super(Mac.getMacWorkerId(MILLI_WORKER_ID_BITS, MILLI_DATA_CENTER_ID_BITS),
                Mac.getMacDataCenterId(MILLI_DATA_CENTER_ID_BITS), epochTimestamp);
    }

    @Override
    public String toString() {
        return "MilliMacDistributor{" +
                "timestampBits=" + timestampBits +
                ", dataCenterIdBits=" + dataCenterIdBits +
                ", workerIdBits=" + workerIdBits +
                ", sequenceBits=" + sequenceBits +
                ", workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                ", epochTimestamp=" + epochTimestamp +
                '}';
    }

    public static MacMilliDistributor of() {
        return new MacMilliDistributor();
    }

    public static MacMilliDistributor of(final long epochTimestamp) {
        return new MacMilliDistributor(epochTimestamp);
    }

}
