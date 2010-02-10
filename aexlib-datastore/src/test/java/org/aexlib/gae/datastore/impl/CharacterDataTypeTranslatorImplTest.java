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

import org.aexlib.gae.LocalDataStoreTestCase;

public class CharacterDataTypeTranslatorImplTest extends LocalDataStoreTestCase {
    CharacterDataTypeTranslatorImpl translator;

    protected void setUp() throws Exception {
        super.setUp();
        
        translator = new CharacterDataTypeTranslatorImpl();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        
        translator = null;
    }

    public void testGetInstance() {
        assertNull(CharacterDataTypeTranslatorImpl.getInstance(Long.class));
        assertNotNull(CharacterDataTypeTranslatorImpl.getInstance(Character.class));
    }

    public void testToStoreType() {
        assertNull(translator.toStoreType(null));
        assertEquals(Long.valueOf('\u0000'), translator.toStoreType(Character.valueOf('\u0000')));
        assertEquals(Long.valueOf('a'), translator.toStoreType(Character.valueOf('a')));
        assertEquals(Long.valueOf('\uffff'), translator.toStoreType(Character.valueOf('\uffff')));
    }

    public void testToUserType() {
        assertNull(translator.toUserType(null));
        assertEquals(Character.valueOf('\u0000'), translator.toUserType(Long.valueOf('\u0000')));
        assertEquals(Character.valueOf('a'), translator.toUserType(Long.valueOf('a')));
        assertEquals(Character.valueOf('\uffff'), translator.toUserType(Long.valueOf('\uffff')));
    }

}
