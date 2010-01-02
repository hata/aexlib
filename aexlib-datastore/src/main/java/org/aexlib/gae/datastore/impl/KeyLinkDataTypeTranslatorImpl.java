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

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityBaseFactory;

import com.google.appengine.api.datastore.Key;

class KeyLinkDataTypeTranslatorImpl<PROP_ENTITY extends EntityBase<PROP_ENTITY>> implements DataTypeTranslator {

    private final EntityBaseFactory<PROP_ENTITY> propertyFactory;
    private final Class<PROP_ENTITY> typeClass;
    
    KeyLinkDataTypeTranslatorImpl(Class<PROP_ENTITY> typeClass, EntityBaseFactory<PROP_ENTITY> propertyFactory) {
        this.propertyFactory = propertyFactory;
        this.typeClass = typeClass;
    }
    
    public Object toStoreType(Object userData) {
        return typeClass.cast(userData).getKey();
    }

    public Object toUserType(Object storedData) {
        return propertyFactory.initInstance((Key)storedData);
    }

}
