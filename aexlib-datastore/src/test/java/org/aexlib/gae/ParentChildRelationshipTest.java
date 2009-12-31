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
package com.archtea.gae;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class ParentChildRelationshipTest extends LocalDataStoreTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void ISSUE_2196_testRelationshipIssue() throws Exception {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Entity parentEntity1 = new Entity("Parent", "parent1");
        parentEntity1.setProperty("p1", "value1");
        ds.put(parentEntity1);
        Key parentKey1 = parentEntity1.getKey();

        Entity childEntity1 = new Entity("Child", "child1", parentKey1);
        childEntity1.setProperty("c1", "value1");
        ds.put(childEntity1);

        assertEquals(KeyFactory.createKey(parentKey1, "Child", "child1"), childEntity1.getKey());

        Query query = new Query(parentKey1);
        query.addFilter("p1", Query.FilterOperator.EQUAL, "value1");
        PreparedQuery pq = ds.prepare(query);
        assertEquals(1, pq.countEntities());
    }
    
    public void testMultiValues() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Entity entity = new Entity("ListTest");
        ArrayList<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        entity.setProperty("multi", list);
        ds.put(entity);

        Query query = new Query("ListTest");
        query.addFilter("multi", Query.FilterOperator.EQUAL, "test1");
        PreparedQuery pq = ds.prepare(query);
        assertEquals(1, pq.countEntities());

        // GAE doesn't support like this query.
//        query = new Query("ListTest");
//        query.addFilter("multi", Query.FilterOperator.EQUAL, new ArrayList<String>(list));
//        pq = ds.prepare(query);
//        assertEquals(1, pq.countEntities());
    }

}
