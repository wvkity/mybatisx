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
package io.github.mybatisx.lambda;

import io.github.mybatisx.util.SerializationHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.invoke.MethodHandleInfo;
import java.util.Objects;

/**
 * copy {@link java.lang.invoke.SerializedLambda}
 *
 * @author wvkity
 * @created 2022/1/8
 * @since 1.0.0
 */
public class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;
    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;


    /**
     * 通过反序列化将序列化对象转换成{@link SerializedLambda}对象
     *
     * @param serializable {@link Serializable}
     * @return {@link SerializedLambda}
     */
    public static SerializedLambda resolve(final Serializable serializable) {
        final byte[] bytes = SerializationHelper.serialize(serializable);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Objects.requireNonNull(bytes))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                final Class<?> clazz = super.resolveClass(desc);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Get the name of the class that captured this lambda.
     *
     * @return the name of the class that captured this lambda
     */
    public Class<?> getCapturingClass() {
        return capturingClass;
    }

    /**
     * Get the name of the invoked type to which this
     * lambda has been converted
     *
     * @return the name of the functional interface class to which
     * this lambda has been converted
     */
    public String getFunctionalInterfaceClass() {
        return functionalInterfaceClass;
    }

    /**
     * Get the name of the primary method for the functional interface
     * to which this lambda has been converted.
     *
     * @return the name of the primary methods of the functional interface
     */
    public String getFunctionalInterfaceMethodName() {
        return functionalInterfaceMethodName;
    }

    /**
     * Get the signature of the primary method for the functional
     * interface to which this lambda has been converted.
     *
     * @return the signature of the primary method of the functional
     * interface
     */
    public String getFunctionalInterfaceMethodSignature() {
        return functionalInterfaceMethodSignature;
    }

    /**
     * Get the name of the class containing the implementation
     * method.
     *
     * @return the name of the class containing the implementation
     * method
     */
    public String getImplClass() {
        return implClass;
    }

    /**
     * Get the name of the implementation method.
     *
     * @return the name of the implementation method
     */
    public String getImplMethodName() {
        return implMethodName;
    }

    /**
     * Get the signature of the implementation method.
     *
     * @return the signature of the implementation method
     */
    public String getImplMethodSignature() {
        return implMethodSignature;
    }

    /**
     * Get the method handle kind (see {@link MethodHandleInfo}) of
     * the implementation method.
     *
     * @return the method handle kind of the implementation method
     */
    public int getImplMethodKind() {
        return implMethodKind;
    }

    /**
     * Get the signature of the primary functional interface method
     * after type variables are substituted with their instantiation
     * from the capture site.
     *
     * @return the signature of the primary functional interface method
     * after type variable processing
     */
    public final String getInstantiatedMethodType() {
        return instantiatedMethodType;
    }

    /**
     * Get the count of dynamic arguments to the lambda capture site.
     *
     * @return the count of dynamic arguments to the lambda capture site
     */
    public int getCapturedArgCount() {
        return capturedArgs.length;
    }

    /**
     * Get a dynamic argument to the lambda capture site.
     *
     * @param i the argument to capture
     * @return a dynamic argument to the lambda capture site
     */
    public Object getCapturedArg(int i) {
        return capturedArgs[i];
    }

    /**
     * 获取显现类名
     *
     * @return 类名
     */
    private String getImplClassName() {
        return normalized(implClass);
    }

    /**
     * 获取接口类名
     *
     * @return 类名
     */
    private String getFunctionalInterfaceClassName() {
        return normalized(functionalInterfaceClass);
    }

    private String normalized(final String name) {
        return name.replace('/', '.');
    }

    @Override
    public String toString() {
        String interfaceName = getFunctionalInterfaceClassName();
        String implName = getImplClassName();
        return String.format("%s -> %s::%s",
                interfaceName.substring(interfaceName.lastIndexOf('.') + 1),
                implName.substring(implName.lastIndexOf('.') + 1),
                implMethodName);
    }
}
