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
 * 抽象分配器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractDistributor implements Distributor {

    /// SECONDS LEVEL ARGS ///

    protected static final int SEC_TIMESTAMP_BITS = 31;
    protected static final int SEC_DATA_CENTER_ID_BITS = 8;
    protected static final int SEC_WORKER_ID_BITS = 8;
    protected static final int SEC_SEQUENCE_BITS = 16;

    /// MILLISECONDS LEVEL ARGS ///

    protected static final int MILLI_TIMESTAMP_BITS = 41;
    protected static final int MILLI_DATA_CENTER_ID_BITS = 5;
    protected static final int MILLI_WORKER_ID_BITS = 5;
    protected static final int MILLI_SEQUENCE_BITS = 12;

    protected final int timestampBits;
    protected final int dataCenterIdBits;
    protected final int workerIdBits;
    protected final int sequenceBits;
    protected final long workerId;
    protected final long dataCenterId;
    protected final long epochTimestamp;

    public AbstractDistributor(int timestampBits, int dataCenterIdBits, int workerIdBits, int sequenceBits,
                               long workerId, long dataCenterId, long epochTimestamp) {
        this.timestampBits = timestampBits;
        this.dataCenterIdBits = dataCenterIdBits;
        this.workerIdBits = workerIdBits;
        this.sequenceBits = sequenceBits;
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.epochTimestamp = epochTimestamp;
    }

    @Override
    public int getTimestampBits() {
        return this.timestampBits;
    }

    @Override
    public int getDataCenterIdBits() {
        return this.dataCenterIdBits;
    }

    @Override
    public int getWorkerIdBits() {
        return this.workerIdBits;
    }

    @Override
    public int getSequenceBits() {
        return this.sequenceBits;
    }

    @Override
    public long getWorkerId() {
        return this.workerId;
    }

    @Override
    public long getDataCenterId() {
        return this.dataCenterId;
    }

    @Override
    public long getEpochTimestamp() {
        return this.epochTimestamp;
    }

}
