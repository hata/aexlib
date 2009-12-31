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


public class EntityVersionPropertyInfoImpl<ENTITY extends EntityBase<ENTITY>> extends
        EntityIndexablePropertyInfoImpl<ENTITY, Long> {

    private long version;

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>>
    EntityVersionPropertyInfoImpl<ENTITY_CLASS>
            getInstance(Class<ENTITY_CLASS> entityClass, String propertyName, long version) {
        return new EntityVersionPropertyInfoImpl<ENTITY_CLASS>(entityClass, propertyName, version);
    }


    public EntityVersionPropertyInfoImpl(Class<ENTITY> entityClass,
           String propertyName, long version) {
        super(entityClass, Long.class, propertyName);
        this.version = version;
    }


    @Override
    public EntityPropertyImpl<ENTITY, Long> newInstance(
            EntityBasePropertyAccess<ENTITY> entityInstance) {
        return new EntityVersionPropertyImpl<ENTITY>(entityInstance, getName(), version);
    }

}
