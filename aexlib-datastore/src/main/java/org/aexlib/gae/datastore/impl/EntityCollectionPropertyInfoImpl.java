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

import java.util.Collection;

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityCollectionProperty;
import org.aexlib.gae.datastore.EntityCollectionPropertyInfo;


public class EntityCollectionPropertyInfoImpl<ENTITY extends EntityBase<ENTITY>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE> implements EntityCollectionPropertyInfo<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE> {

//    private final Class<ENTITY> entityClass;
    private final Class<COLLECTION_TYPE> collectionClass;
    private final Class<PROPERTY_TYPE> typeClass;
    private final String propertyName;

    public static <ENTITY2 extends EntityBase<ENTITY2>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
        EntityCollectionPropertyInfoImpl<ENTITY2, COLLECTION_TYPE, PROPERTY_TYPE>
            getInstance(Class<ENTITY2> entityClass, Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> typeClass, String propertyName) {
        return new EntityCollectionPropertyInfoImpl<ENTITY2, COLLECTION_TYPE, PROPERTY_TYPE>(entityClass, collectionClass, typeClass, propertyName);
    }

    EntityCollectionPropertyInfoImpl(Class<ENTITY> entityClass,
            Class<COLLECTION_TYPE> collectionClass,
            Class<PROPERTY_TYPE> typeClass, String propertyName) {
//        this.entityClass = entityClass;
        this.collectionClass = collectionClass;
        this.typeClass = typeClass;
        this.propertyName = propertyName;
    }

    public EntityCollectionProperty<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE> newInstance(EntityBasePropertyAccess<ENTITY> entityInstance) {
        return new EntityCollectionPropertyImpl<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>(
                entityInstance, collectionClass, typeClass, propertyName,
                DataTypeTranslatorFactory.getUnindexedTranslator(typeClass), false);
    }

    public String getName() {
        return propertyName;
    }
    
    protected Class<COLLECTION_TYPE> getCollectionType() {
        return collectionClass;
    }
    
    protected Class<PROPERTY_TYPE> getPropertyType() {
        return typeClass;
    }
}
