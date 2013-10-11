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


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract class for decoder proxies.
 *
 * @author Jin Kwon <jinahya at gmail.com>
 * @param <T> decoder(delegate) type parameter.
 */
public abstract class AbstractDecoderProxy<T> implements InvocationHandler {


    /**
     * logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(AbstractDecoderProxy.class);


    private static final String DECODER_EXCEPTION_NAME =
        "org.apache.commons.codec.DecoderException";


    /**
     * Class for {@link org.apache.commons.codec.DecoderException}.
     */
    protected static final Class<? extends Throwable> DECODER_EXCEPTION;


    static {
//        final Class<?> loaded;
//        try {
//            final Thread currentThread = Thread.currentThread();
//            LOGGER.debug("currentThread: {}", currentThread);
//            ClassLoader classLoader = currentThread.getContextClassLoader();
//            if (classLoader == null) {
//                classLoader = AbstractDecoderProxy.class.getClassLoader();
//            }
//            LOGGER.debug("classLoader: {}", classLoader);
//            loaded = classLoader.loadClass(DECODER_EXCEPTION_NAME);
//        } catch (final ClassNotFoundException cnfe) {
//            throw new InstantiationError(cnfe.getMessage());
//        }
        final Class<?> named;
        try {
            named = Class.forName(DECODER_EXCEPTION_NAME);
        } catch (final ClassNotFoundException cnfe) {
            throw new InstantiationError(cnfe.getMessage());
        }
        DECODER_EXCEPTION = named.asSubclass(Throwable.class);
    }


    /**
     * Creates a new instance of
     * {@link org.apache.commons.codec.DecoderException}.
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.DecoderException}.
     */
    protected static Throwable newDecoderException() {

        try {
            return DECODER_EXCEPTION.newInstance();
        } catch (final InstantiationException ie) {
            throw new RuntimeException(ie);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException(iae);
        }
    }


    /**
     * Creates a new instance of
     * {@link org.apache.commons.codec.DecoderException} with given
     * {@code message}.
     *
     * @param message message
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.DecoderException}.
     */
    protected static Throwable newDecoderException(final String message) {

        try {
            return DECODER_EXCEPTION.getConstructor(String.class)
                .newInstance(message);
        } catch (final NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        } catch (final InstantiationException ie) {
            throw new RuntimeException(ie);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException(iae);
        } catch (final InvocationTargetException ite) {
            throw new RuntimeException(ite);
        }
    }


    /**
     * Creates a new instance of
     * {@link org.apache.commons.codec.DecoderException} with given
     * {@code message} and {@code cause}.
     *
     * @param message message
     * @param cause cause
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.DecoderException}.
     */
    protected static Throwable newDecoderException(final String message,
                                                   final Throwable cause) {

        try {
            return DECODER_EXCEPTION
                .getConstructor(String.class, Throwable.class)
                .newInstance(message, cause);
        } catch (final NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        } catch (final InstantiationException ie) {
            throw new RuntimeException(ie);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException(iae);
        } catch (final InvocationTargetException ite) {
            throw new RuntimeException(ite);
        }
    }


    /**
     * Creates a new instance of
     * {@link org.apache.commons.codec.DecoderException} with given
     * {@code cause}.
     *
     * @param cause cause
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.DecoderException}.
     */
    protected static Throwable newDecoderException(final Throwable cause) {

        try {
            return DECODER_EXCEPTION.getConstructor(Throwable.class)
                .newInstance(cause);
        } catch (final NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        } catch (final InstantiationException ie) {
            throw new RuntimeException(ie);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException(iae);
        } catch (final InvocationTargetException ite) {
            throw new RuntimeException(ite);
        }
    }


    /**
     * Creates a new proxy instance.
     *
     * @param <P> proxy type parameter
     * @param <T> decoder type parameter
     * @param loader class loader
     * @param interfaces interfaces
     * @param proxyType proxy type
     * @param decoderType decoder type
     * @param decoder decoder
     *
     * @return a new proxy instance.
     */
    protected static <P extends AbstractDecoderProxy<T>, T> Object newInstance(
        final ClassLoader loader, final Class<?>[] interfaces,
        final Class<P> proxyType, final Class<T> decoderType, final T decoder) {

        if (loader == null) {
            throw new NullPointerException("loader");
        }

        if (interfaces == null) {
            throw new NullPointerException("interfaces");
        }

        if (proxyType == null) {
            throw new NullPointerException("proxyType");
        }

        if (decoderType == null) {
            throw new NullPointerException("decoderType");
        }

        if (decoder == null) {
            //throw new NullPointerException("decoder");
        }

        try {
            final Constructor<P> constructor =
                proxyType.getDeclaredConstructor(decoderType);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            try {
                return Proxy.newProxyInstance(
                    loader, interfaces, constructor.newInstance(decoder));
            } catch (final InstantiationException ie) {
                throw new RuntimeException(ie);
            } catch (final IllegalAccessException iae) {
                throw new RuntimeException(iae);
            } catch (final InvocationTargetException ite) {
                throw new RuntimeException(ite);
            }
        } catch (final NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        }
    }


    /**
     * Creates a new instance.
     *
     * @param decoder the decoder to use. Maybe {@code null}.
     */
    protected AbstractDecoderProxy(final T decoder) {

        super();

        if (decoder == null) {
            //throw new NullPointerException("decoder");
        }

        this.decoder = decoder;
    }


    /**
     * The decoder instance passed in constructor. Maybe {@code null}.
     */
    protected T decoder;


}

