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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author <a href="mailto:jinahya@gmail.com">Jin Kwon</a>
 */
public class RareEncoder {


    /**
     * logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(RareEncoder.class);


    /**
     * Bypasses given {@code source}.
     *
     * @param source source to bypass
     *
     * @return given {@code source}.
     */
    public Object encode(final Object source) {

        LOGGER.debug("encode({})", source);

        if (source == null) {
            throw new NullPointerException("source");
        }

        return source;
    }

}

