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
package com.archtea.gae.datastore.impl;

import java.util.HashSet;
import java.util.Iterator;

import com.archtea.gae.LocalDataStoreTestCase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class TransactionManagerImplTest extends LocalDataStoreTestCase {

    TransactionManagerImpl manager;
    TestDocument doc;

    protected void setUp() throws Exception {
        super.setUp();
        manager = TransactionManagerImpl.getInstance();
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBeginAndCommit() throws Exception {
        manager.begin();
        doc.title.set("title1");
        doc.put();
        manager.commit();
        assertEquals("title1", TestDocument.NAME_FACTORY.initInstance("doc1").title.get());
    }

    public void testRollback() throws Exception {
        manager.begin();
        doc.title.set("title1");
        doc.put();
        manager.rollback();
        assertNull(TestDocument.NAME_FACTORY.initInstance("doc1").title.get());
    }

    public void testRollbackIfActive() throws Exception {
        manager.begin();
        doc.title.set("title1");
        doc.put();
        manager.rollbackIfActive();
        assertNull(TestDocument.NAME_FACTORY.initInstance("doc1").title.get());

        manager.begin();
        doc.title.set("title1");
        doc.put();
        manager.commit();
        manager.rollbackIfActive();
        assertEquals("title1", TestDocument.NAME_FACTORY.initInstance("doc1").title.get());
    }

    public void COMMENT_OUT_BECAUSE_OF_A_BUG_testPutIterableOfEntityCommit() throws Exception {
        HashSet<Entity> set = new HashSet<Entity>();
        set.add(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter1")));
        set.add(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter2")));

        manager.begin();
        manager.put(set);
        // TODO: Maybe, a bug ?
// Workaround.
//        Iterator<Entity> it = set.iterator();
//        manager.put(it.next());
//        manager.put(it.next());
        manager.commit();
        
        assertFalse(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1").putIfAbsent());
        assertFalse(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2").putIfAbsent());
    }

    public void testPutIterableOfEntityRollback() throws Exception {
        HashSet<Entity> set = new HashSet<Entity>();
        set.add(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter1")));
        set.add(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter2")));

        manager.begin();
        manager.put(set);
        manager.rollbackIfActive();
        
        assertTrue(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1").putIfAbsent());
        assertTrue(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2").putIfAbsent());
    }

    public void testPutEntityCommit() throws Exception {
        manager.begin();
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter1")));
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter2")));
        manager.commit();
        
        assertFalse(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1").putIfAbsent());
        assertFalse(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2").putIfAbsent());
    }

    public void testPutEntityRollback() throws Exception {
        manager.begin();
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter1")));
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter2")));
        manager.rollback();
        
        assertTrue(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1").putIfAbsent());
        assertTrue(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2").putIfAbsent());
    }

    // TODO:
    public void COMMENT_OUT_BECAUSE_OF_A_BUG_testDeleteIterableOfKey() {
        manager.begin();
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter1")));
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter2")));
        manager.commit();

        HashSet<Key> keys = new HashSet<Key>();
        keys.add(doc.getKey());
        keys.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1").getKey());
        keys.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2").getKey());
        manager.begin();
        manager.delete(keys);
        manager.commit();
    }

    // TODO: 
    public void COMMENT_OUT_BECAUSE_OF_A_BUG_testDeleteKeyArray() {
        manager.begin();
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter1")));
        manager.put(new Entity(KeyFactory.createKey(doc.getKey(), "TestChapter", "chapter2")));
        manager.commit();

        HashSet<Key> keys = new HashSet<Key>();
        keys.add(doc.getKey());
        keys.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1").getKey());
        keys.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2").getKey());
        manager.begin();
        manager.delete(keys.toArray(new Key[keys.size()]));
        manager.commit();
    }

}
