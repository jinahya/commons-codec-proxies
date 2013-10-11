/*
 * Copyright 2013 Jin Kwon <jinahya at gmail.com>.
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


import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Encoder;
import org.apache.commons.codec.EncoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon <jinahya at gmail.com>
 */
public class RareBinaryEncoderProxyTest {


    /**
     * logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(RareBinaryEncoderProxyTest.class);


    @Test
    public void testAsEncoder() throws EncoderException {

        final Encoder encoder =
            (Encoder) RareBinaryEncoderProxy.newInstance();

        try {
            encoder.encode(null);
            Assert.fail("passed: <Object>encode(null)");
        } catch (final NullPointerException npe) {
            // expected
        }

        final Object expected = new byte[0];
        final Object actual = encoder.encode(expected);
        Assert.assertEquals(actual, expected);
    }


    @Test
    public void testAsBinaryEncoder() throws EncoderException {

        final BinaryEncoder encoder =
            (BinaryEncoder) RareBinaryEncoderProxy.newInstance();

        try {
            encoder.encode((byte[]) null);
            Assert.fail("passed: encode((byte[]) null)");
        } catch (final NullPointerException npe) {
            // expected
        }

        final byte[] expected = new byte[0];
        final byte[] actual = encoder.encode(expected);
        Assert.assertEquals(actual, expected);
    }


}
