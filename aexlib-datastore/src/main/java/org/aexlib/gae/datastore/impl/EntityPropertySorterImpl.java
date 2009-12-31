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
import com.archtea.gae.datastore.EntityPropertySorter;
import com.google.appengine.api.datastore.Query;

public class EntityPropertySorterImpl<ENTITY extends EntityBase<ENTITY>> implements EntityPropertySorter<ENTITY> {
    private final Query.SortDirection direction;
    private final String propertyName;
    
    EntityPropertySorterImpl(String propertyName, Query.SortDirection direction) {
        this.direction = direction;
        this.propertyName = propertyName;
    }

    void addSort(EntityQueryImpl<ENTITY> query) {
        query.addSorter(propertyName, direction);
    }
}
