/*
 * $Id$
 * 
 * Copyright 2009-${year} Hiroki Ata
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

import com.google.appengine.api.datastore.Cursor;

/**
 * This interface provides a feature for Cursor for query.
 * 
 * 
 */
public interface EntityResultQuery<ENTITY extends EntityBase<ENTITY>> extends EntityQuery<ENTITY> {
    public <PROPERTY_TYPE> EntityResultQuery<ENTITY> filter(EntityPropertyFilter<ENTITY, PROPERTY_TYPE> filter);
    public EntityResultQuery<ENTITY> sort(EntityPropertySorter<ENTITY> sort);
    
    /**
     * Call Query#setKeysOnly().
     */
    public EntityResultQuery<ENTITY> setKeysOnly();

    public EntityResultQuery<ENTITY> limit(int limit);
    public EntityResultQuery<ENTITY> offset(int offset);
    public EntityResultQuery<ENTITY> chunkSize(int chunkSize);
    public EntityResultQuery<ENTITY> prefetchSize(int prefetchSize);
    public EntityResultQuery<ENTITY> cursor(Cursor cursor);

    public EntityResultIterable<ENTITY> asIterable();
    public EntityResultIterator<ENTITY> asIterator();
    public EntityResultList<ENTITY> asList();

}
