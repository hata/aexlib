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
package com.archtea.gae.datastore;

import java.util.List;

import com.archtea.gae.datastore.EntityCreator;
import com.archtea.gae.datastore.EntityFactory;
import com.archtea.gae.datastore.EntityNameFactory;
import com.archtea.gae.datastore.EntityProperty;
import com.archtea.gae.datastore.EntityPropertyInfoFactory;
import com.archtea.gae.datastore.EntityPropertyInfo;
import com.archtea.gae.datastore.EntityQueryFactory;

public class TestUser extends EntityNameBase<TestUser> {
    public static final EntityNameFactory<TestUser> FACTORY =
        EntityFactory.getEntityNameFactory(TestUser.class,
            new EntityCreator<TestUser>() {
        public TestUser newInstance() {
            return new TestUser();
        }
    });

    public static final EntityQueryFactory<TestUser> QUERY =
        EntityQueryFactory.getInstance(TestUser.class, FACTORY);

    public static final EntityPropertyInfo<TestUser, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(TestUser.class, "version", 1);

    public static final EntityIndexablePropertyInfo<TestUser, String> TITLE =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(TestUser.class, String.class, "title");
    public static final EntityPropertyInfo<TestUser, String> TEXT =
        EntityPropertyInfoFactory.getPropertyInfo(TestUser.class, String.class, "text");

    public static final EntityCollectionPropertyInfo<TestUser, List, String> REFERENCES =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestUser.class, List.class, String.class, "text");

    
    public final EntityProperty<TestUser, Long> version;
    public final EntityProperty<TestUser, String> title;
    public final EntityProperty<TestUser, String> text;
    public final EntityCollectionProperty<TestUser, List, String> references;

    private TestUser() {
        version = VERSION.newInstance(this.getEntityPropertyAccess());
        title = TITLE.newInstance(this.getEntityPropertyAccess());
        text = TEXT.newInstance(this.getEntityPropertyAccess());
        references = REFERENCES.newInstance(this.getEntityPropertyAccess());
    }

}
