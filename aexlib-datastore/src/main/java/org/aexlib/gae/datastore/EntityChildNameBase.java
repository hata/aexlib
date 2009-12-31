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

public class EntityChildNameBase<ENTITY extends EntityChildNameBase<ENTITY, PARENT_ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>> extends EntityNameBase<ENTITY> implements EntityChildBase<PARENT_ENTITY> {
    private Key parentKey;
    private EntityBaseFactory<PARENT_ENTITY> parentFactory;

    public PARENT_ENTITY getParent() {
        if (parentFactory == null || parentKey == null) {
            return null; // TODO: throw an exception ?
        }
        return parentFactory.initInstance(parentKey);
    }

    public Key getParentKey() {
        return parentKey;
    }
    
    @Override
    protected void init(Key key) {
        this.parentKey = key != null ? key.getParent() : null;
        super.init(key);
    }
    
/*
 *     void init(Key parentKey, String kind) {
 
        this.parentKey = parentKey;
        super.init(kind);
    }
*/
    @Override
    protected void init(Entity entity) {
        parentKey = entity != null ? entity.getKey().getParent() : null;
        super.init(entity);
    }


    protected void setParentEntityBaseFactory(EntityBaseFactory<PARENT_ENTITY> parentFactory) {
        this.parentFactory = parentFactory;
    }
}
