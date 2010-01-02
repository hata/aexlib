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
package org.aexlib.gae.datastore.impl;

import java.util.Collection;

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityBaseFactory;
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityCollectionProperty;


public class EntityIndexableKeyLinkCollectionPropertyInfoImpl
<ENTITY extends EntityBase<ENTITY>,
 COLLECTION_TYPE extends Collection<PROPERTY_TYPE>,
 PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
extends EntityIndexableCollectionPropertyInfoImpl<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE> {

    private final EntityBaseFactory<PROPERTY_TYPE> propertyFactory;

    public static <ENTITY2 extends EntityBase<ENTITY2>,
        COLLECTION_TYPE extends Collection<PROPERTY_TYPE>,
        PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
        EntityIndexableKeyLinkCollectionPropertyInfoImpl<ENTITY2,COLLECTION_TYPE,PROPERTY_TYPE>
            getInstance(
                    Class<ENTITY2> entityClass,
                    Class<COLLECTION_TYPE> collectionClass,
                    Class<PROPERTY_TYPE> typeClass,
                    EntityBaseFactory<PROPERTY_TYPE> propertyFactory,
                    String propertyName) {
        return new EntityIndexableKeyLinkCollectionPropertyInfoImpl<ENTITY2, COLLECTION_TYPE, PROPERTY_TYPE>(
                entityClass, collectionClass, typeClass, propertyFactory, propertyName);
    }

    EntityIndexableKeyLinkCollectionPropertyInfoImpl(Class<ENTITY> entityClass,
            Class<COLLECTION_TYPE> collectionClass,
            Class<PROPERTY_TYPE> typeClass,
            EntityBaseFactory<PROPERTY_TYPE> propertyFactory,
            String propertyName) {
        super(entityClass, collectionClass, typeClass, propertyName, EntityIndexableKeyLinkPropertyInfoImpl.getInstance(entityClass, typeClass, propertyFactory, propertyName));
        this.propertyFactory = propertyFactory;
    }

    public EntityCollectionProperty<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>
    newInstance(EntityBasePropertyAccess<ENTITY> entityInstance) {
        return new EntityKeyLinkCollectionPropertyImpl<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>(entityInstance, getCollectionType(), propertyFactory, getName(), true);
    }

}
