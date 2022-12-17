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
package io.github.mybatisx.lang;

import io.github.mybatisx.util.Optional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
public class StringAppTest {

    private static final Logger log = LoggerFactory.getLogger(StringAppTest.class);
    
    @Test
    public void test1() {
        log.info(Strings.toLowerCase("Admin test"));
        log.info(Strings.toUpperCase("admin test"));
        log.info(Strings.toUpperCase(null));
        log.info(Strings.toUpperCase("     "));
        java.util.Optional<String> opt = Optional.of("test").to();
        final Optional<String> opt2 = opt.map(Optional::ofNullable).orElseGet(Optional::empty);
        final Optional<Optional<String>>opt3 = Optional.ofNullable(opt2);
        System.out.println("{}: " + opt);
        System.out.println("{}: " + opt2);
        System.out.println("{}: " + opt3);
        log.info("string length: {}", Strings.toUpperCase("     "));
    }
}
