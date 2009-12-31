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
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfo;

public class EntityPropertyInfoImpl<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE> implements EntityPropertyInfo<ENTITY, PROPERTY_TYPE> {
//    private final Class<ENTITY> entityClass;
    private final Class<PROPERTY_TYPE> typeClass;
    private final String propertyName;
    
    public static <ENTITY2 extends EntityBase<ENTITY2>, PROPERTY_TYPE>
        EntityPropertyInfoImpl<ENTITY2, PROPERTY_TYPE>
        getInstance(Class<ENTITY2> entityClass, Class<PROPERTY_TYPE> typeClass, String propertyName) {
        return new EntityPropertyInfoImpl<ENTITY2, PROPERTY_TYPE>(entityClass, typeClass, propertyName);
    }

    EntityPropertyInfoImpl(Class<ENTITY> entityClass, Class<PROPERTY_TYPE> typeClass, String propertyName) {
//        this.entityClass = entityClass;
        this.typeClass = typeClass;
        this.propertyName = propertyName;
    }

    public EntityProperty<ENTITY, PROPERTY_TYPE> newInstance(EntityBasePropertyAccess<ENTITY> entityInstance) {
        return new EntityPropertyImpl<ENTITY, PROPERTY_TYPE>(entityInstance, typeClass, propertyName,
                DataTypeTranslatorFactory.getUnindexedTranslator(typeClass));
    }
    
    public String getName() {
        return propertyName;
    }
    
    protected Class<PROPERTY_TYPE> getPropertyType() {
        return typeClass;
    }
}
