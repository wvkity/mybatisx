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

import io.github.mybatisx.reflect.Reflections;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author wvkity
 * @created 2021/12/21
 * @since 1.0.0
 */
public class ObjectAppTest {

    private static final Logger log = LoggerFactory.getLogger(ObjectAppTest.class);

    interface F {

    }

    static class A {
    }

    static class B extends A {

    }

    static class C extends B implements F {

    }

    @Test
    public void test1() {
        Object obj = new Object();
        A a = new A();
        log.info("is Object: {}", Objects.isObject(obj));
        log.info("is Object: {}", Objects.isObject(a));
        log.info("is Object: {}", Types.isObject(a.getClass().getSuperclass()));
        log.info("is Object: {}", Types.isObject(A.class));
        log.info("is Object: {}", Types.isObject(null));
        log.info("java version: {}", System.getProperty("java.version"));
    }

    @Test
    public void test2() {
        final B b = new B();
        final Set<Class<?>> ss1 = Reflections.getAllTypes(b.getClass());
        final Set<Class<?>> ss2 = Reflections.getAllSuperTypes(b.getClass());
        log.info("{}", ss1);
        log.info("{}", ss2);
        final C c = new C();
        final Set<Class<?>> ss3 = Reflections.getAllSuperTypes(c.getClass());
        final Set<Class<?>> ss4 = Reflections.getAllSuperTypes(c.getClass(), Class::isInterface);
        log.info("{}", ss3);
        log.info("{}", ss4);
    }
}
