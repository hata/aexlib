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

import java.util.concurrent.ConcurrentHashMap;

import org.aexlib.gae.datastore.DataTypeTranslator;

public class EnumDataTypeTranslatorImpl implements DataTypeTranslator {
    private static final ConcurrentHashMap<Class<?>, EnumDataTypeTranslatorImpl> instances =
        new ConcurrentHashMap<Class<?>, EnumDataTypeTranslatorImpl>();

    private final Class<?> userTypeClass;

    public static DataTypeTranslator getInstance(Class<?> userTypeClass) {
        if (!userTypeClass.isEnum()) {
            return null;
        }

        EnumDataTypeTranslatorImpl inst = instances.get(userTypeClass);
        if (inst == null) {
            instances.putIfAbsent(userTypeClass, inst = new EnumDataTypeTranslatorImpl(userTypeClass));
        }
        return inst;
    }

    private EnumDataTypeTranslatorImpl(Class<?> userTypeClass) {
        this.userTypeClass = userTypeClass;
    }

    public Object toStoreType(Object userData) {
        return userData != null ? userData.toString() : null;
    }

    public Object toUserType(Object storedData) {
        if (storedData == null || userTypeClass.getEnumConstants() == null) {
            return null;
        }

        for (Object o : userTypeClass.getEnumConstants()) {
            if (o != null && storedData.equals(o.toString())) {
                return o;
            }
        }

        return null;
    }

}
