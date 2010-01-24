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

import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityIdBase;
import org.aexlib.gae.datastore.EntityIdFactory;
import org.aexlib.gae.datastore.EntityIndexablePropertyInfo;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfo;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class TestDataType extends EntityIdBase<TestDataType> {
    public static final EntityIdFactory<TestDataType> ID_FACTORY =
        EntityFactory.getEntityIdFactory(TestDataType.class,
            new EntityCreator<TestDataType>() {
        public TestDataType newInstance() {
            return new TestDataType();
        }
    });

    static EntityPropertyInfo<TestDataType, byte[]> BYTES =
        EntityPropertyInfoFactory.getPropertyInfo(TestDataType.class, byte[].class, "Bytes");
    EntityProperty<TestDataType, byte[]> bytes;

    static EntityIndexablePropertyInfo<TestDataType, byte[]> INDEXABLE_BYTES =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(TestDataType.class, byte[].class, "IndexableBytes");
    EntityProperty<TestDataType, byte[]> indexableBytes;

    enum Status {
        NEW,
        REVIEW,
        PUBLISH,
    }
    
    static EntityPropertyInfo<TestDataType, Status> ENUM_DATA =
        EntityPropertyInfoFactory.getPropertyInfo(TestDataType.class, Status.class, "EnumData");
    EntityProperty<TestDataType, Status> enumData;

    static EntityIndexablePropertyInfo<TestDataType, Status> INDEXABLE_ENUM_DATA =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(TestDataType.class, Status.class, "IndexableEnumData");
    EntityProperty<TestDataType, Status> indexableEnumData;

    private TestDataType() {
        bytes = BYTES.newInstance(this.getEntityPropertyAccess());
        indexableBytes = INDEXABLE_BYTES.newInstance(this.getEntityPropertyAccess());

        enumData = ENUM_DATA.newInstance(this.getEntityPropertyAccess());
        indexableEnumData = INDEXABLE_ENUM_DATA.newInstance(this.getEntityPropertyAccess());
    }
    
    public Object getStoredBytesData() throws EntityNotFoundException {
        return this.getEntityPropertyAccess().getProperty("Bytes");
    }

    public Object getStoredIndexableBytesData() throws EntityNotFoundException {
        return this.getEntityPropertyAccess().getProperty("IndexableBytes");
    }


    public Object getStoredEnumData() throws EntityNotFoundException {
        return this.getEntityPropertyAccess().getProperty("EnumData");
    }

    public Object getStoredIndexableEnumData() throws EntityNotFoundException {
        return this.getEntityPropertyAccess().getProperty("IndexableEnumData");
    }
}
