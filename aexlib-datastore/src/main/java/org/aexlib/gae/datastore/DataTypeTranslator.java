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

/**
 * This interface defines a conversion between a user application data
 * to datastored data. This interface is used when get or set data from/to datastore.
 * And when query filter is used, this translator is also used.
 */
public interface DataTypeTranslator {
    /**
     * Change userData which is set from a user application to datastore's data type instance.
     * @param userData is a user application's input data.
     * @return an instance to be stored into datastore.
     */
    public Object toStoreType(Object userData);

    /**
     * Change a stored data to userData.
     * @param storedData is got from datastore.
     * @return an instance from storedData instance to return a user application.
     */
    public Object toUserType(Object storedData);
}
