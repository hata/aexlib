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

import org.aexlib.gae.datastore.EntityChildIdBase;
import org.aexlib.gae.datastore.EntityChildIdFactory;
import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfo;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;
import org.aexlib.gae.datastore.EntityQueryFactory;

public class TestIdPage extends EntityChildIdBase<TestIdPage, TestDocument>  {

    public static final EntityChildIdFactory<TestIdPage, TestDocument> ID_FACTORY =
        EntityFactory.getEntityChildIdFactory(TestIdPage.class,
            new EntityCreator<TestIdPage>() {
        public TestIdPage newInstance() {
            return new TestIdPage();
        }
        });

    public static final EntityQueryFactory<TestIdPage> QUERY =
        EntityQueryFactory.getInstance(TestIdPage.class, ID_FACTORY);

    static EntityPropertyInfo<TestIdPage, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(TestIdPage.class, "VERSION", 1);

    static EntityPropertyInfo<TestIdPage, String> SUBJECT =
        EntityPropertyInfoFactory.getPropertyInfo(TestIdPage.class, String.class, "Subject");

    EntityProperty<TestIdPage, Long> version;
    EntityProperty<TestIdPage, String> title;

    // This should be a private contructor. I changed it to default for testing.
    TestIdPage() {
        version = VERSION.newInstance(this.getEntityPropertyAccess());
        title = SUBJECT.newInstance(this.getEntityPropertyAccess());
    }
}
