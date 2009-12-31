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

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityCollectionProperty;
import org.aexlib.gae.datastore.EntityCollectionPropertyInfo;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.IMHandle;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class EntityCollectionPropertyTest extends LocalDataStoreTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testStringCollection() throws Exception {
        Collection<String> set = new ArrayList<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringCollection.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Collection<String> newList = collection.stringCollection.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testStringList() throws Exception {
        List<String> set = new ArrayList<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringList.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        List<String> newList = collection.stringList.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testStringSet() throws Exception {
        Set<String> set = new HashSet<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Set<String> newList = collection.stringSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testStringSortedSet() throws Exception {
        SortedSet<String> set = new TreeSet<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringSortedSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        SortedSet<String> newList = collection.stringSortedSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }
    
    public void testArrayList() throws Exception {
        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringArrayList.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        ArrayList<String> newList = collection.stringArrayList.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testLinkedList() throws Exception {
        LinkedList<String> set = new LinkedList<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringLinkedList.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        LinkedList<String> newList = collection.stringLinkedList.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }
    
    public void testStringHashSet() throws Exception {
        HashSet<String> set = new HashSet<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringHashSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        HashSet<String> newList = collection.stringHashSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testStringLinkedHashSet() throws Exception {
        LinkedHashSet<String> set = new LinkedHashSet<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringLinkedHashSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        LinkedHashSet<String> newList = collection.stringLinkedHashSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
    }
    
    public void testStringStack() throws Exception {
        Stack<String> set = new Stack<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringStack.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Stack<String> newList = collection.stringStack.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testStringTreeSet() throws Exception {
        TreeSet<String> set = new TreeSet<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringTreeSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        TreeSet<String> newList = collection.stringTreeSet.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    public void testStringVector() throws Exception {
        Vector<String> set = new Vector<String>();
        for (int i = 0;i < 3;i++) {
            set.add("string" + i);
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringVector.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Vector<String> newList = collection.stringVector.get();
        assertEquals(3, newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    
    public void testStandardCollections() throws Exception {
        Map<Class<?>, Collection<?>> map = prepareCollectionData();
        
        for (Map.Entry<Class<?>, Collection<?>> entry : map.entrySet()) {
            verifyCollections(ArrayList.class, entry.getKey(), new ArrayList(entry.getValue()));
            verifyCollections(List.class, entry.getKey(), new ArrayList(entry.getValue()));
            verifyCollections(LinkedList.class, entry.getKey(), new LinkedList(entry.getValue()));
            verifyCollections(HashSet.class, entry.getKey(), new HashSet(entry.getValue()));
            verifyCollections(Set.class, entry.getKey(), new HashSet(entry.getValue()));
            verifyCollections(LinkedHashSet.class, entry.getKey(), new LinkedHashSet(entry.getValue()));
            verifyCollections(Vector.class, entry.getKey(), new Vector(entry.getValue()));

            if (!entry.getKey().equals(Blob.class) && !entry.getKey().equals(Text.class)) {
                verifyCollections(TreeSet.class, entry.getKey(), new TreeSet(entry.getValue()));
                verifyCollections(SortedSet.class, entry.getKey(), new TreeSet(entry.getValue()));
            }

            Stack stack = new Stack();
            stack.addAll(entry.getValue());
            verifyCollections(Stack.class, entry.getKey(), stack);
        }
    }

    public Map<Class<?>, Collection<?>> prepareCollectionData() throws Exception {
        Map<Class<?>, Collection<?>> dataMap = new HashMap<Class<?>, Collection<?>>();

        dataMap.put(Byte.class, getCollection((byte)0, (byte)1, Byte.MAX_VALUE, Byte.MIN_VALUE));
        dataMap.put(Short.class, getCollection((short)0, (short)1, Short.MAX_VALUE, Short.MIN_VALUE));
        dataMap.put(Integer.class, getCollection((int)0, (int)1, Integer.MAX_VALUE, Integer.MIN_VALUE));
        dataMap.put(Long.class, getCollection((long)0L, (long)1L, Long.MAX_VALUE, Long.MIN_VALUE));
        dataMap.put(Float.class, getCollection((float)0f, (float)1f, Float.MAX_VALUE, Float.MIN_VALUE));
        dataMap.put(Double.class, getCollection((double)0d, (double)1d, Double.MAX_VALUE, Double.MIN_VALUE));
        dataMap.put(String.class, getCollection("", "abc", "123"));
        dataMap.put(Date.class, getCollection(new Date()));
        dataMap.put(Text.class, getCollection(new Text(""), new Text("abc"), new Text("123")));
        dataMap.put(ShortBlob.class, getCollection(new ShortBlob("".getBytes()), new ShortBlob("abc".getBytes())));
        dataMap.put(Blob.class, getCollection(new Blob("".getBytes()), new Blob("abc".getBytes())));
        dataMap.put(Key.class, getCollection(KeyFactory.createKey("testKind", "key1"), KeyFactory.createKey("testKind", "key2")));
        dataMap.put(User.class, getCollection(UserServiceFactory.getUserService().getCurrentUser()));
        dataMap.put(Link.class, getCollection(new Link("http://localhost/"), new Link("http://localhost/test")));
        dataMap.put(BlobKey.class, getCollection(new BlobKey("test.txt"), new BlobKey("test2.txt")));
        
        dataMap.put(Email.class, getCollection(new Email("test@example.com"), new Email("test2@example.com")));
        dataMap.put(Category.class, getCollection(new Category("category1"), new Category("category2")));
        dataMap.put(Boolean.class, getCollection(Boolean.TRUE, Boolean.FALSE));
        dataMap.put(Rating.class, getCollection(new Rating(1), new Rating(2)));
        dataMap.put(IMHandle.class, getCollection(new IMHandle(new URL("http://localhost/"), "handle1"), new IMHandle(new URL("http://localhost"), "handle2")));
        dataMap.put(GeoPt.class, getCollection(new GeoPt(0.0f, 1.1f), new GeoPt(1.1f, 2.2f)));
        dataMap.put(PhoneNumber.class, getCollection(new PhoneNumber("00000000000"), new PhoneNumber("00000000000")));
        dataMap.put(PostalAddress.class, getCollection(new PostalAddress("00000000000"), new PostalAddress("00000000000")));

        return dataMap;
    }

    private Collection<?> getCollection(Object ... objs) {
        ArrayList<Object> col = new ArrayList<Object>();
        for (Object o : objs) {
            col.add(o);
        }
        return col;
    }
    
    
    <COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    void verifyCollections(Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> propertyClass, COLLECTION_TYPE colInstance) throws Exception {
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();

        EntityCollectionPropertyInfo<TestCollection, COLLECTION_TYPE, PROPERTY_TYPE> collectionPropertyInfo =
            EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, collectionClass, propertyClass, "Collection" + propertyClass.getClass());

        EntityCollectionProperty<TestCollection, COLLECTION_TYPE, PROPERTY_TYPE> prop = collectionPropertyInfo.newInstance(collection.getEntityPropertyAccess());
        
        prop.set(colInstance);
        collection.put();

        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        prop = collectionPropertyInfo.newInstance(collection.getEntityPropertyAccess());
        COLLECTION_TYPE newCollection = prop.get();
        assertEquals("Check size.", colInstance.size(), newCollection.size());
        assertTrue("Verify col=" + collectionClass + ",prop=" + propertyClass, newCollection.containsAll(colInstance));
    }
    
    
    public void testSerializableSet() throws Exception {
        Set<Serializable> set = new HashSet<Serializable>();
        for (int i = 0;i < 3;i++) {
            set.add(new SerializableObject("string" + i));
        }

        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.serializableSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Set<Serializable> newList = collection.serializableSet.get();
        assertEquals(set.size(), newList.size());
        assertTrue("ret=" + newList + ",s=" + set, newList.containsAll(set));
    }
    
    
    public void testLongStringSet() throws Exception {
        Set<String> set = new HashSet<String>();
        for (int i = 495;i < 510;i++) {
            char[] chars = new char[i];
            for (int k = 0;k < chars.length;k++) {
                chars[k] = 'a';
            }
            set.add(new String(chars));
        }
        TestCollection collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        collection.putIfAbsent();
        collection.stringSet.set(set);
        collection.put();
        collection = TestCollection.NAME_FACTORY.initInstance("collection1");
        Set<String> newList = collection.stringSet.get();
        assertEquals(set.size(), newList.size());
        assertTrue(newList.containsAll(set));
        
    }

    
    static class SerializableObject implements Serializable {
        private String value;
        public SerializableObject(String o) {
            this.value = o;
        }
        
        public int hashCode() {
            return value.hashCode();
        }
        
        public boolean equals(Object o) {
            return o instanceof SerializableObject && value.equals(((SerializableObject)o).value);
        }
        
        public String toString() {
            return "s:" + value;
        }
    }
}
