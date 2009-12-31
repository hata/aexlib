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
import java.util.Date;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityNameBase;
import org.aexlib.gae.datastore.EntityNameFactory;
import org.aexlib.gae.datastore.impl.DataTypeTranslatorFactory;
import org.aexlib.gae.datastore.impl.EntityPropertyImpl;
import org.aexlib.gae.datastore.impl.SerializableDataTypeTranslatorImpl;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

//http://code.google.com/intl/ja/appengine/docs/java/datastore/dataclasses.html
public class EntityPropertyImplTest extends LocalDataStoreTestCase {

    TestDocument doc;
    EntityPropertyTest test;
    

    protected void setUp() throws Exception {
        super.setUp();
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();
        test = EntityPropertyTest.FACTORY.initInstance("test1");
        test.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        doc = null;
        test = null;
        super.tearDown();
    }

    public void testGet() throws Exception {
        assertNull(doc.title.get());
    }

    public void testSet() throws Exception {
        doc.title.set("title1");
        assertEquals("title1", doc.title.get());
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        assertNull(doc.title.get());
    }

    public void testSetPut() throws Exception {
        doc.title.set("title1");
        doc.put();
        assertEquals("title1", doc.title.get());
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        assertEquals("title1", doc.title.get());
    }

    public void testGetName() {
        assertEquals("Title", doc.title.getName());
    }

    public void testStringProperty() throws Exception {
        EntityPropertyImpl<EntityPropertyTest, String> prop =
            new EntityPropertyImpl<EntityPropertyTest, String>(
                    test.getEntityBasePropertyAccess(), String.class, "stringProperty",
                    DataTypeTranslatorFactory.getUnindexedTranslator(String.class));
        prop.set("testString");
        test.put();
        assertEquals("testString", prop.get());
    }
    
    public void testShortBlob() throws Exception {
        EntityPropertyImpl<EntityPropertyTest, ShortBlob> prop =
            new EntityPropertyImpl<EntityPropertyTest, ShortBlob>(
                    test.getEntityBasePropertyAccess(), ShortBlob.class, "shortBlobProperty",
                    DataTypeTranslatorFactory.getUnindexedTranslator(ShortBlob.class));
        prop.set(new ShortBlob("testShortBlob".getBytes()));
        test.put();
        assertEquals("testShortBlob", new String(prop.get().getBytes()));
    }

    public void testBool() throws Exception {
        EntityPropertyImpl<EntityPropertyTest, Boolean> prop =
            new EntityPropertyImpl<EntityPropertyTest, Boolean>(
                    test.getEntityBasePropertyAccess(), Boolean.class, "booleanProperty",
                    DataTypeTranslatorFactory.getUnindexedTranslator(Boolean.class));
        prop.set(true);
        test.put();
        assertTrue(prop.get());

        prop.set(false);
        test.put();
        assertFalse(prop.get());
    }
    
    public void testByte() throws Exception {
        testPropertyType(Byte.class, (byte)0, (byte)15, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public void testShort() throws Exception {
        testPropertyType(Short.class, (short)0, (short)15, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public void testInteger() throws Exception {
        testPropertyType(Integer.class, 0, 15, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public void testLong() throws Exception {
        testPropertyType(Long.class, 0L, 15L, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public void testFloat() throws Exception {
        testPropertyType(Float.class, 1.2f, 0f, Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public void testDouble() throws Exception {
        testPropertyType(Double.class, 1.2d, 0d, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public void testDate() throws Exception {
        testPropertyType(Date.class, new Date());
    }
    
    public void testText() throws Exception {
        testPropertyType(Text.class, new Text("test"));
    }

    public void testBlob() throws Exception {
        testPropertyType(Blob.class, new Blob("test".getBytes()));
    }

    public void testLink() throws Exception {
        testPropertyType(Link.class, new Link("http://localhost/"));
    }

    public void testKey() throws Exception {
        testPropertyType(Key.class, doc.getKey());
    }
    
    public void testUser() throws Exception {
        UserService service = UserServiceFactory.getUserService();
        User user = service.getCurrentUser();
        assertNotNull(user);
        testPropertyType(User.class, user);
    }

    public void testNull() throws Exception {
        testPropertyType(String.class, (String)null);
    }

    public void testSerialized() throws Exception {
        SerializedObject value = new SerializedObject();
        value.value = 5;
        testPropertyType(SerializedObject.class, value);
    }

    public void testSupportSerializableDataType() throws Exception {
        SerializedObject value = new SerializedObject();
        value.value = 5;

        test = EntityPropertyTest.FACTORY.initInstance("test1");
        test.putIfAbsent();
        EntityPropertyImpl<EntityPropertyTest, SerializedObject> prop = 
            new EntityPropertyImpl<EntityPropertyTest, SerializedObject>(
                    test.getEntityBasePropertyAccess(), SerializedObject.class, "serializable", SerializableDataTypeTranslatorImpl.getInstance(SerializedObject.class));

        prop.set(value);
        test.put();
        prop = new EntityPropertyImpl<EntityPropertyTest, SerializedObject>(
                test.getEntityBasePropertyAccess(), SerializedObject.class, "serializable", SerializableDataTypeTranslatorImpl.getInstance(SerializedObject.class));

        assertEquals(value, SerializedObject.class.cast(prop.get()));
    }

    public void testLongStringProperty() throws Exception {
        for (int i = 495;i < 510;i++) {
            char[] chars = new char[i];
            for (int k = 0;k < chars.length;k++) {
                chars[k] = 'a';
            }
            testPropertyType(String.class, new String(chars));
        }
    }


    public <T> void testPropertyType(Class<T> typeClass, T ...values) throws Exception {
        String propName = typeClass.getSimpleName() + "Property";

        EntityPropertyImpl<EntityPropertyTest, T> prop = createProperty(typeClass, propName);
        test.putIfAbsent();

        for (T value : values) {
            prop.set(value);
            test.put();
            prop = createProperty(typeClass, propName);
            assertEquals(value, typeClass.cast(prop.get()));
        }
    }
    
    
    private <T> EntityPropertyImpl<EntityPropertyTest, T> createProperty(Class<T> typeClass, String name) throws Exception {
        test = EntityPropertyTest.FACTORY.initInstance("test1");
        return new EntityPropertyImpl<EntityPropertyTest, T>(
                    test.getEntityBasePropertyAccess(), typeClass, name,
                    DataTypeTranslatorFactory.getUnindexedTranslator(typeClass));
    }
    
    private static class SerializedObject implements Serializable {
        public int value;
        
        public int hashCode() {
            return value;
        }
        
        public boolean equals(Object o) {
            return o instanceof SerializedObject && value == ((SerializedObject)o).value;
        }
    }

    static class EntityPropertyTest extends EntityNameBase<EntityPropertyTest> {
        public static final EntityNameFactory<EntityPropertyTest> FACTORY =
            EntityFactory.getEntityNameFactory(EntityPropertyTest.class,
                new EntityCreator<EntityPropertyTest>() {
            public EntityPropertyTest newInstance() {
                return new EntityPropertyTest();
            }
        });
        
        
        public EntityBasePropertyAccess<EntityPropertyTest> getEntityBasePropertyAccess() {
            return super.getEntityPropertyAccess();
        }
    }

}
