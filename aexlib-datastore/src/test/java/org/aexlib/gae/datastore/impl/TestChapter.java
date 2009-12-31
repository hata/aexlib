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

import com.archtea.gae.datastore.EntityChildIdFactory;
import com.archtea.gae.datastore.EntityChildNameBase;
import com.archtea.gae.datastore.EntityChildNameFactory;
import com.archtea.gae.datastore.EntityCreator;
import com.archtea.gae.datastore.EntityFactory;
import com.archtea.gae.datastore.EntityProperty;
import com.archtea.gae.datastore.EntityPropertyInfoFactory;
import com.archtea.gae.datastore.EntityPropertyInfo;
import com.archtea.gae.datastore.EntityQueryFactory;

public class TestChapter extends EntityChildNameBase<TestChapter, TestDocument> {
    // There are two factory... This is for test purpose.
    // Normally, factory should only have 1 instance.
    public static final EntityChildNameFactory<TestChapter, TestDocument> NAME_FACTORY =
        EntityFactory.getEntityChildNameFactory(TestChapter.class,
            new EntityCreator<TestChapter>() {
        public TestChapter newInstance() {
            return new TestChapter();
        }
    });
    
/*    public static final EntityChildIdFactory<TestChapter, TestDocument> ID_FACTORY =
        EntityFactory.getEntityChildIdFactory(TestChapter.class,
            new EntityCreator<TestChapter>() {
        public TestChapter newInstance() {
            return new TestChapter();
        }
    });
*/
    public static final EntityQueryFactory<TestChapter> QUERY =
        EntityQueryFactory.getInstance(TestChapter.class, NAME_FACTORY);

    static EntityPropertyInfo<TestChapter, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(TestChapter.class, "VERSION", 1);

    static EntityPropertyInfo<TestChapter, String> SUBJECT =
        EntityPropertyInfoFactory.getPropertyInfo(TestChapter.class, String.class, "Subject");

    EntityProperty<TestChapter, Long> version;
    EntityProperty<TestChapter, String> title;
    

    // This should be a private contructor. I changed it to default for testing.
    TestChapter() {
        version = VERSION.newInstance(this.getEntityPropertyAccess());
        title = SUBJECT.newInstance(this.getEntityPropertyAccess());
    }

}
