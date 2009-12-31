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

import java.util.LinkedList;

import com.archtea.gae.datastore.EntityBase;
import com.archtea.gae.datastore.EntityPropertyFilter;
import com.google.appengine.api.datastore.Query;

public class EntityPropertyFilterImpl<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE> implements EntityPropertyFilter<ENTITY, PROPERTY_TYPE> {
    private final String propertyName;
    private final LinkedList<Query.FilterOperator> opList = new LinkedList<Query.FilterOperator>();
    private final LinkedList<PROPERTY_TYPE> valueList = new LinkedList<PROPERTY_TYPE>();
    
    
    public EntityPropertyFilterImpl(String propertyName) {
        this.propertyName = propertyName;
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> equal(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.EQUAL);
        valueList.add(value);
        return this;
    }
    
    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> lessThan(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.LESS_THAN);
        valueList.add(value);
        return this;
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> lessThanOrEqual(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.LESS_THAN_OR_EQUAL);
        valueList.add(value);
        return this;
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> greaterThan(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.GREATER_THAN);
        valueList.add(value);
        return this;
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> greaterThanOrEqual(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.GREATER_THAN_OR_EQUAL);
        valueList.add(value);
        return this;
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> in(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.IN);
        valueList.add(value);
        return this;
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> not_equal(PROPERTY_TYPE value) {
        opList.add(Query.FilterOperator.NOT_EQUAL);
        valueList.add(value);
        return this;
    }

    void addFilter(EntityQueryImpl<ENTITY> query) {
        final int length = opList.size();
        for (int i = 0;i < length;i++) {
            query.addFilter(propertyName, opList.get(i), getQueryFilterValue(valueList.get(i)));
        }
    }
    
    protected Object getQueryFilterValue(PROPERTY_TYPE value) {
        return value;
    }

}
