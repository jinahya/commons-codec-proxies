/*
 * Copyright 2012 Jin Kwon <jinahya at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.codec.commons;


import java.lang.reflect.Method;


/**
 * Abstract class for proxies of {@link org.apache.commons.codec.StringEncoder}.
 *
 * @author <a href="mailto:jinahya@gmail.com">Jin Kwon</a>
 * @param <T> encoder (delegate) type parameter
 */
public abstract class StringEncoderProxy<T> extends EncoderProxy<T> {


    private static final String ENCODER_NAME =
        "org.apache.commons.codec.StringEncoder";


    /**
     * the class of {@link org.apache.commons.codec.StringEncoder}.
     */
    private static final Class<?> ENCODER;


    static {
        try {
            ENCODER = Class.forName(ENCODER_NAME);
        } catch (final ClassNotFoundException cnfe) {
            throw new InstantiationError(cnfe.getMessage());
        }
    }


    /**
     * the method of {@code encode(Ljava/lang/String;)Ljava/lang/String;}.
     */
    private static final Method ENCODE;


    static {
        try {
            ENCODE = ENCODER.getMethod("encode", String.class);
        } catch (final NoSuchMethodException nsme) {
            throw new InstantiationError(nsme.getMessage());
        }
    }


    /**
     * Creates a new proxy instance.
     *
     * @param <P> proxy type parameter
     * @param <T> user encoder type parameter
     * @param proxyType proxy type
     * @param encoderType encoder type
     * @param encoder encoder
     *
     * @return a new proxy instance
     */
    protected static <P extends AbstractEncoderProxy<T>, T> Object newInstance(
        final Class<P> proxyType, final Class<T> encoderType, final T encoder) {

        if (proxyType == null) {
            throw new NullPointerException("proxyType");
        }

        if (!StringEncoderProxy.class.isAssignableFrom(proxyType)) {
            throw new IllegalArgumentException(
                "proxyType(" + proxyType + ") is not assignable to "
                + StringEncoderProxy.class);
        }

        return newInstance(ENCODER.getClassLoader(), new Class<?>[]{ENCODER},
                           proxyType, encoderType, encoder);
    }


    /**
     * Creates a new instance.
     *
     * @param encoder encoding delegate
     */
    protected StringEncoderProxy(final T encoder) {

        super(encoder);
    }


    @Override
    public Object invoke(final Object proxy, final Method method,
                         final Object[] args)
        throws Throwable {

        if (ENCODE.equals(method)) {
            return encode((String) args[0]);
        }

        return super.invoke(proxy, method, args);
    }


    @Override
    protected Object encode(final Object source) throws Throwable {

        try {
            return encode((String) source);
        } catch (final ClassCastException cce) {
            throw newEncoderException(cce);
        }
    }


    /**
     * Encodes given {@code source}.
     *
     * @param source source
     *
     * @return encoded output
     *
     * @throws Throwable if an error occurs.
     */
    protected abstract String encode(final String source) throws Throwable;

}

