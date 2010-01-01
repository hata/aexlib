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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Logger;

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityCollectionProperty;
import org.aexlib.gae.datastore.NotSupportedTypeException;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class EntityCollectionPropertyImpl<ENTITY extends EntityBase<ENTITY>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
implements EntityCollectionProperty<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>  {
    private static final Logger log = Logger.getLogger(EntityCollectionProperty.class.getName());

    // private Class<ENTITY> entityClass;
    private final String propertyName;
    private final EntityBasePropertyAccess<ENTITY> entityInstance;
    private final Class<COLLECTION_TYPE> collectionClass;
    private final Class<PROPERTY_TYPE> typeClass;
    private final DataTypeTranslator translator;
    private final boolean indexable;

    EntityCollectionPropertyImpl(EntityBasePropertyAccess<ENTITY> entityInstance,
            Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> typeClass,
            String propertyName, DataTypeTranslator translator, boolean indexable) {
        this.entityInstance = entityInstance;
        this.propertyName = propertyName;
        this.collectionClass = collectionClass;
        this.typeClass = typeClass;
        this.translator = translator;
        this.indexable = indexable;
    }
    
    public COLLECTION_TYPE get() throws EntityNotFoundException {
        Object object = entityInstance.getProperty(propertyName);
        if (object == null) {
            return null;
        }
        
        try {
            Collection<Object> values = (Collection<Object>)object;
            if (translator != null && !values.isEmpty()) {
                COLLECTION_TYPE col = EntityUtil.getCollectionInstance(collectionClass);
                for (Object val : values) {
                    col.add(typeClass.cast(translator.toUserType(val)));
                }
                return col;
            }
    
            if (collectionClass.equals(object.getClass())) {
                return collectionClass.cast(object);
            }

            COLLECTION_TYPE colValues = EntityUtil.getCollectionInstance(collectionClass);
            colValues.addAll(Collection.class.cast(object));
            return colValues;
        } catch (Throwable t) {
            throw new NotSupportedTypeException("Stored value is not Collection.", t);
        }
    }

    public void set(COLLECTION_TYPE value) throws EntityNotFoundException {
        if (value == null) {
            setProperty(propertyName, null);
        }

        if (translator != null && !value.isEmpty()) {
            Collection<Object> colValues = EntityUtil.newStoredCollection(Object.class, value);
            for (PROPERTY_TYPE prop : value) {
                colValues.add(translator.toStoreType(prop));
            }
            setProperty(propertyName, colValues);
            return;
        }
        
        if (value instanceof SortedSet || value instanceof Set || value instanceof List) {
            setProperty(propertyName, value);
        } else if (value instanceof Collection && collectionClass.equals(Collection.class)) {
            ArrayList<PROPERTY_TYPE> colValue = new ArrayList<PROPERTY_TYPE>();
            colValue.addAll(value);
            setProperty(propertyName, colValue);
        } else {
            log.severe("Cannot convert Collection value to store data. Class is " + value.getClass());
            throw new NotSupportedTypeException("Cannot convert Collection value to store data. Class is " + value.getClass());
        }
    }
    
    public String getName() {
        return propertyName;
    }
    
    private void setProperty(String name, Object value) throws EntityNotFoundException {
        if (indexable) {
            entityInstance.setProperty(propertyName, value);
        } else {
            entityInstance.setUnindexedProperty(propertyName, value);
        }
    }
}
