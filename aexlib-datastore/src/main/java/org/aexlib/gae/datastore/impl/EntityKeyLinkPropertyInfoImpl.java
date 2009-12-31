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
import org.aexlib.gae.datastore.EntityBaseFactory;
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityProperty;

public class EntityKeyLinkPropertyInfoImpl
<ENTITY extends EntityBase<ENTITY>,
 PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
extends EntityIndexablePropertyInfoImpl<ENTITY, PROPERTY_TYPE> {

    private final EntityBaseFactory<PROPERTY_TYPE> propertyFactory;
    
    public static <ENTITY2 extends EntityBase<ENTITY2>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
        EntityKeyLinkPropertyInfoImpl<ENTITY2, PROPERTY_TYPE>
        getInstance(Class<ENTITY2> entityClass, Class<PROPERTY_TYPE> typeClass, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        return new EntityKeyLinkPropertyInfoImpl<ENTITY2, PROPERTY_TYPE>(entityClass, typeClass, propertyFactory, propertyName);
    }

    EntityKeyLinkPropertyInfoImpl(Class<ENTITY> entityClass, Class<PROPERTY_TYPE> typeClass, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        super(entityClass, typeClass, propertyName);
        this.propertyFactory = propertyFactory;
    }

    @Override
    public EntityProperty<ENTITY, PROPERTY_TYPE> newInstance(EntityBasePropertyAccess<ENTITY> entityInstance) {
        return new EntityKeyLinkPropertyImpl<ENTITY, PROPERTY_TYPE>(entityInstance, propertyFactory, getName());
    }


    @Override
    protected EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE> newEntityPropertyFilter() {
        return new EntityKeyLinkPropertyFilterImpl<ENTITY, PROPERTY_TYPE>(getName());
    }

}
