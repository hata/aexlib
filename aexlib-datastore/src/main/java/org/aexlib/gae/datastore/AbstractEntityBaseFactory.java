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
package com.archtea.gae.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

abstract class AbstractEntityBaseFactory<ENTITY extends EntityBase<ENTITY>> implements EntityBaseFactory<ENTITY> {
    private final EntityCreator<ENTITY> creator;

    AbstractEntityBaseFactory(EntityCreator<ENTITY> creator) {
        this.creator = creator;
    }

    protected void init(ENTITY entity, Key key) {
        entity.init(key);
    }
    
    protected void init(ENTITY entity, Entity data) {
        entity.init(data);
    }
    
/*    protected <ENTITY extends EntityChildIdBase<ENTITY, PARENT_ENTITY>,
               PARENT_ENTITY extends EntityBase<PARENT_ENTITY>>
    void init(ENTITY entity, PARENT_ENTITY parentEntity, String kind) {
        entity.init(parentEntity.getKey(), kind);
    }
*/
    
    protected EntityCreator<ENTITY> getCreator() {
        return creator;
    }
    
    public ENTITY initInstance(Key key) {
        ENTITY entity;
        init(entity = creator.newInstance(), key);
        return entity;
    }
    
    public ENTITY initInstance(Entity entity) {
        ENTITY ret = creator.newInstance();
        init(ret, entity);
        return ret;
    }

}
