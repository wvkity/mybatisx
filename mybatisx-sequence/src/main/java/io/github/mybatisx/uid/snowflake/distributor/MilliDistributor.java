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

/**
 * 毫秒级分配器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class MilliDistributor extends AbstractDistributor {

    private static final long serialVersionUID = -2660095055759646400L;

    public MilliDistributor(long workerId, long dataCenterId) {
        this(workerId, dataCenterId, 0L);
    }

    public MilliDistributor(long workerId, long dataCenterId, long epochTimestamp) {
        super(MILLI_TIMESTAMP_BITS, MILLI_DATA_CENTER_ID_BITS, MILLI_WORKER_ID_BITS, MILLI_SEQUENCE_BITS,
                workerId, dataCenterId, epochTimestamp);
    }

    @Override
    public String toString() {
        return "MilliDistributor{" +
                "timestampBits=" + timestampBits +
                ", dataCenterIdBits=" + dataCenterIdBits +
                ", workerIdBits=" + workerIdBits +
                ", sequenceBits=" + sequenceBits +
                ", workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                ", epochTimestamp=" + epochTimestamp +
                '}';
    }

    public static MilliDistributor of(final long workerId, final long dataCenterId) {
        return new MilliDistributor(workerId, dataCenterId);
    }

    public static MilliDistributor of(final long workerId, final long dataCenterId, final long epochTimestamp) {
        return new MilliDistributor(workerId, dataCenterId, epochTimestamp);
    }

}
