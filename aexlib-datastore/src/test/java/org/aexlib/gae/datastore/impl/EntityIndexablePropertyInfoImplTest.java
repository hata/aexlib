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

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;
import org.aexlib.gae.datastore.impl.EntityIndexablePropertyInfoImpl;
import org.aexlib.gae.datastore.impl.EntityPropertyFilterImpl;
import org.aexlib.gae.datastore.impl.EntityPropertySorterImpl;
import org.aexlib.gae.datastore.impl.EntityQueryImpl;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;


public class EntityIndexablePropertyInfoImplTest extends LocalDataStoreTestCase {

    EntityIndexablePropertyInfoImpl<TestDocument, String> info;
    TestDocument doc;

    String propertyName;
    ArrayList<FilterOperator> opList;
    ArrayList<Object> valueList;
    
    Query.SortDirection direction;

    protected void setUp() throws Exception {
        super.setUp();
        info = (EntityIndexablePropertyInfoImpl<TestDocument, String>)EntityPropertyInfoFactory.getIndexablePropertyInfo(TestDocument.class, String.class, "testProp");
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

    public void testEqual() {
        checkFilter(info.equal("test"), FilterOperator.EQUAL);
    }

    public void testLessThan() {
        checkFilter(info.lessThan("test"), FilterOperator.LESS_THAN);
    }

    public void testLessThanAndGreaterThan() {
        checkFilter2(info.lessThan("test").greaterThan("test2"),
                FilterOperator.LESS_THAN, "test", FilterOperator.GREATER_THAN, "test2");
    }

    public void testLessThanOrEqual() {
        checkFilter(info.lessThanOrEqual("test"), FilterOperator.LESS_THAN_OR_EQUAL);
    }

    public void testGreaterThan() {
        checkFilter(info.greaterThan("test"), FilterOperator.GREATER_THAN);
    }

    public void testGreaterThanOrEqual() {
        checkFilter(info.greaterThanOrEqual("test"), FilterOperator.GREATER_THAN_OR_EQUAL);
    }

    public void testGreaterThanOrEqualAndLessThanOrEqual() {
        checkFilter2(info.greaterThanOrEqual("test").lessThanOrEqual("test2"),
                FilterOperator.GREATER_THAN_OR_EQUAL, "test", FilterOperator.LESS_THAN_OR_EQUAL, "test2");
    }

    public void testAsc() {
        EntityPropertySorterImpl<TestDocument> sorter = info.asc();
        sorter.addSort(new EntityQueryMock());
        assertEquals("testProp", propertyName);
        assertEquals(Query.SortDirection.ASCENDING, direction);
    }

    public void testDesc() {
        EntityPropertySorterImpl<TestDocument> sorter = info.desc();
        sorter.addSort(new EntityQueryMock());
        assertEquals("testProp", propertyName);
        assertEquals(Query.SortDirection.DESCENDING, direction);
    }

    public void testGetPropertyName() {
        assertEquals("testProp", info.getName());
    }

    public void testGetPropertyType() {
        assertEquals(String.class, info.getPropertyType());
    }
    
    
    public void testLongStringProperty() throws Exception {
        EntityProperty<TestDocument, String> prop = info.newInstance(doc.getEntityPropertyAccess());
        char[] chars = new char[501];
        for (int i = 0;i < chars.length;i++) {
            chars[i] = 'a';
        }
        try {
            prop.set(new String(chars));
            fail("This should throw an exception because string is too long");
        } catch (Exception e) {
            // ok
        }
    }

    private void checkFilter(EntityPropertyFilterImpl<TestDocument, String> filter, FilterOperator op) {
        filter.addFilter(new EntityQueryMock());
        assertEquals("testProp", propertyName);
        assertEquals(1, opList.size());
        assertEquals(1, valueList.size());
        assertEquals(op, opList.get(0));
        assertEquals("test", valueList.get(0));
    }

    private void checkFilter2(EntityPropertyFilterImpl<TestDocument, String> filter,
            FilterOperator op1, Object value1, FilterOperator op2, Object value2) {
        filter.addFilter(new EntityQueryMock());
        assertEquals("testProp", propertyName);
        assertEquals(2, opList.size());
        assertEquals(2, valueList.size());
        assertEquals(op1, opList.get(0));
        assertEquals(value1, valueList.get(0));
        assertEquals(op2, opList.get(1));
        assertEquals(value2, valueList.get(1));
    }

    class EntityQueryMock extends EntityQueryImpl<TestDocument> {

        public EntityQueryMock() {
            super(new Query(EntityBase.getKindName(TestDocument.class)), TestDocument.NAME_FACTORY);
        }

        @Override
        void addFilter(String propertyName, Query.FilterOperator op, Object value) {
            EntityIndexablePropertyInfoImplTest.this.propertyName = propertyName;
            opList.add(op);
            valueList.add(value);
        }

        void addSorter(String propertyName, Query.SortDirection direction) {
            EntityIndexablePropertyInfoImplTest.this.propertyName = propertyName;
            EntityIndexablePropertyInfoImplTest.this.direction = direction;
        }

    }
}
