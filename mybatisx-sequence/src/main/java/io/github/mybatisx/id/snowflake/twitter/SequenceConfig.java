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

import io.github.mybatisx.id.snowflake.core.Config;
import io.github.mybatisx.id.snowflake.core.Level;
import io.github.mybatisx.id.snowflake.core.Policy;
import io.github.mybatisx.id.snowflake.distributor.Distributor;

/**
 * ID生成器配置
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class SequenceConfig implements Config {

    private static final long serialVersionUID = -5727346695193485257L;
    private final Level level;
    private final Policy policy;
    private final Distributor distributor;
    private final boolean useClock;

    public SequenceConfig(Policy policy, Distributor distributor) {
        this(Level.MILLISECONDS, policy, distributor, false);
    }

    public SequenceConfig(Level level, Policy policy, Distributor distributor) {
        this(level, policy, distributor, false);
    }

    public SequenceConfig(Level level, Policy policy, Distributor distributor, boolean useClock) {
        this.level = level;
        this.policy = policy;
        this.distributor = distributor;
        this.useClock = useClock;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public Policy getPolicy() {
        return this.policy;
    }

    @Override
    public Distributor getDistributor() {
        return this.distributor;
    }

    @Override
    public boolean isUseClock() {
        return this.useClock;
    }

    public static SequenceConfig of(final Policy policy, final Distributor distributor) {
        return new SequenceConfig(policy, distributor);
    }

    public static SequenceConfig of(final Level level, final Policy policy, final Distributor distributor) {
        return new SequenceConfig(level, policy, distributor);
    }

    public static SequenceConfig of(final Level level, final Policy policy, final Distributor distributor,
                                    final boolean useClock) {
        return new SequenceConfig(level, policy, distributor, useClock);
    }
}
