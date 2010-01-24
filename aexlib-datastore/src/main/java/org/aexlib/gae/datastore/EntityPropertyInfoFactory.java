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

import java.util.Collection;

import org.aexlib.gae.datastore.impl.DataTypeTranslatorFactory;
import org.aexlib.gae.datastore.impl.EntityCollectionPropertyInfoImpl;
import org.aexlib.gae.datastore.impl.EntityIndexableCollectionPropertyInfoImpl;
import org.aexlib.gae.datastore.impl.EntityIndexablePropertyInfoImpl;
import org.aexlib.gae.datastore.impl.EntityPropertyInfoImpl;
import org.aexlib.gae.datastore.impl.EntityVersionPropertyInfoImpl;
import org.aexlib.gae.datastore.impl.KeyLinkDataTypeTranslatorImpl;


public class EntityPropertyInfoFactory {
    /**
     * Get EntityPropertyInfo instance.
     * If a propertyClass is String, the object may be stored as Text.
     * If a propertyClass is not supported class and implements Serializable,
     * the instance is stored as Blob.
     * If a propertyClass is Number objects, the value is stored as Long or Double.
     * This class supports to convert from Long/Double to other types like Short/Float
     * automatically.
     * @param entityClass is an owned class which derived from EntityBase.
     * @param propertyClass is a propertyClass to be stored.
     * @param propertyName is a propertyName.
     */
    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, PROPERTY_TYPE>
    EntityPropertyInfo<ENTITY_CLASS, PROPERTY_TYPE>
    getPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<PROPERTY_TYPE> propertyClass, String propertyName) {
        return getPropertyInfo(entityClass, propertyClass,
                DataTypeTranslatorFactory.getUnindexedTranslator(propertyClass), propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, PROPERTY_TYPE>
    EntityPropertyInfo<ENTITY_CLASS, PROPERTY_TYPE>
    getPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<PROPERTY_TYPE> propertyClass,
            DataTypeTranslator translator, String propertyName) {
        return EntityPropertyInfoImpl.getInstance(entityClass, propertyClass, translator, propertyName);
    }

    /**
     * Get EntityIndexablePropertyInfo instance.
     * The returned object provides a way to sort/filter in query.
     * This class doesn't convert String object to Text if it is more than 501 bytes.
     * This class doesn't store Serializable object.
     * This class will convert Number instances automatically.
     */
    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, PROPERTY_TYPE>
    EntityIndexablePropertyInfo<ENTITY_CLASS, PROPERTY_TYPE>
    getIndexablePropertyInfo(Class<ENTITY_CLASS> entityClass, Class<PROPERTY_TYPE> propertyClass, String propertyName) {
        return getIndexablePropertyInfo(entityClass, propertyClass,
                DataTypeTranslatorFactory.getIndexableTranslator(propertyClass), propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, PROPERTY_TYPE>
    EntityIndexablePropertyInfo<ENTITY_CLASS, PROPERTY_TYPE>
    getIndexablePropertyInfo(Class<ENTITY_CLASS> entityClass, Class<PROPERTY_TYPE> propertyClass,
            DataTypeTranslator translator, String propertyName) {
        return EntityIndexablePropertyInfoImpl.getInstance(entityClass, propertyClass, translator, propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>>
    EntityIndexablePropertyInfo<ENTITY_CLASS, Long>
    getVersionPropertyInfo(Class<ENTITY_CLASS> entityClass, String propertyName, long currentVersion) {
        return EntityVersionPropertyInfoImpl.getInstance(entityClass, propertyName, currentVersion);
    }
 
    
    /**
     * Get EntityCollectionPropertyInfo instance.
     * @param entityClass is a class object of EntityBase.
     * @param collectionClass is a class object for Collection like List, Set, and so on.
     * @param propertyClass is a property class like String.
     * @param propertyName is a property name to be stored in datastore.
     * @return EntityCollectionPropertyInfo instance.
     */
    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    EntityCollectionPropertyInfo<ENTITY_CLASS, COLLECTION_TYPE, PROPERTY_TYPE>
    getCollectionPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> propertyClass, String propertyName) {
        return getCollectionPropertyInfo(entityClass, collectionClass, propertyClass,
                DataTypeTranslatorFactory.getUnindexedTranslator(propertyClass), propertyName);
    }

    /**
     * Get EntityCollectionPropertyInfo instance with translator.
     * @param entityClass is a class object of EntityBase.
     * @param collectionClass is a class object for Collection like List, Set, and so on.
     * @param propertyClass is a property class like String.
     * @param translator is a data converter between a property class and datastore.
     * @param propertyName is a property name to be stored in datastore.
     * @return EntityCollectionPropertyInfo instance.
     */
    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    EntityCollectionPropertyInfo<ENTITY_CLASS, COLLECTION_TYPE, PROPERTY_TYPE>
    getCollectionPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<COLLECTION_TYPE> collectionClass,
            Class<PROPERTY_TYPE> propertyClass, DataTypeTranslator translator, String propertyName) {
        return EntityCollectionPropertyInfoImpl.getInstance(entityClass, collectionClass, propertyClass,
                translator, propertyName);
    }

    /**
     * Get EntityIndexableCollectionPropertyInfo.
     * @param entityClass is a class object of EntityBase.
     * @param collectionClass is a class object for Collection like List, Set, and so on.
     * @param propertyClass is a property class like String.
     * @param propertyName is a property name to be stored in datastore.
     * @return EntityCollectionPropertyInfo instance.
     */
    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    EntityIndexableCollectionPropertyInfo<ENTITY_CLASS, COLLECTION_TYPE, PROPERTY_TYPE>
    getIndexableCollectionPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> propertyClass, String propertyName) {
        return getIndexableCollectionPropertyInfo(entityClass, collectionClass, propertyClass,
                DataTypeTranslatorFactory.getIndexableTranslator(propertyClass), propertyName);
    }

    /**
     * Get EntityIndexableCollectionPropertyInfo with a translator.
     * @param entityClass is a class object of EntityBase.
     * @param collectionClass is a class object for Collection like List, Set, and so on.
     * @param propertyClass is a property class like String.
     * @param translator is a data converter between a property class and datastore.
     * @param propertyName is a property name to be stored in datastore.
     * @return EntityCollectionPropertyInfo instance.
     */
    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE>
    EntityIndexableCollectionPropertyInfo<ENTITY_CLASS, COLLECTION_TYPE, PROPERTY_TYPE>
    getIndexableCollectionPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<COLLECTION_TYPE> collectionClass,
            Class<PROPERTY_TYPE> propertyClass, DataTypeTranslator translator, String propertyName) {
        return EntityIndexableCollectionPropertyInfoImpl.getInstance(entityClass, collectionClass, propertyClass, translator, propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
    EntityPropertyInfo<ENTITY_CLASS, PROPERTY_TYPE>
    getKeyLinkPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<PROPERTY_TYPE> propertyClass, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        final DataTypeTranslator translator = new KeyLinkDataTypeTranslatorImpl<PROPERTY_TYPE>(propertyClass, propertyFactory);
        return getPropertyInfo(entityClass, propertyClass, translator, propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
    EntityIndexablePropertyInfo<ENTITY_CLASS, PROPERTY_TYPE>
    getIndexableKeyLinkPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<PROPERTY_TYPE> propertyClass, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        final DataTypeTranslator translator = new KeyLinkDataTypeTranslatorImpl<PROPERTY_TYPE>(propertyClass, propertyFactory);
        return getIndexablePropertyInfo(entityClass, propertyClass, translator, propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
    EntityCollectionPropertyInfo<ENTITY_CLASS, COLLECTION_TYPE, PROPERTY_TYPE>
    getKeyLinkCollectionPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> propertyClass, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        final DataTypeTranslator translator = new KeyLinkDataTypeTranslatorImpl<PROPERTY_TYPE>(propertyClass, propertyFactory);
        return getCollectionPropertyInfo(entityClass, collectionClass, propertyClass, translator, propertyName);
    }

    public static <ENTITY_CLASS extends EntityBase<ENTITY_CLASS>, COLLECTION_TYPE extends Collection<PROPERTY_TYPE>, PROPERTY_TYPE extends EntityBase<PROPERTY_TYPE>>
    EntityIndexableCollectionPropertyInfo<ENTITY_CLASS, COLLECTION_TYPE, PROPERTY_TYPE>
    getIndexableKeyLinkCollectionPropertyInfo(Class<ENTITY_CLASS> entityClass, Class<COLLECTION_TYPE> collectionClass, Class<PROPERTY_TYPE> propertyClass, EntityBaseFactory<PROPERTY_TYPE> propertyFactory, String propertyName) {
        final DataTypeTranslator translator = new KeyLinkDataTypeTranslatorImpl<PROPERTY_TYPE>(propertyClass, propertyFactory);
        return getIndexableCollectionPropertyInfo(entityClass, collectionClass, propertyClass, translator, propertyName);
    }
}
