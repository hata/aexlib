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

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.archtea.gae.LocalDataStoreTestCase;
import com.archtea.gae.datastore.EntityBase;
import com.archtea.gae.datastore.EntityBasePropertyAccess;
import com.archtea.gae.datastore.EntityVersionManager;
import com.archtea.gae.datastore.impl.EntityVersionPropertyInfoImpl;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityBaseImplTest extends LocalDataStoreTestCase {

    TestDocument doc;

    protected void setUp() throws Exception {
        super.setUp();
        doc = TestDocument.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        doc = null;
        
        TestDocument.versionManagerList = null;
        super.tearDown();
    }

    public void testGetKindName() {
        assertEquals("TestDocument", TestDocument.getKindName(TestDocument.class));
    }

    public void testGetKey() {
        assertNotNull(doc.getKey());
        assertEquals("TestDocument", doc.getKey().getKind());
        assertEquals("doc1", doc.getKey().getName());
        assertNull(doc.getKey().getParent());
    }


    public void testCreateIfNotExist() {
        assertTrue(TestIdPage.ID_FACTORY.initInstance(doc).putIfAbsent());
        assertTrue(TestDocument.NAME_FACTORY.initInstance("doc2").putIfAbsent());
        assertFalse(TestDocument.NAME_FACTORY.initInstance("doc2").putIfAbsent());
    }

    public void testDelete() throws Exception {
        Key key = doc.getKey();
        assertNotNull(DatastoreServiceFactory.getDatastoreService().get(key));
        doc.delete();
        try {
            DatastoreServiceFactory.getDatastoreService().get(key);
            fail("This should throw an exception.");
        } catch (EntityNotFoundException e) {
            // ok.
        }
    }

    public void testIsExists() {
        assertTrue(doc.isExists());
        assertFalse(TestDocument.NAME_FACTORY.initInstance("doc2").isExists());
    }

    public void testVersionManager() throws Exception {
        ArrayList<EntityVersionManager<TestDocument>> versionManagerList
            = new ArrayList<EntityVersionManager<TestDocument>>();
        for (int i = 1;i <= 5;i++) {
            versionManagerList.add(new TestDocumentVersion(i));
        }
        TestDocument.versionManagerList = versionManagerList;

        for (long i = 1L;i <= 5L;i++) {
            setTestDocumentVersion(i);
            TestDocument doc = TestDocument.NAME_FACTORY.initInstance("docx" + i);
            assertTrue(doc.putIfAbsent());
            assertEquals((Long)i, doc.version.get());
        }

        for (long i = 1L;i <= 5L;i++) {
            TestDocument doc = TestDocument.NAME_FACTORY.initInstance("docx" + i);
            assertFalse(doc.putIfAbsent());
            assertEquals((Long)5L, doc.version.get());
            switch((int)i) {
            case 1: assertEquals("title2345", doc.title.get()); break;
            case 2: assertEquals("title345", doc.title.get()); break;
            case 3: assertEquals("title45", doc.title.get()); break;
            case 4: assertEquals("title5", doc.title.get()); break;
            case 5: assertNull(doc.title.get()); break;
                default:
                    fail("This should not come.");
            }
            doc.put();
        }
        
        setTestDocumentVersion(1L);
        for (long i = 1L;i <= 5L;i++) {
            TestDocument doc = TestDocument.NAME_FACTORY.initInstance("docx" + i);
            assertFalse(doc.putIfAbsent());
            assertEquals((Long)1L, doc.version.get());
            switch((int)i) {
            case 1: assertEquals("2345title2345", doc.title.get()); break;
            case 2: assertEquals("2345title345", doc.title.get()); break;
            case 3: assertEquals("2345title45", doc.title.get()); break;
            case 4: assertEquals("2345title5", doc.title.get()); break;
            case 5: assertEquals("2345title", doc.title.get()); break;
                default:
                    fail("This should not come.");
            }
            doc.put();
        }
    }

    
    public void testInitialValueSetAndCreateEntity() throws Exception {
        doc = TestDocument.NAME_FACTORY.initInstance("doc11");
        doc.title.set("title11");
        assertTrue(doc.putIfAbsent());
        
        doc = TestDocument.NAME_FACTORY.initInstance("doc11");
        assertEquals("title11", doc.title.get());
    }
    
    public void testInitialValueSetAndPutEntity() throws Exception {
        doc = TestDocument.NAME_FACTORY.initInstance("doc12");
        doc.title.set("title12");
        doc.put();
        
        doc = TestDocument.NAME_FACTORY.initInstance("doc12");
        assertEquals("title12", doc.title.get());
    }
    
    public void testInitialValueSetForIdEntity() throws Exception {
        TestIdPage page = TestIdPage.ID_FACTORY.initInstance(doc);
        page.title.set("new page title");
        page.putIfAbsent();
    }

    private void setTestDocumentVersion(long ver) throws Exception {
        EntityVersionPropertyInfoImpl<TestDocument> verInfo = ((EntityVersionPropertyInfoImpl<TestDocument>)TestDocument.VERSION);
        Field versionField = EntityVersionPropertyInfoImpl.class.getDeclaredField("version");
        versionField.setAccessible(true);
        versionField.set(verInfo, ver);
    }

    
    static class TestDocumentVersion implements EntityVersionManager<TestDocument> {
        private long version;
        TestDocumentVersion(long version) {
            this.version = version;
        }

        public long getVersion() {
            return version;
        }
        
        public void init(EntityBasePropertyAccess<TestDocument> newEntity) {
            
        }


        public void up(EntityBasePropertyAccess<TestDocument> entity) {
            try {
                String title = (String)entity.getProperty(TestDocument.TITLE.getName());
                title = title != null ? title + version : "title" + version;
                entity.setProperty(TestDocument.TITLE.getName(), title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void down(EntityBasePropertyAccess<TestDocument> entity) {
            try {
                String title = (String)entity.getProperty(TestDocument.TITLE.getName());
                title = title != null ? "" + version + title : "" + version + "title";
                entity.setProperty(TestDocument.TITLE.getName(), title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
