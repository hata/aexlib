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


public class EntityChildIdFactoryImplTest extends LocalDataStoreTestCase {

    TestDocument doc;

    protected void setUp() throws Exception {
        super.setUp();
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        doc = null;
        super.tearDown();
    }

    public void testGetEntity() {
        TestIdPage page = TestIdPage.ID_FACTORY.initInstance(doc);
        assertNotNull(page);
        assertNull(page.getKey());
        page.putIfAbsent();
        assertNotNull(page.getKey());
        assertEquals(EntityBase.getKindName(TestIdPage.class), page.getKey().getKind());
        assertEquals(doc.getKey(), page.getKey().getParent());
    }

    public void testGetEntityWithID() {
        TestIdPage page = TestIdPage.ID_FACTORY.initInstance(doc, 10);
        assertNotNull(page);
        assertNotNull(page.getKey());
        assertTrue(page.putIfAbsent());
        assertEquals(10, page.getKey().getId());

        page = TestIdPage.ID_FACTORY.initInstance(doc, 10);
        assertNotNull(page);
        assertNotNull(page.getKey());
        assertFalse(page.putIfAbsent());
        assertEquals(10, page.getKey().getId());
    }
    
    public void testInitKeyAndPut() {
        TestIdPage page = TestIdPage.ID_FACTORY.initInstance(KeyFactory.createKey(doc.getKey(), TestIdPage.getKindName(TestIdPage.class), 5L));
        page.put();
        assertEquals(5L, page.getKey().getId());
    }
}
