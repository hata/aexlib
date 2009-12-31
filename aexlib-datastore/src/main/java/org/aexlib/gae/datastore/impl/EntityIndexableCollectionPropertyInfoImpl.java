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

import java.util.Collection;

import com.archtea.gae.datastore.EntityBase;
import com.archtea.gae.datastore.EntityBasePropertyAccess;
import com.archtea.gae.datastore.EntityCollectionProperty;
import com.archtea.gae.datastore.EntityIndexableCollectionPropertyInfo;
import com.archtea.gae.datastore.EntityIndexablePropertyInfo;
import com.archtea.gae.datastore.EntityPropertyFilter;
import com.archtea.gae.datastore.EntityPropertySorter;

public class EntityIndexableCollectionPropertyInfoImpl
<ENTITY extends EntityBase<ENTITY>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
extends EntityCollectionPropertyInfoImpl<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>
implements EntityIndexableCollectionPropertyInfo<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE> {

    private final EntityIndexablePropertyInfo<ENTITY, PROPERTY_TYPE> propertyInfo;

    public static <ENTITY2 extends EntityBase<ENTITY2>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    EntityIndexableCollectionPropertyInfoImpl<ENTITY2, COLLECTION_TYPE, PROPERTY_TYPE>
            getInstance(Class<ENTITY2> entityClass, Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> typeClass, String propertyName) {
        return new EntityIndexableCollectionPropertyInfoImpl<ENTITY2, COLLECTION_TYPE, PROPERTY_TYPE>(entityClass, collectionClass, typeClass, propertyName);
    }

    EntityIndexableCollectionPropertyInfoImpl(Class<ENTITY> entityClass,
            Class<COLLECTION_TYPE> collectionClass,
            Class<PROPERTY_TYPE> typeClass, String propertyName) {
        this(entityClass, collectionClass, typeClass, propertyName,
            new EntityIndexablePropertyInfoImpl<ENTITY, PROPERTY_TYPE>(entityClass, typeClass, propertyName));
    }

    EntityIndexableCollectionPropertyInfoImpl(Class<ENTITY> entityClass,
            Class<COLLECTION_TYPE> collectionClass,
            Class<PROPERTY_TYPE> typeClass, String propertyName, 
            EntityIndexablePropertyInfo<ENTITY, PROPERTY_TYPE> delegatePropertyInfo) {
        super(entityClass, collectionClass, typeClass, propertyName);
        propertyInfo = delegatePropertyInfo;
    }

    @Override
    public EntityCollectionProperty<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE> newInstance(EntityBasePropertyAccess<ENTITY> entityInstance) {
        return new EntityCollectionPropertyImpl<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>(
                entityInstance, getCollectionType(), getPropertyType(), getName(), DataTypeTranslatorFactory.getIndexableTranslator(getPropertyType()));
    }

    public EntityPropertySorter<ENTITY> asc() {
        return propertyInfo.asc();
    }

    public EntityPropertySorter<ENTITY> desc() {
        return propertyInfo.desc();
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> equal(PROPERTY_TYPE value) {
        return propertyInfo.equal(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> greaterThan(
            PROPERTY_TYPE value) {
        return propertyInfo.greaterThan(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> greaterThanOrEqual(
            PROPERTY_TYPE value) {
        return propertyInfo.greaterThanOrEqual(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> lessThan(
            PROPERTY_TYPE value) {
        return propertyInfo.lessThan(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> lessThanOrEqual(
            PROPERTY_TYPE value) {
        return propertyInfo.lessThanOrEqual(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> in(PROPERTY_TYPE value) {
        return propertyInfo.in(value);
    }

    public EntityPropertyFilter<ENTITY, PROPERTY_TYPE> not_equal(
            PROPERTY_TYPE value) {
        return propertyInfo.not_equal(value);
    }

}
