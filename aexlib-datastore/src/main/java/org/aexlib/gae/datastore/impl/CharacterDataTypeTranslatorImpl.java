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
package org.aexlib.gae.datastore.impl;

import org.aexlib.gae.datastore.DataTypeTranslator;

public class CharacterDataTypeTranslatorImpl implements DataTypeTranslator {
    private static final CharacterDataTypeTranslatorImpl instance = new CharacterDataTypeTranslatorImpl();

    /**
     * If userTypeClass is not an applicable class,
     * this method may return null.
     */
    public static DataTypeTranslator getInstance(Class<?> userTypeClass) {
        return userTypeClass.equals(Character.class) ? instance : null;
    }

    public Object toStoreType(Object userData) {
        return userData != null ? Long.valueOf(((Character)userData).charValue()) : null;
    }

    public Object toUserType(Object storedData) {
        if (storedData instanceof Long) {
            return Character.valueOf((char)(0xffff & ((Long)storedData).longValue()));
        } else if (storedData instanceof Character) {
            return storedData;
        } else {
            return null;
        }
    }

}
