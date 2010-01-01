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

import com.google.appengine.api.datastore.EntityNotFoundException;

/*
 * class TestData extends StoreEntityImpl<TestData> {
 * public static final EntityPropertyInfoImpl<TestData, String> TEXT =
 *    EntityPropertyInfoImpl<TestData, String>.getInstance(TestData.class, String.class, "TEXT");
 * ...
 * EntityPropertyImpl<TestData, String> text = TEXT.getInstance();
 * }
 */
public class EntityPropertyImpl<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE> implements EntityProperty<ENTITY, PROPERTY_TYPE> {
    // private Class<ENTITY> entityClass;
    private final String propertyName;
    private final EntityBasePropertyAccess<ENTITY> entityInstance;
    private final Class<PROPERTY_TYPE> propertyClass;
    private final DataTypeTranslator translator;
    private final boolean indexable;


    EntityPropertyImpl(EntityBasePropertyAccess<ENTITY> entityInstance, Class<PROPERTY_TYPE> propertyClass, String propertyName, DataTypeTranslator translator, boolean indexable) {
        this.entityInstance = entityInstance;
        this.propertyName = propertyName;
        this.propertyClass = propertyClass;
        this.translator = translator;
        this.indexable = indexable;
    }
    
    public PROPERTY_TYPE get() throws EntityNotFoundException {
        Object object = entityInstance.getProperty(propertyName);
        if (object == null) {
            return null;
        }
        return propertyClass.cast(translator != null ? translator.toUserType(object) : object);
    }

    public void set(PROPERTY_TYPE value) throws EntityNotFoundException {
        Object object = value;
        if (translator != null && value != null) {
            object = translator.toStoreType(value);
        }
        if (indexable) {
            entityInstance.setProperty(propertyName, object);
        } else {
            entityInstance.setUnindexedProperty(propertyName, object);
        }
    }
    
    public void remove() throws EntityNotFoundException {
        entityInstance.removeProperty(propertyName);
    }
    
    public String getName() {
        return propertyName;
    }
    
    protected EntityBasePropertyAccess<ENTITY> getEntityBasePropertyAccess() {
        return entityInstance;
    }
    
}
