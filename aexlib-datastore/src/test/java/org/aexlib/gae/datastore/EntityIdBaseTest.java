/*
 * $Id$
 * 
 * Copyright 2010 Hiroki Ata
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
import org.aexlib.gae.datastore.impl.TestEntry;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class EntityIdBaseTest extends LocalDataStoreTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testPutIfAbsent() {
        TestEntry entry = TestEntry.ID_FACTORY.initInstance();
        assertTrue(entry.putIfAbsent());
        Key key = entry.getKey();
        
        assertFalse(entry.putIfAbsent());
        assertEquals(key, entry.getKey());
    }

    public void testPutIfAbsentFromKeyInit() {
        TestEntry entry = TestEntry.ID_FACTORY.initInstance();
        assertTrue(entry.putIfAbsent());
        Key key = entry.getKey();
 
        entry = TestEntry.ID_FACTORY.initInstance(key);
        assertFalse(entry.putIfAbsent());
        assertEquals(key, entry.getKey());
    }

    public void testPutIfAbsentFromEntityInit() throws Exception {
        TestEntry entry = TestEntry.ID_FACTORY.initInstance();
        assertTrue(entry.putIfAbsent());
        Key key = entry.getKey();
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Entity ent = ds.get(key);
 
        entry = TestEntry.ID_FACTORY.initInstance(ent);
        assertFalse(entry.putIfAbsent());
        assertEquals(key, entry.getKey());
    }

    public void testGetKind() {
        TestEntry entry = TestEntry.ID_FACTORY.initInstance();
        assertEquals(TestEntry.class.getSimpleName(), entry.getKind());
    }

}
