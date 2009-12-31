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



public interface EntityQuery<ENTITY extends EntityBase<ENTITY>> extends Iterable<ENTITY> {
    public <PROPERTY_TYPE> EntityQuery<ENTITY> filter(EntityPropertyFilter<ENTITY, PROPERTY_TYPE> filter);
    public EntityQuery<ENTITY> sort(EntityPropertySorter<ENTITY> sort);

    public Iterable<ENTITY> asIterable();
    public Iterable<ENTITY> asIterable(EntityQueryFetchOptions options);
    public Iterator<ENTITY> asIterator();
    public Iterator<ENTITY> asIterator(EntityQueryFetchOptions options);
    
    public ENTITY asSingleEntity();
    public int countEntities();
}
