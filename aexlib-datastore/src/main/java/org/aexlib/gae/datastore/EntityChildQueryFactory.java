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

import org.aexlib.gae.datastore.impl.EntityQueryImpl;

import com.google.appengine.api.datastore.Query;

public class EntityChildQueryFactory<ENTITY extends EntityBase<ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>> extends EntityQueryFactory<ENTITY> {

    public static <ENTITY extends EntityBase<ENTITY>,
                   PARENT_ENTITY extends EntityBase<PARENT_ENTITY>>
    EntityChildQueryFactory<ENTITY, PARENT_ENTITY> getInstance(Class<ENTITY> entityClass, EntityChildBaseFactory<ENTITY, PARENT_ENTITY> factory) {
        return new EntityChildQueryFactory<ENTITY, PARENT_ENTITY>(entityClass, factory);
    }

    private EntityChildQueryFactory(Class<ENTITY> entityClass, EntityBaseFactory<ENTITY> factory) {
        super(entityClass, factory);
    }
    
    public EntityQuery<ENTITY> query(PARENT_ENTITY parent) {
        return resultQuery(parent);
    }

    public EntityResultQuery<ENTITY> resultQuery(PARENT_ENTITY parent) {
        Query query = new Query(EntityBase.getKindName(getEntityClass()), parent.getKey());
        return new EntityQueryImpl<ENTITY>(query, getEntityBaseFactory());
    }
}
