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
package io.github.mybatisx.base.test;

import com.google.common.base.CaseFormat;
import io.github.mybatisx.annotation.NamingStrategy;
import io.github.mybatisx.base.naming.NamingCase;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
public class NamingAppTest {

    static final Logger log = LoggerFactory.getLogger(NamingAppTest.class);
    
    @Test
    public void test1() {
        final String s1 = "admin";
        final String s2 = "Admin";
        final String s3 = "applicationContext";
        final String s4 = "ApplicationContext";
        final String s5 = "application_context";
        final String s6 = "APPLICATION_CONTEXT";
        log.info("========= LOWER_CAMEL ========");
        log.info(NamingCase.LOWER_CAMEL.to(NamingCase.UPPER_CAMEL, s1));
        log.info(NamingCase.LOWER_CAMEL.to(NamingStrategy.UPPER_CAMEL, s1));
        log.info("========= LOWER_CAMEL ========");
        log.info(NamingCase.UPPER_CAMEL.to(NamingCase.LOWER_CAMEL, s2));
        log.info(NamingCase.UPPER_CAMEL.to(NamingStrategy.LOWER_CAMEL, s2));
        log.info(NamingCase.UPPER_CAMEL.to(NamingCase.UPPER_UNDERSCORE, s2));
        log.info(NamingCase.UPPER_CAMEL.to(NamingStrategy.UPPER_UNDERSCORE, s2));
        log.info(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, s2));
        log.info("========= LOWER_UNDERSCORE ========");
        log.info(NamingCase.LOWER_UNDERSCORE.to(NamingCase.UPPER_UNDERSCORE, s5));
        log.info(NamingCase.LOWER_UNDERSCORE.to(NamingStrategy.UPPER_UNDERSCORE, s5));
        log.info(NamingCase.LOWER_UNDERSCORE.to(NamingCase.LOWER_CAMEL, s5));
        log.info(NamingCase.LOWER_UNDERSCORE.to(NamingStrategy.LOWER_CAMEL, s5));
        log.info(NamingCase.LOWER_UNDERSCORE.to(NamingCase.UPPER_CAMEL, s5));
        log.info(NamingCase.LOWER_UNDERSCORE.to(NamingStrategy.UPPER_CAMEL, s5));
        log.info("========= UPPER_UNDERSCORE ========");
        log.info(NamingCase.UPPER_UNDERSCORE.to(NamingCase.LOWER_UNDERSCORE, s6));
        log.info(NamingCase.UPPER_UNDERSCORE.to(NamingStrategy.LOWER_UNDERSCORE, s6));
        log.info(NamingCase.UPPER_UNDERSCORE.to(NamingCase.LOWER_CAMEL, s6));
        log.info(NamingCase.UPPER_UNDERSCORE.to(NamingStrategy.LOWER_CAMEL, s6));
        log.info(NamingCase.UPPER_UNDERSCORE.to(NamingCase.UPPER_CAMEL, s6));
        log.info(NamingCase.UPPER_UNDERSCORE.to(NamingStrategy.UPPER_CAMEL, s6));
    }
}
