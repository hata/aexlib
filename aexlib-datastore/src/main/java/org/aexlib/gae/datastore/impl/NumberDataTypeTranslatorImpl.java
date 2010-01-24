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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.aexlib.gae.datastore.DataTypeTranslator;
import org.aexlib.gae.datastore.NotSupportedTypeException;


public class NumberDataTypeTranslatorImpl implements DataTypeTranslator {
    // TODO: Number is fixed size. So, this can prepare before ruuning.
    private static final ConcurrentHashMap<Class<?>, NumberDataTypeTranslatorImpl> INSTANCES =
        new ConcurrentHashMap<Class<?>, NumberDataTypeTranslatorImpl>();
    
    private static final Set<Class<?>> NUMBER_CLASSES = new HashSet<Class<?>>();
    
    static {
        NUMBER_CLASSES.add(Number.class);
        NUMBER_CLASSES.add(Byte.class);
        NUMBER_CLASSES.add(Short.class);
        NUMBER_CLASSES.add(Integer.class);
        NUMBER_CLASSES.add(Long.class);
        NUMBER_CLASSES.add(Float.class);
        NUMBER_CLASSES.add(Double.class);
    }

    /**
     * If userTypeClass is not an applicable class,
     * this method may return null.
     */
    public static DataTypeTranslator getInstance(Class<?> userTypeClass) {
        if (!NUMBER_CLASSES.contains(userTypeClass)) {
            return null;
        }

        NumberDataTypeTranslatorImpl inst = INSTANCES.get(userTypeClass);
        if (inst == null) {
            INSTANCES.putIfAbsent(userTypeClass, inst = new NumberDataTypeTranslatorImpl(userTypeClass));
        }
        return inst;
    }
    
    private Class<?> userTypeClass;

    public NumberDataTypeTranslatorImpl(Class<?> userTypeClass) {
        this.userTypeClass = userTypeClass;
    }

    public Object toStoreType(Object userData) {
        return userData;
    }

    public Object toUserType(Object storedData) {
        if (userTypeClass.equals(storedData.getClass())) {
            return storedData;
        } else if (userTypeClass.equals(Byte.class)) {
            return Byte.valueOf(Long.class.cast(storedData).byteValue());
        } else if (userTypeClass.equals(Short.class)) {
            return Short.valueOf(Long.class.cast(storedData).shortValue());
        } else if (userTypeClass.equals(Integer.class)) {
            return Integer.valueOf(Long.class.cast(storedData).intValue());
        } else if (userTypeClass.equals(Float.class)) {
            return Float.valueOf(Double.class.cast(storedData).floatValue());
        }
        throw new NotSupportedTypeException("There is no matched number class for " + storedData.getClass());
    }

}
