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

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * This implementation supports to convert Number objects from
 * Byte to Long and vise-vasa.
 * And if PROPERTY_TYPE is String and it is not indexed,
 * the object may be stored as Text if the text is long.
 * If PROPERTY_TYPE is not supported instances but Serializable
 * object and a property is not indexed, it will be stored as
 * Blob object to use ObjectOutputStream/ObjectInputStream.
 */
public interface EntityProperty<ENTITY extends EntityBase<ENTITY>, PROPERTY_TYPE> {
    public PROPERTY_TYPE get() throws EntityNotFoundException;
    public void set(PROPERTY_TYPE value) throws EntityNotFoundException;
    public void remove() throws EntityNotFoundException;
    public String getName();
}
