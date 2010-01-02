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
import java.util.logging.Logger;

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityBaseFactory;
import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityCollectionProperty;
import org.aexlib.gae.datastore.NotSupportedTypeException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public class EntityKeyLinkCollectionPropertyImpl<ENTITY extends EntityBase<ENTITY>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
implements EntityCollectionProperty<ENTITY, COLLECTION_TYPE, PROPERTY_TYPE>  {
    private static final Logger log = Logger.getLogger(EntityKeyLinkCollectionPropertyImpl.class.getName());

    
    private final EntityBasePropertyAccess<ENTITY> entityInstance;
    private final Class<COLLECTION_TYPE> collectionClass;
    private final EntityBaseFactory<PROPERTY_TYPE> propertyFactory;
    private final String propertyName;
    private final boolean indexable;

    public EntityKeyLinkCollectionPropertyImpl(
            EntityBasePropertyAccess<ENTITY> entityInstance,
            Class<COLLECTION_TYPE> propertyClass,
            EntityBaseFactory<PROPERTY_TYPE> propertyFactory,
            String propertyName,
            boolean indexable) {
        this.entityInstance = entityInstance;
        this.collectionClass = propertyClass;
        this.propertyFactory = propertyFactory;
        this.propertyName = propertyName;
        this.indexable = indexable;
    }

    public COLLECTION_TYPE get() throws EntityNotFoundException {
        final Object keys = entityInstance.getProperty(propertyName);
        if (keys == null) {
            return null;
        }
        if (!(keys instanceof Collection)) {
            throw new NotSupportedTypeException("Stored keys are not Collection.");
        }
        try {
            COLLECTION_TYPE values = EntityUtil.getCollectionInstance(collectionClass);
    
            for (Key key : ((Collection<Key>)keys)) {
                values.add(propertyFactory.initInstance(key));
            }
            return values;
        } catch (Throwable t) {
            throw new NotSupportedTypeException("Stored keys are not Collection.", t);
        }
    }

    public void set(COLLECTION_TYPE value) throws EntityNotFoundException {
        if (value == null) {
            setProperty(null);
            return;
        }

        Collection<Key> keys = EntityUtil.newStoredCollection(Key.class, value);
        
        for (PROPERTY_TYPE entity : value) {
            keys.add(entity.getKey());
        }
        setProperty(keys);
    }

    public void remove() throws EntityNotFoundException {
        entityInstance.removeProperty(propertyName);
    }
    

    public String getName() {
        return propertyName;
    }

    private void setProperty(Object value) throws EntityNotFoundException {
        if (indexable) {
            entityInstance.setProperty(propertyName, value);
        } else {
            entityInstance.setUnindexedProperty(propertyName, value);
        }
    }
}