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

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityIndexablePropertyInfo;
import org.aexlib.gae.datastore.EntityPropertyFilter;

import com.google.appengine.api.datastore.Query;

public class EntityIndexablePropertyInfoImpl<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE>
extends EntityPropertyInfoImpl<ENTITY, PROPERTY_TYPE>
implements EntityIndexablePropertyInfo<ENTITY, PROPERTY_TYPE> {
    private final EntityPropertySorterImpl<ENTITY> asc;
    private final EntityPropertySorterImpl<ENTITY> desc;
    
    public static <ENTITY2 extends EntityBase<ENTITY2>, PROPERTY_TYPE>
    EntityIndexablePropertyInfoImpl<ENTITY2, PROPERTY_TYPE>
    getInstance(Class<ENTITY2> entityClass, Class<PROPERTY_TYPE> typeClass, DataTypeTranslator translator, String propertyName) {
        return new EntityIndexablePropertyInfoImpl<ENTITY2, PROPERTY_TYPE>(entityClass, typeClass, translator, propertyName);
    }

    EntityIndexablePropertyInfoImpl(Class<ENTITY> entityClass, Class<PROPERTY_TYPE> propertyClass, DataTypeTranslator translator, String propertyName) {
        super(entityClass, propertyClass, translator, propertyName);
        // TODO: this should be cached.
        this.asc = new EntityPropertySorterImpl<ENTITY>(propertyName, Query.SortDirection.ASCENDING);
        this.desc = new EntityPropertySorterImpl<ENTITY>(propertyName, Query.SortDirection.DESCENDING);
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> equal(PROPERTY_TYPE value) {
        // TODO: Cache should be used.
        return newEntityPropertyFilter().equal(value);
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> lessThan(PROPERTY_TYPE value) {
        // TODO: Cache should be used.
        return newEntityPropertyFilter().lessThan(value);
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> lessThanOrEqual(PROPERTY_TYPE value) {
        // TODO: Cache should be used.
        return newEntityPropertyFilter().lessThanOrEqual(value);
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> greaterThan(PROPERTY_TYPE value) {
        // TODO: Cache should be used.
        return newEntityPropertyFilter().greaterThan(value);
    }

    public EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> greaterThanOrEqual(PROPERTY_TYPE value) {
        // TODO: Cache should be used.
        return newEntityPropertyFilter().greaterThanOrEqual(value);
    }
    
    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> in(PROPERTY_TYPE value) {
        return newEntityPropertyFilter().in(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> notEqual(PROPERTY_TYPE value) {
        return newEntityPropertyFilter().notEqual(value);
    }

    public EntityPropertySorterImpl<ENTITY> asc() {
        return asc;
    }
    
    public EntityPropertySorterImpl<ENTITY> desc() {
        return desc;
    }
    
    protected EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> newEntityPropertyFilter() {
        return new EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE>(getName(), getTranslator(getPropertyType()));
    }

    @Override
    protected boolean isIndexable() {
        return true;
    }

}
