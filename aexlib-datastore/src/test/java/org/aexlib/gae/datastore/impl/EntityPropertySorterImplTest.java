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

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.impl.EntityPropertySorterImpl;
import org.aexlib.gae.datastore.impl.EntityQueryImpl;

import com.google.appengine.api.datastore.Query;

public class EntityPropertySorterImplTest extends LocalDataStoreTestCase {

    String name;
    Query.SortDirection sortDirection;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAddSortAsc() throws Exception {
        EntityPropertySorterImpl<TestDocument> sorter =
            new EntityPropertySorterImpl<TestDocument>("test", Query.SortDirection.ASCENDING);
        sorter.addSort(new EntityQueryMock());
        assertEquals("test", name);
        assertEquals(Query.SortDirection.ASCENDING, sortDirection);
    }

    public void testAddSortDesc() throws Exception {
        EntityPropertySorterImpl<TestDocument> sorter =
            new EntityPropertySorterImpl<TestDocument>("test", Query.SortDirection.DESCENDING);
        sorter.addSort(new EntityQueryMock());
        assertEquals("test", name);
        assertEquals(Query.SortDirection.DESCENDING, sortDirection);
    }
    
    class EntityQueryMock extends EntityQueryImpl<TestDocument> {

        public EntityQueryMock() {
            super(new Query(EntityBase.getKindName(TestDocument.class)), TestDocument.NAME_FACTORY);
        }

        void addSorter(String propertyName, Query.SortDirection direction) {
            name = propertyName;
            sortDirection = direction;
        }
    }
}
