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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.archtea.gae.datastore.NotSupportedTypeException;
import com.google.appengine.api.datastore.Blob;

public class SerializableDataTypeTranslatorImpl implements DataTypeTranslator {
    private static final ConcurrentHashMap<Class<?>, SerializableDataTypeTranslatorImpl> instances =
        new ConcurrentHashMap<Class<?>, SerializableDataTypeTranslatorImpl>();
    
    public static DataTypeTranslator getInstance(Class<?> userTypeClass) {
        if (!EntityUtil.isDerivedClass(userTypeClass, Serializable.class)) {
            return null;
        }
        SerializableDataTypeTranslatorImpl inst = instances.get(userTypeClass);
        if (inst == null) {
            instances.putIfAbsent(userTypeClass, inst = new SerializableDataTypeTranslatorImpl(userTypeClass));
        }
        return inst;
    }
    
    
    private final Class<?> userTypeClass;

    private SerializableDataTypeTranslatorImpl(Class<?> userTypeClass) {
        this.userTypeClass = userTypeClass;
    }
    

    public Object toStoreType(final Object userData) {
        // TODO: ByteArrayOutputStream is high cost.
        try {
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            final ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(userData);
            oout.close();
            return new Blob(bout.toByteArray());
        } catch (Exception e) {
            throw new NotSupportedTypeException(e);
        }
    }

    public Object toUserType(final Object value) {
        try {
            final Blob blob = (Blob)value;
            final ObjectInputStream oin = new ObjectInputStream(
                    new ByteArrayInputStream(blob.getBytes()));
            final Object object = oin.readObject();
            oin.close();
            return userTypeClass.cast(object);
        } catch (Exception e) {
            throw new NotSupportedTypeException(e);
        }
    }

}
