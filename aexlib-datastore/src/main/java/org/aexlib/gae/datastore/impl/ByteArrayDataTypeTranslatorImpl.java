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

import com.google.appengine.api.datastore.Blob;

public class ByteArrayDataTypeTranslatorImpl implements DataTypeTranslator {
    private static final ByteArrayDataTypeTranslatorImpl translator =
        new ByteArrayDataTypeTranslatorImpl();

    public static DataTypeTranslator getInstance(Class<?> userTypeClass) {
        return userTypeClass != null && userTypeClass.equals(byte[].class) ? translator : null;
    }

    public Object toStoreType(Object userData) {
        return userData != null ? new Blob((byte[])userData) : null;
    }

    public Object toUserType(Object storedData) {
        return storedData != null ? ((Blob)storedData).getBytes() : null;
    }

}
