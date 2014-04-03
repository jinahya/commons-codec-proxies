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
 * Abstract class for proxies of {@link org.apache.commons.codec.StringDecoder}.
 *
 * @author <a href="mailto:jinahya@gmail.com">Jin Kwon</a>
 * @param <T> decoder type parameter.
 */
public abstract class StringDecoderProxy<T> extends DecoderProxy<T> {


    private static final String DECODER_NAME =
        "org.apache.commons.codec.StringDecoder";


    /**
     * the class of {@link org.apache.commons.codec.StringDecoder}.
     */
    private static final Class<?> DECODER;


    static {
        try {
            DECODER = Class.forName(DECODER_NAME);
        } catch (final ClassNotFoundException cnfe) {
            throw new InstantiationError(cnfe.getMessage());
        }
    }


    /**
     * the method of {@code decode(Ljava/lang/String;)Ljava/lang/String;}.
     */
    private static final Method DECODE;


    static {
        try {
            DECODE = DECODER.getMethod("decode", String.class);
        } catch (final NoSuchMethodException nsme) {
            throw new InstantiationError(nsme.getMessage());
        }
    }


    /**
     * Creates a new proxy instance.
     *
     * @param <P> proxy type parameter
     * @param <T> user decoder type parameter
     * @param proxyType proxy type
     * @param decoderType decoder type
     * @param decoder decoder
     *
     * @return a new instance.
     */
    protected static <P extends AbstractDecoderProxy<T>, T> Object newInstance(
        final Class<P> proxyType, final Class<T> decoderType, final T decoder) {

        if (proxyType == null) {
            throw new NullPointerException("proxyType");
        }

        if (!StringDecoderProxy.class.isAssignableFrom(proxyType)) {
            throw new IllegalArgumentException(
                "proxyType(" + proxyType + ") is not assignable to "
                + StringDecoderProxy.class);
        }

        return newInstance(DECODER.getClassLoader(), new Class<?>[]{DECODER},
                           proxyType, decoderType, decoder);
    }


    /**
     * Creates a new instance.
     *
     * @param decoder the decoder to use.
     */
    protected StringDecoderProxy(final T decoder) {

        super(decoder);
    }


    @Override
    public Object invoke(final Object proxy, final Method method,
                         final Object[] args)
        throws Throwable {

        if (DECODE.equals(method)) {
            return decode((String) args[0]);
        }

        return super.invoke(proxy, method, args);
    }


    @Override
    protected Object decode(final Object source) throws Throwable {

        try {
            return decode((String) source);
        } catch (final ClassCastException cce) {
            throw newDecoderException(cce);
        }
    }


    /**
     * Decodes given {@code source}.
     *
     * @param source source to decode
     *
     * @return decoded output
     *
     * @throws Throwable if an error occurs.
     */
    protected abstract String decode(final String source) throws Throwable;

}

