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

import com.archtea.gae.datastore.impl.EntityQueryImpl;
import com.google.appengine.api.datastore.Query;

public class EntityQueryFactory<ENTITY extends EntityBase<ENTITY>> {
    private final Class<ENTITY> entityClass;
    private final EntityBaseFactory<ENTITY> factory;

    public static <ENTITY extends EntityBase<ENTITY>> EntityQueryFactory<ENTITY> getInstance(Class<ENTITY> entityClass, EntityBaseFactory<ENTITY> factory) {
        return new EntityQueryFactory<ENTITY>(entityClass, factory);
    }

    EntityQueryFactory(Class<ENTITY> entityClass, EntityBaseFactory<ENTITY> factory) {
        this.entityClass = entityClass;
        this.factory = factory;
    }
    
    protected Class<ENTITY> getEntityClass() {
        return entityClass;
    }
    
    protected EntityBaseFactory<ENTITY> getEntityBaseFactory() {
        return factory;
    }
    
    public EntityQuery<ENTITY> query() {
        Query query = new Query(EntityBase.getKindName(entityClass));
        return new EntityQueryImpl<ENTITY>(query, factory);
    }

    public EntityQuery<GenericEntityBase> ancestorQuery(ENTITY parent) {
        Query query = new Query(parent.getKey());
        return new EntityQueryImpl<GenericEntityBase>(query, GenericEntityBase.FACTORY);
    }
}
