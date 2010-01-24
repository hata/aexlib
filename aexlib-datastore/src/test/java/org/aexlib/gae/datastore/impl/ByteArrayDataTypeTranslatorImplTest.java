/*
 * $Id$
 * 
 * Copyright 2010 Hiroki Ata
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
package org.aexlib.gae.datastore.impl;

import org.aexlib.gae.datastore.DataTypeTranslator;

import com.google.appengine.api.datastore.Blob;

import junit.framework.TestCase;

public class ByteArrayDataTypeTranslatorImplTest extends TestCase {
    private static final byte[] SAMPLE_BYTES = new byte[] {1,2,3};
    DataTypeTranslator translator;

    protected void setUp() throws Exception {
        super.setUp();
        translator = ByteArrayDataTypeTranslatorImpl.getInstance(byte[].class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        translator = null;
    }

    public void testGetInstance() {
        assertNotNull(ByteArrayDataTypeTranslatorImpl.getInstance(byte[].class));
        assertNull(ByteArrayDataTypeTranslatorImpl.getInstance(null));
        assertNull(ByteArrayDataTypeTranslatorImpl.getInstance(long[].class));
    }

    public void testToStoreType() {
        assertEquals(new Blob(SAMPLE_BYTES), translator.toStoreType(SAMPLE_BYTES));
        assertNull(translator.toStoreType(null));
    }

    public void testToUserType() {
        assertEquals(new String(SAMPLE_BYTES), new String((byte[])translator.toUserType(new Blob(SAMPLE_BYTES))));
        assertNull(translator.toUserType(null));
    }

}
