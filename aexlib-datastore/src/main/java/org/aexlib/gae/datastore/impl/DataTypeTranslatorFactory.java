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
package org.aexlib.gae.datastore.impl;

import org.aexlib.gae.datastore.DataTypeTranslator;

import com.google.appengine.api.datastore.DataTypeUtils;

public class DataTypeTranslatorFactory {
    public static DataTypeTranslator getIndexableTranslator(Class<?> propertyClass) {
        if (propertyClass.isEnum()) {
            return EnumDataTypeTranslatorImpl.getInstance(propertyClass);
        } else {
            DataTypeTranslator translator = NumberDataTypeTranslatorImpl.getInstance(propertyClass);
            if (translator != null) {
                return translator;
            }
            translator = IndexableByteArrayDataTypeTranslatorImpl.getInstance(propertyClass);
            if (translator != null) {
                return translator;
            }
        }

        return null;
    }

    public static DataTypeTranslator getUnindexedTranslator(Class<?> propertyClass) {
        if (propertyClass.isEnum()) {
            return EnumDataTypeTranslatorImpl.getInstance(propertyClass);
        }

        DataTypeTranslator translator = NumberDataTypeTranslatorImpl.getInstance(propertyClass);
        if (translator != null) {
            return translator;
        }

        translator = ByteArrayDataTypeTranslatorImpl.getInstance(propertyClass);
        if (translator != null) {
            return translator;
        }
        
        // Unindexed String can replace with Text because it is not searched.
        translator = TextDataTypeTranslatorImpl.getInstance(propertyClass);
        if (translator != null) {
            return translator;
        }
        
        if (DataTypeUtils.isSupportedType(propertyClass)) {
            return null;
        }
        
        return SerializableDataTypeTranslatorImpl.getInstance(propertyClass);
    }
    
    private DataTypeTranslatorFactory() {
        
    }
}
