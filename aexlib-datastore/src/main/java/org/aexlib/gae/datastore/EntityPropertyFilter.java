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



public interface EntityPropertyFilter<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE> {
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> equal(PROPERTY_TYPE value);
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> lessThan(PROPERTY_TYPE value);
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> lessThanOrEqual(PROPERTY_TYPE value);
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> greaterThan(PROPERTY_TYPE value);
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> greaterThanOrEqual(PROPERTY_TYPE value);
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> in(PROPERTY_TYPE value);
    EntityPropertyFilter<ENTITY, PROPERTY_TYPE> not_equal(PROPERTY_TYPE value);
    
}
