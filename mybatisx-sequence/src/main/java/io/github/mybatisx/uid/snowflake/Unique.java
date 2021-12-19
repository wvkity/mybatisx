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
package io.github.mybatisx.uid.snowflake;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 唯一ID信息
 *
 * @author wvkity
 * @created 2021/12/18
 * @since 1.0.0
 */
public class Unique implements Serializable {

    private static final long serialVersionUID = 2982133121283787635L;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private final String id;
    private final long dataCenterId;
    private final long workerId;
    private final long sequence;
    private final long timestamp;
    private final TimeUnit timeUnit;
    private final LocalDateTime gmtTime;
    private final String timeString;

    public Unique(long id, long timestamp, long dataCenterId, long workerId, long sequence, TimeUnit timeUnit) {
        this.id = Long.toString(id);
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.sequence = sequence;
        this.timeUnit = timeUnit;
        this.timestamp = timestamp;
        final LocalDateTime _$gmtTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault());
        this.gmtTime = _$gmtTime;
        this.timeString = DTF.format(_$gmtTime);
    }

    public String getId() {
        return id;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getSequence() {
        return sequence;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public LocalDateTime getGmtTime() {
        return gmtTime;
    }

    public String getTimeString() {
        return timeString;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": \"" + this.id + "\"" +
                ", \"dataCenterId\": " + this.dataCenterId +
                ", \"workerId\": " + this.workerId +
                ", \"sequence\": " + this.sequence +
                ", \"timestamp\": " + this.timestamp +
                ", \"timeUnit\": \"" + this.timeUnit + "\"" +
                ", \"gmtTime\": \"" + this.gmtTime + "\"" +
                ", \"timeString\": \"" + this.timeString +
                "\"}";
    }
}
