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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.aexlib.gae.LocalDataStoreTestCase;


public class KeyLinkPropertyTest extends LocalDataStoreTestCase {

    TestDocument doc;
    TestIdPage page;

    protected void setUp() throws Exception {
        super.setUp();
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();

        page = TestIdPage.ID_FACTORY.initInstance(doc);
        page.putIfAbsent();

        doc.page.set(page);
        
        List<TestChapter> chapters = new ArrayList<TestChapter>();
        chapters.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter1"));
        chapters.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter2"));
        chapters.add(TestChapter.NAME_FACTORY.initInstance(doc, "chapter3"));
        doc.chapters.set(chapters);
        
        doc.put();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testKeyLink() throws Exception {
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        assertEquals(page.getKey(), doc.page.get().getKey());
    }
    
    public void testKeyLinkQuery() throws Exception {
        int i = 0;
        for (TestDocument doc : TestDocument.QUERY.query().filter(TestDocument.PAGE.equal(page))) {
            assertEquals(page.getKey(), doc.page.get().getKey());
            i++;
        }
        assertEquals(1, i);
    }
    
    public void testKeyLinkCollection() throws Exception {
        List<TestChapter> list = doc.chapters.get();
        assertEquals(3, list.size());
        for (int i = 0;i < list.size();i++) {
            assertEquals("chapter" + (i + 1), list.get(i).getKey().getName());
        }
    }

    public void testKeyLinkCollectionQuery() throws Exception {
        int i = 0;
        TestChapter chapter2 = TestChapter.NAME_FACTORY.initInstance(doc, "chapter2");
        for (TestDocument doc : TestDocument.QUERY.query().filter(TestDocument.CHAPTERS.equal(chapter2))) {
            i++;
        }
        assertEquals(1, i);
    }
    
    public void testKeyLinkCollectionCollection() throws Exception {
        Collection<TestChapter> set = new ArrayList<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterCollection.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Collection<TestChapter> newSet = collection.chapterCollection.get();
        assertEquals(3, newSet.size());
    }

    public void testKeyLinkCollectionSet() throws Exception {
        HashSet<TestChapter> set = new HashSet<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Set<TestChapter> newSet = collection.chapterSet.get();
        assertEquals(3, newSet.size());
    }

    public void testKeyLinkCollectionSortSet() throws Exception {
        TreeSet<TestChapter> set = new TreeSet<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterSortedSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        SortedSet<TestChapter> newSet = collection.chapterSortedSet.get();
        assertEquals(3, newSet.size());
    }

    public void testKeyLinkCollectionList() throws Exception {
        ArrayList<TestChapter> set = new ArrayList<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterList.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        List<TestChapter> newList = collection.chapterList.get();
        assertEquals(3, newList.size());
    }

    public void testKeyLinkCollectionArrayList() throws Exception {
        ArrayList<TestChapter> set = new ArrayList<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterArrayList.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        ArrayList<TestChapter> newList = collection.chapterArrayList.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }

    public void testKeyLinkCollectionLinkedList() throws Exception {
        LinkedList<TestChapter> set = new LinkedList<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterLinkedList.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        LinkedList<TestChapter> newList = collection.chapterLinkedList.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }

    public void testKeyLinkCollectionHashSet() throws Exception {
        HashSet<TestChapter> set = new HashSet<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterHashSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        HashSet<TestChapter> newList = collection.chapterHashSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }

    public void testKeyLinkCollectionLinkedHashSet() throws Exception {
        LinkedHashSet<TestChapter> set = new LinkedHashSet<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterLinkedHashSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        LinkedHashSet<TestChapter> newList = collection.chapterLinkedHashSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }

    public void testKeyLinkCollectionStack() throws Exception {
        Stack<TestChapter> set = new Stack<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterStack.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Stack<TestChapter> newList = collection.chapterStack.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }

    public void testKeyLinkCollectionTreeSet() throws Exception {
        TreeSet<TestChapter> set = new TreeSet<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterTreeSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        TreeSet<TestChapter> newList = collection.chapterTreeSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }

    public void testKeyLinkCollectionVector() throws Exception {
        Vector<TestChapter> set = new Vector<TestChapter>();
        for (int i = 0;i < 3;i++) {
            TestChapter chapter = TestChapter.NAME_FACTORY.initInstance(doc, "chapter" + i);
            assertTrue(chapter.putIfAbsent());
            set.add(chapter);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.chapterVector.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Vector<TestChapter> newList = collection.chapterVector.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }
}
