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

import java.util.Iterator;
import java.util.List;



public interface EntityQuery<ENTITY extends EntityBase<ENTITY>> extends Iterable<ENTITY> {
    public <PROPERTY_TYPE> EntityQuery<ENTITY> filter(EntityPropertyFilter<ENTITY, PROPERTY_TYPE> filter);
    public EntityQuery<ENTITY> sort(EntityPropertySorter<ENTITY> sort);

    public EntityQuery<ENTITY> limit(int limit);
    public EntityQuery<ENTITY> offset(int offset);
    public EntityQuery<ENTITY> chunkSize(int chunkSize);
    public EntityQuery<ENTITY> prefetchSize(int prefetchSize);

    public Iterable<ENTITY> asIterable();
    public Iterator<ENTITY> asIterator();
    public List<ENTITY> asList();
    
    public ENTITY asSingleEntity();
    public int countEntities();
}
