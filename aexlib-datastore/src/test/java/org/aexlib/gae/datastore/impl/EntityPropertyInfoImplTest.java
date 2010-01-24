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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;
import org.aexlib.gae.datastore.impl.EntityPropertyInfoImpl;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.DataTypeUtils;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.IMHandle;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;

public class EntityPropertyInfoImplTest extends LocalDataStoreTestCase {

    EntityPropertyInfoImpl<TestDocument, String> info;
    TestDocument doc;

    String propertyName;
    ArrayList<FilterOperator> opList;
    ArrayList<Object> valueList;
    
    Query.SortDirection direction;

    protected void setUp() throws Exception {
        super.setUp();
        info = (EntityPropertyInfoImpl<TestDocument, String>)EntityPropertyInfoFactory.getPropertyInfo(TestDocument.class, String.class, "testProp");
        doc = TestDocument.NAME_FACTORY.initInstance("test");
        doc.putIfAbsent();
        opList = new ArrayList<FilterOperator>();
        valueList = new ArrayList<Object>();
    }

    protected void tearDown() throws Exception {
        info = null;
        super.tearDown();
    }

    public void testGetInstance() {
        assertNotNull(info);
    }

    public void testNewInstance() throws Exception {
        EntityProperty<TestDocument, String> prop = info.newInstance(null);
        assertNotNull(prop);
        assertEquals("testProp", prop.getName());
    }


    public void testGetPropertyName() {
        assertEquals("testProp", info.getName());
    }

    public void testGetPropertyType() {
        assertEquals(String.class, info.getPropertyType());
    }

    // This can detect new support classes.
    public void testCheckSupportedClasses() {
        HashSet<Class<?>> testClasses =  new HashSet<Class<?>>();
        testClasses.add(Byte.class);
        testClasses.add(Short.class);
        testClasses.add(Integer.class);
        testClasses.add(Long.class);
        testClasses.add(Float.class);
        testClasses.add(Double.class);
        testClasses.add(String.class);
        testClasses.add(Date.class);
        testClasses.add(Text.class);
        testClasses.add(ShortBlob.class);
        testClasses.add(Blob.class);
        testClasses.add(Key.class);
        testClasses.add(User.class);
        testClasses.add(Link.class);
        testClasses.add(BlobKey.class);
        testClasses.add(Email.class);
        testClasses.add(Category.class);
        testClasses.add(Boolean.class);
        testClasses.add(Rating.class);
        testClasses.add(IMHandle.class);
        testClasses.add(GeoPt.class);
        testClasses.add(PhoneNumber.class);
        testClasses.add(PostalAddress.class);
        
        
        for (Class<?> clazz : testClasses) {
            DataTypeUtils.isSupportedType(clazz);
        }
        
        HashSet<Class<?>> differenceSet = new HashSet<Class<?>>(testClasses);
        
        assertEquals("difference. " + differenceSet.removeAll(DataTypeUtils.getSupportedTypes()), testClasses, DataTypeUtils.getSupportedTypes());
    }

    public void testInteger() throws Exception {
        EntityPropertyInfoImpl<TestDocument, Integer> info = (EntityPropertyInfoImpl<TestDocument, Integer>)EntityPropertyInfoFactory.getPropertyInfo(TestDocument.class, Integer.class, "prop" + Integer.class.getSimpleName());
        EntityProperty<TestDocument, Integer> prop = info.newInstance(doc.getEntityPropertyAccess());
        prop.set(10);
        doc.put();
        doc = TestDocument.NAME_FACTORY.initInstance("test");
        assertEquals(10, (int)prop.get());
    }


    public void testSerializable() throws Exception {
        EntityPropertyInfoImpl<TestDocument, SerializableObject> info = (EntityPropertyInfoImpl<TestDocument, SerializableObject>)EntityPropertyInfoFactory.getPropertyInfo(TestDocument.class, SerializableObject.class, "prop" + SerializableObject.class.getSimpleName());
        EntityProperty<TestDocument, SerializableObject> prop = info.newInstance(doc.getEntityPropertyAccess());
        SerializableObject value = new SerializableObject(5);
        prop.set(value);
        doc.put();
        doc = TestDocument.NAME_FACTORY.initInstance("test");
        assertEquals(value, (SerializableObject)prop.get());
    }

    
    public void testNotSerializableUserObject() throws Exception {
        try {
            EntityPropertyInfoImpl<TestDocument, NotSerializableObject> info = (EntityPropertyInfoImpl<TestDocument, NotSerializableObject>)EntityPropertyInfoFactory.getPropertyInfo(TestDocument.class, NotSerializableObject.class, "prop" + NotSerializableObject.class.getSimpleName());
            EntityProperty<TestDocument, NotSerializableObject> prop = info.newInstance(doc.getEntityPropertyAccess());
            NotSerializableObject value = new NotSerializableObject();
            prop.set(value);
            doc.put();
            fail("Cannot store no serializable object.");
        } catch (Exception e) {
            // ok
        }
    }

    static class SerializableObject implements Serializable {
        public int value;

        public SerializableObject(int value) {
            this.value = value;
        }

        public int hashCode() {
            return value;
        }

        public boolean equals(Object o) {
            return o instanceof SerializableObject && value == ((SerializableObject)o).value;
        }
    }
    
    static class NotSerializableObject {
    }
}
