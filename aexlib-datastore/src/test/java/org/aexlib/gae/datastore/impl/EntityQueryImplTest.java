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

import java.util.Iterator;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityQueryFetchOptions;
import org.aexlib.gae.datastore.impl.EntityQueryImpl;


public class EntityQueryImplTest extends LocalDataStoreTestCase {

    TestDocument[] docs;
    
    EntityQueryImpl<TestDocument> query;

    protected void setUp() throws Exception {
        super.setUp();
        query = (EntityQueryImpl<TestDocument>)TestDocument.QUERY.query();
        
        docs = new TestDocument[5];
        for (int i = 0;i < docs.length;i++) {
            docs[i] = TestDocument.NAME_FACTORY.initInstance("doc" + i);
            docs[i].putIfAbsent();
            docs[i].title.set("title" + i);
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
        query = query.filter(TestDocument.TITLE.lessThan("title2")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[0].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[1].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }
    
    public void testFilterLessThanOrEqual() {
        query = query.filter(TestDocument.TITLE.lessThanOrEqual("title2")).sort(TestDocument.TITLE.asc());
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
        query = query.filter(TestDocument.TITLE.greaterThan("title2")).sort(TestDocument.TITLE.asc());
        Iterator<TestDocument> docIt = query.asIterable().iterator();
        assertTrue(docIt.hasNext());
        assertEquals(docs[3].getKey(), docIt.next().getKey());
        assertTrue(docIt.hasNext());
        assertEquals(docs[4].getKey(), docIt.next().getKey());
        assertFalse(docIt.hasNext());
    }
    
    public void testFilterGreaterThanOrEqual() {
        query = query.filter(TestDocument.TITLE.greaterThanOrEqual("title2")).sort(TestDocument.TITLE.asc());
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
        query = query.filter(TestDocument.TITLE.greaterThan("title1").lessThan("title4")).sort(TestDocument.TITLE.asc());
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
        for (TestDocument doc : query.sort(TestDocument.TITLE.asc()).asIterable(EntityQueryFetchOptions.Factory.withLimit(3))) {
            assertEquals(docs[i++].getKey(), doc.getKey());
        }
        assertEquals(3, i);
    }

    public void testAsIterableLimitOffset() {
        int i = 2;
        for (TestDocument doc : query.sort(TestDocument.TITLE.asc()).asIterable(EntityQueryFetchOptions.Factory.withLimit(3).offset(2))) {
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
        Iterator<TestDocument> docIt = query.sort(TestDocument.TITLE.asc()).asIterator(EntityQueryFetchOptions.Factory.withLimit(2));
        while (docIt.hasNext()) {
            assertEquals(docs[i++].getKey(), docIt.next().getKey());
        }
        assertEquals(2, i);
    }

    public void testAsIteratorLimitOffset() {
        int i = 3;
        Iterator<TestDocument> docIt = query.sort(TestDocument.TITLE.asc()).asIterator(EntityQueryFetchOptions.Factory.withLimit(2).offset(3));
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
}
