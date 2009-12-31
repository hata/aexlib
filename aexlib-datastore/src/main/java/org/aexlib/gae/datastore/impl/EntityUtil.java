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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.archtea.gae.datastore.NotSupportedTypeException;

final class EntityUtil {
    private static final Logger log = Logger.getLogger(EntityUtil.class.getName());

    static <COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    COLLECTION_TYPE getCollectionInstance(Class<COLLECTION_TYPE> collectionClass/*, Class<PROPERTY_TYPE> typeClass*/) throws InstantiationException, IllegalAccessException {
        if (collectionClass.equals(SortedSet.class)) {
            return collectionClass.cast(new TreeSet<PROPERTY_TYPE>());
        } else if (collectionClass.equals(Set.class)) {
            return collectionClass.cast(new HashSet<PROPERTY_TYPE>());
        } else if (collectionClass.equals(List.class)) {
            return collectionClass.cast(new ArrayList<PROPERTY_TYPE>());
        } else if (collectionClass.equals(Collection.class)) {
            return collectionClass.cast(new ArrayList<PROPERTY_TYPE>());
        } else if (collectionClass.isInterface()) {
            throw new NotSupportedTypeException("This interface, " + collectionClass + " is not supported.");
        } else {
            return collectionClass.newInstance();
        }
    }
    
    static <T> Collection<T> newStoredCollection(Class<T> clazz, Collection<?> instance) {
        Collection<T> newCollection;
        if (instance instanceof SortedSet) { // TODO: wrong code ??? use instanceof for template inst..
            newCollection = new TreeSet<T>();
        } else if (instance instanceof Set) {
            newCollection = new HashSet<T>();
        } else if (instance instanceof List) {
            newCollection = new ArrayList<T>();
        } else {
            log.warning("Collection class is not SortedSet, Set, and List. It is " + instance.getClass());
            newCollection = new ArrayList<T>();
            // TODO: Should this throw an exception ?
        }
        return newCollection;
    }
    
    static boolean isDerivedClass(Class<?> typeClass, Class<?> lookupClassOrInterface) {
        if (typeClass.equals(lookupClassOrInterface)) {
            return true;
        } else {
            Class<?> parentClass = typeClass.getSuperclass();
            if (parentClass != null) {
                if (isDerivedClass(parentClass, lookupClassOrInterface)) {
                    return true;
                }
            }

            Class<?>[] interfaces = typeClass.getInterfaces();
            if (interfaces.length > 0) {
                for (int i = 0;i < interfaces.length;i++) {
                    if (isDerivedClass(interfaces[i], lookupClassOrInterface)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private EntityUtil() {
        
    }
}
