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
import com.google.appengine.api.datastore.KeyFactory;

public class EntityChildIdBase<ENTITY extends EntityChildIdBase<ENTITY, PARENT_ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>> extends EntityIdBase<ENTITY> implements EntityChildBase<PARENT_ENTITY> {
    private Key parentKey;
    private EntityBaseFactory<PARENT_ENTITY> parentFactory;

    public PARENT_ENTITY getParent() {
        if (parentKey == null || parentFactory == null) {
            return null; // Maybe,,, this should throw an exception.
        }
        return parentFactory.initInstance(parentKey);
    }

    protected Key getParentKey() {
        return parentKey;
    }
    
    protected void init(Key parentKey, String kind) {
        init(kind);
        this.parentKey = parentKey;
    }

    protected void init(Key parentKey, String kind, long id) {
        init(kind);
        super.init(KeyFactory.createKey(parentKey, kind, id));
        this.parentKey = parentKey;
    }

    @Override
    protected void init(Entity entity) {
        parentKey = entity != null ? entity.getKey().getParent() : null;
        super.init(entity);
    }

    @Override
    protected Entity newEntity() {
        return new Entity(getKind(), parentKey);
    }
    
    protected void setParentEntityBaseFactory(EntityBaseFactory<PARENT_ENTITY> parentFactory) {
        this.parentFactory = parentFactory;
    }

}
