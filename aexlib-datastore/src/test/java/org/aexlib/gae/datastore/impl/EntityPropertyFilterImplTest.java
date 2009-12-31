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

import java.util.ArrayList;

import com.archtea.gae.LocalDataStoreTestCase;
import com.archtea.gae.datastore.EntityBase;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class EntityPropertyFilterImplTest extends LocalDataStoreTestCase {

    EntityPropertyFilterImpl<TestDocument, String> filter;
    String propertyName;
    ArrayList<FilterOperator> opList;
    ArrayList<Object> valueList;

    protected void setUp() throws Exception {
        super.setUp();
        filter = new EntityPropertyFilterImpl<TestDocument, String>("testProp");
        opList = new ArrayList<FilterOperator>();
        valueList = new ArrayList<Object>();
    }

    protected void tearDown() throws Exception {
        filter = null;
        super.tearDown();
    }

    public void testEqual() {
        assertEquals(0, opList.size());
        assertEquals(0, valueList.size());
        filter.equal("test");
        checkFilter(FilterOperator.EQUAL, "test");
    }

    public void testLessThan() {
        assertEquals(0, opList.size());
        assertEquals(0, valueList.size());
        filter.lessThan("test");
        checkFilter(FilterOperator.LESS_THAN, "test");
    }

    public void testLessThanOrEqual() {
        assertEquals(0, opList.size());
        assertEquals(0, valueList.size());
        filter.lessThanOrEqual("test");
        checkFilter(FilterOperator.LESS_THAN_OR_EQUAL, "test");
    }

    public void testGreaterThan() {
        assertEquals(0, opList.size());
        assertEquals(0, valueList.size());
        filter.greaterThan("test");
        checkFilter(FilterOperator.GREATER_THAN, "test");
    }

    public void testGreaterThanOrEqual() {
        assertEquals(0, opList.size());
        assertEquals(0, valueList.size());
        filter.greaterThanOrEqual("test");
        checkFilter(FilterOperator.GREATER_THAN_OR_EQUAL, "test");
    }

    public void testGreaterThanAndLessThan() {
        assertEquals(0, opList.size());
        assertEquals(0, valueList.size());
        filter.greaterThan("test").lessThan("test2");
        checkFilter2(FilterOperator.GREATER_THAN, "test", FilterOperator.LESS_THAN, "test2");
    }

    public void checkFilter(FilterOperator op, String value) {
        filter.addFilter(new EntityQueryMock());
        assertEquals("testProp", propertyName);
        assertEquals(1, opList.size());
        assertEquals(1, valueList.size());
        assertEquals(op, opList.get(0));
        assertEquals(value, valueList.get(0));
    }

    public void checkFilter2(FilterOperator op1, String value1, FilterOperator op2, String value2) {
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
            EntityPropertyFilterImplTest.this.propertyName = propertyName;
            opList.add(op);
            valueList.add(value);
        }
    }
}
