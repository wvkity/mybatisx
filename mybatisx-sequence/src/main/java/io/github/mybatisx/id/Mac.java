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
package io.github.mybatisx.id;

import io.github.mybatisx.lang.Maths;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * Mac分配机器标识和数据中心标识工具
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public final class Mac {

    private static final Logger log = LoggerFactory.getLogger(Mac.class);

    private Mac() {
    }

    /**
     * 根据Mac地址获取数据中心标识
     *
     * @param dataCenterIdBits 数据中心标识位数
     * @return 数据中心标识
     */
    public static long getMacDataCenterId(final int dataCenterIdBits) {
        long uid = 0L;
        try {
            final InetAddress ip = InetAddress.getLocalHost();
            final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (Objects.isNull(network)) {
                uid = 1L;
            } else {
                final byte[] mac = network.getHardwareAddress();
                if (Objects.nonNull(mac)) {
                    final int size = mac.length;
                    uid = ((0x000000ff & (long) mac[size - 2]) | (0x0000ff00 & (((long) mac[size - 1]) << 8))) >> 6;
                    uid = uid % (Maths.shiftLeft(dataCenterIdBits) + 1L);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to get the corresponding worker id according to MAC address: " + e.getMessage(), e);
        }
        return uid;
    }

    /**
     * 根据mac地址获取机器标识
     *
     * @param workerIdBits     机器标识位数
     * @param dataCenterIdBits 数据中心标识位数
     * @return 机器标识
     */
    public static long getMacWorkerId(final int workerIdBits, final int dataCenterIdBits) {
        final StringBuilder sb = new StringBuilder();
        sb.append(Maths.shiftLeft(dataCenterIdBits));
        final String name = ManagementFactory.getRuntimeMXBean().getName();
        if (Strings.isNotWhitespace(name)) {
            sb.append(name.split("@")[0]);
        }
        return (sb.toString().hashCode() & 0xffff) % (Maths.shiftLeft(workerIdBits) + 1L);
    }

}
