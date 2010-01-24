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

import com.google.appengine.api.datastore.ShortBlob;

import junit.framework.TestCase;

public class IndexableByteArrayDataTypeTranslatorImplTest extends TestCase {

    DataTypeTranslator translator;

    protected void setUp() throws Exception {
        super.setUp();
        translator = IndexableByteArrayDataTypeTranslatorImpl.getInstance(byte[].class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetInstance() {
        assertNotNull(IndexableByteArrayDataTypeTranslatorImpl.getInstance(byte[].class));
        assertNull(IndexableByteArrayDataTypeTranslatorImpl.getInstance(null));
        assertNull(IndexableByteArrayDataTypeTranslatorImpl.getInstance(String.class));
    }

    public void testToStoreType() {
        assertEquals(new ShortBlob(new byte[]{1,2,3}), translator.toStoreType(new byte[]{1,2,3}));
        assertNull(translator.toStoreType(null));
    }

    public void testToUserType() throws Exception {
        assertEquals(new String(new byte[]{1,2,3}), new String((byte[])translator.toUserType(new ShortBlob(new byte[]{1,2,3}))));
        assertNull(translator.toUserType(null));
    }

}
