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

import com.google.appengine.api.datastore.DataTypeUtils;
import com.google.appengine.api.datastore.Text;

public class TextDataTypeTranslatorImpl implements DataTypeTranslator {
    private static final TextDataTypeTranslatorImpl INSTANCE = new TextDataTypeTranslatorImpl();
    
    public static DataTypeTranslator getInstance(Class<?> userTypeClass) {
        return String.class.equals(userTypeClass) ? INSTANCE : null;
    }
    
    private TextDataTypeTranslatorImpl() {
        
    }
    
    public Object toStoreType(Object userData) {
        if (userData instanceof String && ((String)userData).length() > DataTypeUtils.MAX_STRING_PROPERTY_LENGTH) { // Maybe, shorter value should store as String ...
            return new Text((String)userData);
        } else {
            return userData;
        }
    }

    public Object toUserType(Object storedData) {
        return storedData instanceof Text ? ((Text)storedData).getValue() : storedData;
    }
}
