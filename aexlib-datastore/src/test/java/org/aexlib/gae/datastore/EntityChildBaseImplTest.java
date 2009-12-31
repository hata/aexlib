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
package org.aexlib.gae.datastore;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityChildNameBase;
import org.aexlib.gae.datastore.impl.TestChapter;
import org.aexlib.gae.datastore.impl.TestDocument;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityChildBaseImplTest extends LocalDataStoreTestCase {

    TestDocument doc;
    TestChapter chapter;

    protected void setUp() throws Exception {
        super.setUp();
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetParentKey() {
        chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter1");
        assertEquals(doc.getKey(), chapter.getParentKey());
    }

    public void testInitKey() throws Exception {
        chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter1");
        assertTrue(doc.getKey() == chapter.getParentKey());
        ((EntityChildNameBase)chapter).init(KeyFactory.createKey("TestDocument", "doc2"));
        assertTrue(doc.getKey() != chapter.getParentKey());
    }

    public void testInitEntity() {
        TestDocument doc2 = TestDocument.NAME_FACTORY.initInstance("doc2");
        doc2.putIfAbsent();
        chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter1");
        Entity entity = new Entity(KeyFactory.createKey(doc2.getKey(), "TestChapter", "chapter3"));
        assertEquals(doc.getKey(), chapter.getParentKey());
        assertEquals("chapter1", chapter.getKey().getName());
        ((EntityChildNameBase)chapter).init(entity);
        assertEquals("chapter3", chapter.getKey().getName());
        assertEquals(doc2.getKey(), chapter.getParentKey());
    }

}
