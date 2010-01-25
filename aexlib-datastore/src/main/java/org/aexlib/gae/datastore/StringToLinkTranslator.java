/*
 * $Id$
 * 
 * Copyright 2010 Hiroki Ata
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

import com.google.appengine.api.datastore.Link;

/**
 * If Link is used for stored data and user can access this property
 * as String, this translator should be set.
 */
public class StringToLinkTranslator implements DataTypeTranslator {

    public Object toStoreType(Object userData) {
        if (userData instanceof String) {
            return new Link((String)userData);
        } else if (userData instanceof Link) {
            return (Link)userData;
        } else {
            return null;
        }
    }

    public Object toUserType(Object storedData) {
        if (storedData instanceof String) {
            return storedData;
        } else if (storedData instanceof Link) {
            return ((Link)storedData).getValue();
        } else {
            return null;
        }
    }

}
