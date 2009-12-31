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
package com.archtea.gae.datastore.impl;

import com.archtea.gae.datastore.EntityBase;
import com.archtea.gae.datastore.EntityBasePropertyAccess;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class EntityVersionPropertyImpl<ENTITY extends EntityBase<ENTITY>> extends
        EntityPropertyImpl<ENTITY, Long> {

    EntityVersionPropertyImpl(EntityBasePropertyAccess<ENTITY> entityInstance, String propertyName, long version) {
        super(entityInstance, Long.class, propertyName, null);
        entityInstance.setVersion(propertyName, version);
    }

    @Override
    public void set(Long value) throws EntityNotFoundException {
        // TODO: This method should not be allowed.
        // TODO: So, ignore updating version from here.
    }

    @Override
    public Long get() throws EntityNotFoundException {
        return Long.class.cast(getEntityBasePropertyAccess().getProperty(getName()));
    }


    public void setVersionForce(Long value) throws EntityNotFoundException {
        super.set(value);
    }
}
