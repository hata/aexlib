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

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public class EntityKeyLinkPropertyImpl<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>> implements EntityProperty<ENTITY, PROPERTY_TYPE> {
    // private Class<ENTITY> entityClass;
    private String propertyName;
    private EntityBasePropertyAccess<ENTITY> entityInstance;
    // private Class<PROPERTY_TYPE> propertyClass;
    private EntityBaseFactory<PROPERTY_TYPE> propertyFactory;

    EntityKeyLinkPropertyImpl(EntityBasePropertyAccess<ENTITY> entityInstance, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        this.entityInstance = entityInstance;
        this.propertyName = propertyName;
        this.propertyFactory = propertyFactory;
//        this.propertyClass = propertyClass;
    }
    
    public PROPERTY_TYPE get() throws EntityNotFoundException {
        return propertyFactory.initInstance((Key)entityInstance.getProperty(propertyName));
    }

    public void set(PROPERTY_TYPE value) throws EntityNotFoundException {
        entityInstance.setProperty(propertyName, value.getKey());
    }

    public void remove() throws EntityNotFoundException {
        entityInstance.removeProperty(propertyName);
    }
    
    public String getName() {
        return propertyName;
    }

}
