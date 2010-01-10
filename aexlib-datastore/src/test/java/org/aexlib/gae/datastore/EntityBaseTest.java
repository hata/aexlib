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
import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityNameBase;
import org.aexlib.gae.datastore.EntityNameFactory;
import org.aexlib.gae.datastore.impl.TestDocument;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityBaseTest extends LocalDataStoreTestCase {
    public static class ParentEntity extends EntityNameBase<ParentEntity> {
        public static final EntityNameFactory<ParentEntity> NAME_FACTORY =
            EntityFactory.getEntityNameFactory(ParentEntity.class,
                new EntityCreator<ParentEntity>() {
            public ParentEntity newInstance() {
                return new ParentEntity();
            }
        });
        
    }
    
    public class ChildEntity extends EntityChildNameBase<ChildEntity, ParentEntity> {
        
    }
    
    public class SubChildEntity extends EntityChildNameBase<SubChildEntity, ChildEntity> {
        
    }

    ParentEntity doc;
    protected void setUp() throws Exception {
        super.setUp();
        doc = ParentEntity.NAME_FACTORY.initInstance("doc1");
        doc.putIfAbsent();

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUser() throws Exception {
        for (int i = 0;i < 10;i++) {
            TestUser user = TestUser.FACTORY.initInstance("test" + i);
            assertTrue(user.putIfAbsent());
            user.title.set("title" + i);
            user.text.set("text" + i);
            assertEquals("title" + i, user.title.get());
            assertEquals("text" + i, user.text.get());
        }
        
        for (TestUser test : TestUser.QUERY.query().filter(TestUser.TITLE.equal("test5")).asIterable()) {
            assertEquals("test5", test.title.get());
        }
        
    }
    public void testInitKey() {
        Key key = doc.getKey();
        assertEquals("doc1", key.getName());
        doc.init(KeyFactory.createKey("ParentEntity", "doc2"));
        assertEquals("doc2", doc.getKey().getName());
    }


    public void testInitEntity() {
        Key key = doc.getKey();
        assertEquals("doc1", key.getName());
        doc.init(new Entity(KeyFactory.createKey("ParentEntity", "doc2")));
        assertEquals("doc2", doc.getKey().getName());
    }

    public void testSetVersion() throws Exception {
        Entity entity = new Entity(KeyFactory.createKey("ParentEntity", "doc2"));
        doc.getEntityPropertyAccess().setVersion("version", 2L);
        doc.init(entity);
        assertEquals(2L, entity.getProperty("version"));
//        doc.title.set("title2");
        
        doc.getEntityPropertyAccess().setVersion("version", 3L);
        doc.init(entity);
        assertEquals((Long)3L, entity.getProperty("version"));
    }

    public void testGetProperty() throws Exception {
        doc.getEntityPropertyAccess().setProperty("Title", "title2");
        assertEquals("title2", doc.getEntityPropertyAccess().getProperty("Title"));
    }

    public void testSetProperty() throws Exception {
        doc.getEntityPropertyAccess().setProperty("Title", "title2");
        doc.put();
        Entity entity = DatastoreServiceFactory.getDatastoreService().get(doc.getKey());
        assertEquals("title2", entity.getProperty("Title"));
    }

}
