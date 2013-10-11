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
 * Abstract class for encoder proxies.
 *
 * @author Jin Kwon <jinahya at gmail.com>
 * @param <T> encoder (delegate) type parameter.
 */
public abstract class AbstractEncoderProxy<T> implements InvocationHandler {


    private static final Logger LOGGER =
        LoggerFactory.getLogger(AbstractEncoderProxy.class);


    private static final String ENCODER_EXCEPTION_NAME =
        "org.apache.commons.codec.EncoderException";


    /**
     * the class for {@link org.apache.commons.codec.EncoderException}.
     */
    protected static final Class<? extends Throwable> ENCODER_EXCEPTION;


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
//            loaded = classLoader.loadClass(ENCODER_EXCEPTION_NAME);
//        } catch (final ClassNotFoundException cnfe) {
//            throw new InstantiationError(cnfe.getMessage());
//        }
        final Class<?> named;
        try {
            named = Class.forName(ENCODER_EXCEPTION_NAME);
        } catch (final ClassNotFoundException cnfe) {
            throw new InstantiationError(cnfe.getMessage());
        }
        ENCODER_EXCEPTION = named.asSubclass(Throwable.class);
    }


    /**
     * Creates a new instance of
     * {@link org.apache.commons.codec.EncoderException}.
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.EncoderException}.
     */
    protected static Throwable newEncoderException() {

        try {
            return ENCODER_EXCEPTION.newInstance();
        } catch (final InstantiationException ie) {
            throw new RuntimeException(ie);
        } catch (final IllegalAccessException iae) {
            throw new RuntimeException(iae);
        }
    }


    /**
     * Creates a new instance of
     * {@link org.apache.commons.codec.EncoderException} with given message.
     *
     * @param message the message
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.EncoderException}
     */
    protected static Throwable newEncoderException(final String message) {

        try {
            return ENCODER_EXCEPTION.getConstructor(String.class)
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
     * {@link org.apache.commons.codec.EncoderException} with given
     * {@code cause}.
     *
     * @param cause the cause
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.EncoderException}
     */
    protected static Throwable newEncoderException(final Throwable cause) {

        try {
            return ENCODER_EXCEPTION.getConstructor(Throwable.class)
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
     * Creates a new instance of
     * {@link org.apache.commons.codec.EncoderException} with given
     * {@code message} and {@code cause}.
     *
     * @param message the message
     * @param cause the cause
     *
     * @return a new instance of
     * {@link org.apache.commons.codec.EncoderException}
     */
    protected static Throwable newEncoderException(final String message,
                                                   final Throwable cause) {

        try {
            return ENCODER_EXCEPTION
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
     * Creates a new proxy instance.
     *
     * @param <P> proxy type parameter
     * @param <T> encoder type parameter
     * @param loader class loader
     * @param interfaces interfaces
     * @param proxyType proxy type
     * @param encoderType encoder type
     * @param encoder encoder
     *
     * @return a new proxy instance.
     */
    protected static <P extends AbstractEncoderProxy<T>, T> Object newInstance(
        final ClassLoader loader, final Class<?>[] interfaces,
        final Class<P> proxyType, final Class<T> encoderType, final T encoder) {

        if (loader == null) {
            throw new NullPointerException("loader");
        }

        if (interfaces == null) {
            throw new NullPointerException("interfaces");
        }

        if (proxyType == null) {
            throw new NullPointerException("proxyType");
        }

        if (encoderType == null) {
            throw new NullPointerException("encoderType");
        }

        if (encoder == null) {
            //throw new NullPointerException("encoder");
        }

        try {
            final Constructor<P> constructor =
                proxyType.getDeclaredConstructor(encoderType);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            try {
                return Proxy.newProxyInstance(
                    loader, interfaces, constructor.newInstance(encoder));
            } catch (final IllegalAccessException iae) {
                throw new RuntimeException(iae);
            } catch (final InstantiationException ie) {
                throw new RuntimeException(ie);
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
     * @param encoder the delegate. Maybe {@code null}.
     */
    protected AbstractEncoderProxy(final T encoder) {

        super();

        if (encoder == null) {
            //throw new NullPointerException("encoder");
        }

        this.encoder = encoder;
    }


    /**
     * The encoder instance passed in constructor. Maybe {@code null}.
     */
    protected T encoder;


}

