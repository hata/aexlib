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
package org.aexlib.gae.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class GenericEntityBase extends EntityBase<GenericEntityBase> {
    static final EntityBaseFactory<GenericEntityBase> FACTORY =
        new EntityBaseFactory<GenericEntityBase>() {

            public GenericEntityBase initInstance(Key key) {
                GenericEntityBase genericEntity = new GenericEntityBase();
                genericEntity.init(key);
                return genericEntity;
            }

            public GenericEntityBase initInstance(Entity entity) {
                GenericEntityBase genericEntity = new GenericEntityBase();
                genericEntity.init(entity);
                return genericEntity;
            }
    };
    
    private GenericEntityBase() {
        
    }

    public String getKind() {
        return getKey() != null ? getKey().getKind() : null;
    }

    public <ENTITY extends EntityBase<ENTITY>> ENTITY toEntity(EntityBaseFactory<ENTITY> factory) {
        return getKey() != null ? factory.initInstance(getKey()) : null;
    }

    @Override
    protected Entity newEntity() {
        // TODO: Throw an appropriate exception. This class doesn't support to create a new entity.
        return null;
    }
}
