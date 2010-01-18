/*
 * $Id$
 * 
 * Copyright 2009 Hiroki Ata
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
import org.aexlib.gae.datastore.EntityBase;

import com.google.appengine.api.datastore.KeyFactory;


public class EntityIdFactoryImplTest extends LocalDataStoreTestCase {

    TestEntry doc;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetEntity() {
        doc = TestEntry.ID_FACTORY.initInstance();
        assertNotNull(doc);
        assertNull(doc.getKey());
        assertTrue(doc.putIfAbsent());
        assertNotNull(doc.getKey());
        assertEquals(EntityBase.getKindName(TestEntry.class), doc.getKey().getKind());
    }

    public void testGetEntityWithId() {
        doc = TestEntry.ID_FACTORY.initInstance(10);
        assertNotNull(doc);
        assertNotNull(doc.getKey());
        assertEquals(10, doc.getKey().getId());
        assertTrue(doc.putIfAbsent());
        assertNotNull(doc.getKey());
        assertEquals(EntityBase.getKindName(TestEntry.class), doc.getKey().getKind());
        assertEquals(10, doc.getKey().getId());

        doc = TestEntry.ID_FACTORY.initInstance(10);
        assertNotNull(doc);
        assertNotNull(doc.getKey());
        assertEquals(10, doc.getKey().getId());
        assertFalse(doc.putIfAbsent());
        assertNotNull(doc.getKey());
        assertEquals(EntityBase.getKindName(TestEntry.class), doc.getKey().getKind());
        assertEquals(10, doc.getKey().getId());
    }
    
    public void testInitKeyAndPut() {
        doc = TestEntry.ID_FACTORY.initInstance(KeyFactory.createKey(TestEntry.getKindName(TestEntry.class), 10L));
        doc.put();
        assertEquals(10L, doc.getKey().getId());
    }
}
