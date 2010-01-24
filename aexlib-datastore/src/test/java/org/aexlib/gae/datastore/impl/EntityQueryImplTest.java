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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityResultIterable;
import org.aexlib.gae.datastore.EntityResultIterator;
import org.aexlib.gae.datastore.EntityResultList;
import org.aexlib.gae.datastore.impl.EntityQueryImpl;

import com.google.appengine.api.datastore.Cursor;


public class EntityQueryImplTest extends LocalDataStoreTestCase {

    TestDocument[] docs;
    
    EntityQueryImpl<TestDocument> query;

    protected void setUp() throws Exception {
        super.setUp();
        query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query();
        
        Set<TestDocument.Status> statusSet = new HashSet<TestDocument.Status>();
        statusSet.add(TestDocument.Status.EDIT);
        statusSet.add(TestDocument.Status.PUBLISH);
        
        
        docs = new TestDocument[5];
        for (int i = 0;i < docs.length;i++) {
            docs[i] = TestDocument.NAME_FACTORY.initInstance("doc" + i);
            docs[i].putIfAbsent();
            docs[i].title.set("title" + i);
            docs[i].status.set(TestDocument.Status.EDIT);
            docs[i].statusSet.set(statusSet);
            docs[i].put();
        }
    }

    protected void tearDown() throws Exception {
        query = null;
        super.tearDown();
    }

    public void testFilterEqual() {
        for (int i = 0;i < docs.length;i++) {
            query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query().filter(TestDocument.TITLE.equal("title" + i));
            assertEquals(docs[i].getKey(), query.asSingleEntity().getKey());
        }
    }

    public void testFilterLessThan() {
        query = (EntityQueryImpl<TestDocument>) query.filter(TestDocument.TITLE.lessThan("title2")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[0].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[1].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }
    
    public void testFilterLessThanOrEqual() {
        query = (EntityQueryImpl<TestDocument>) query.filter(TestDocument.TITLE.lessThanOrEqual("title2")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[0].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[1].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[2].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }

    public void testFilterGreaterThan() {
        query = (EntityQueryImpl<TestDocument>) query.filter(TestDocument.TITLE.greaterThan("title2")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[3].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[4].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }
    
    public void testFilterGreaterThanOrEqual() {
        query = (EntityQueryImpl<TestDocument>) query.filter(TestDocument.TITLE.greaterThanOrEqual("title2")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[2].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[3].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[4].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }

    public void testFilterBetween() {
        query = (EntityQueryImpl<TestDocument>) query.filter(TestDocument.TITLE.greaterThan("title1").lessThan("title4")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[2].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[3].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }

    public void testSort() {
        int i = 4;
        for (TestDocument doc : query.sort(TestDocument.TITLE.desc()).asIterable()) {
            assertEquals(docs[i--].getKey(), doc.getKey());
        }
        assertEquals(-1, i);
    }

    public void testAsIterable() {
        int i = 0;
        for (TestDocument doc : query.sort(TestDocument.TITLE.asc()).asIterable()) {
            assertEquals(docs[i++].getKey(), doc.getKey());
        }
        assertEquals(5, i);
    }

    public void testAsIterableLimit() {
        int i = 0;
        for (TestDocument doc : query.sort(TestDocument.TITLE.asc()).limit(3).asIterable()) {
            assertEquals(docs[i++].getKey(), doc.getKey());
        }
        assertEquals(3, i);
    }

    public void testAsIterableLimitOffset() {
        int i = 2;
        for (TestDocument doc : query.sort(TestDocument.TITLE.asc()).limit(3).offset(2).asIterable()) {
            assertEquals(docs[i++].getKey(), doc.getKey());
        }
        assertEquals(5, i);
    }

    public void testAsIterator() {
        int i = 0;
        Iterator<TestDocument> docIt = query.sort(TestDocument.TITLE.asc()).asIterator();
        while (docIt.hasNext()) {
            assertEquals(docs[i++].getKey(), docIt.next().getKey());
        }
        assertEquals(5, i);
    }

    public void testAsIteratorLimit() {
        int i = 0;
        Iterator<TestDocument> docIt = query.sort(TestDocument.TITLE.asc()).limit(2).asIterator();
        while (docIt.hasNext()) {
            assertEquals(docs[i++].getKey(), docIt.next().getKey());
        }
        assertEquals(2, i);
    }

    public void testAsIteratorLimitOffset() {
        int i = 3;
        Iterator<TestDocument> docIt = query.sort(TestDocument.TITLE.asc()).limit(2).offset(3).asIterator();
        while (docIt.hasNext()) {
            assertEquals(docs[i++].getKey(), docIt.next().getKey());
        }
        assertEquals(5, i);
    }

    public void testAsSingleEntity() throws Exception {
        query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query().
            filter(TestDocument.TITLE.greaterThanOrEqual("title3").lessThanOrEqual("title3"));
        assertEquals(docs[3].getKey(), query.asSingleEntity().getKey());
    }

    public void testCountEntities() {
        for (int i = 0;i < docs.length;i++) {
            query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query().filter(TestDocument.TITLE.greaterThan("title" + i));
            assertEquals(4 - i, query.countEntities());
        }
    }
    
    public void testSetKeysOnly() throws Exception {
        int i = 0;
        for (TestDocument doc : TestDocument.QUERY.query().sort(TestDocument.TITLE.asc()).setKeysOnly().asIterable()) {
            assertEquals("title" + (i++), doc.title.get());
        }
    }
    
    public void testIterableCursor() throws Exception {
        Cursor cursor = null;

        EntityResultIterable<TestDocument> iterable = TestDocument.QUERY.resultQuery().sort(TestDocument.TITLE.asc()).asIterable();
        EntityResultIterator<TestDocument> iterator = iterable.iterator();

        for (int i = 0;i < docs.length;i++) {
            while (iterator.hasNext()) {
                TestDocument doc = iterator.next();
                assertEquals("title" + i, doc.title.get());
                cursor = iterator.getCursor();
                break;
            }
            cursor = Cursor.fromWebSafeString(cursor.toWebSafeString());
            iterable = TestDocument.QUERY.resultQuery().sort(TestDocument.TITLE.asc()).cursor(cursor).asIterable();
            iterator = iterable.iterator();
        }
    }

    // TODO: Is this ok ?
    // If I set offset(1), it works ...
    // Maybe, QueryResultList.getCursor() returns the first element or last element -1,
    // and offset is the place from it...
    public void test_DOUTFUL_ListCursor() throws Exception {
        Cursor cursor = null;

        EntityResultList<TestDocument> list = TestDocument.QUERY.resultQuery().sort(TestDocument.TITLE.asc()).offset(0).limit(1).asList();
        for (int i = 0;i < docs.length;i++) {
            Iterator<TestDocument> it = list.iterator();
            while (it.hasNext()) {
                assertEquals("title" + i, it.next().title.get());
            }
            cursor = list.getCursor();
            cursor = Cursor.fromWebSafeString(cursor.toWebSafeString());
            list = TestDocument.QUERY.resultQuery().sort(TestDocument.TITLE.asc()).offset(1).limit(1).cursor(cursor).asList();
        }
    }
    
    public void testEnumProperty() {
        query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query().filter(TestDocument.STATUS.equal(TestDocument.Status.EDIT));
        assertEquals(docs.length, query.countEntities());
    }

    public void testEnumSetProperty() {
        query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query().filter(TestDocument.STATUS_SET.equal(TestDocument.Status.EDIT));
        assertEquals(docs.length, query.countEntities());
    }

    public void testEnumSetPropertyNotFound() {
        query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query().filter(TestDocument.STATUS_SET.equal(TestDocument.Status.REVIEW));
        assertEquals(0, query.countEntities());
    }
}
