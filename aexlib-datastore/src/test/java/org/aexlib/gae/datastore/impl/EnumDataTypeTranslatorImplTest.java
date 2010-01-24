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

import junit.framework.TestCase;

public class EnumDataTypeTranslatorImplTest extends TestCase {
    
    enum TestEnum {
        test1,
        test2
    }

    DataTypeTranslator translator = EnumDataTypeTranslatorImpl.getInstance(TestEnum.class);

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testToStoreType() {
        assertEquals(TestEnum.test1.toString(), translator.toStoreType(TestEnum.test1));
    }

    public void testToUserType() {
        assertEquals(TestEnum.test1, translator.toUserType(TestEnum.test1.toString()));
    }

    public void testNullUserType() {
        assertNull(translator.toUserType(null));
    }

    public void testNullStoreType() {
        assertNull(translator.toStoreType(null));
    }
    
    public void testReturnNullForOtherClasses() {
        assertNull(EnumDataTypeTranslatorImpl.getInstance(String.class));
    }
}
