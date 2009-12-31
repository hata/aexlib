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
import java.util.List;

import com.archtea.gae.datastore.EntityBasePropertyAccess;
import com.archtea.gae.datastore.EntityCollectionProperty;
import com.archtea.gae.datastore.EntityCollectionPropertyInfo;
import com.archtea.gae.datastore.EntityCreator;
import com.archtea.gae.datastore.EntityFactory;
import com.archtea.gae.datastore.EntityIdFactory;
import com.archtea.gae.datastore.EntityIndexableCollectionPropertyInfo;
import com.archtea.gae.datastore.EntityIndexablePropertyInfo;
import com.archtea.gae.datastore.EntityNameBase;
import com.archtea.gae.datastore.EntityNameFactory;
import com.archtea.gae.datastore.EntityProperty;
import com.archtea.gae.datastore.EntityPropertyInfoFactory;
import com.archtea.gae.datastore.EntityPropertyInfo;
import com.archtea.gae.datastore.EntityQueryFactory;
import com.archtea.gae.datastore.EntityVersionManager;

public class TestDocument extends EntityNameBase<TestDocument> {
    // There are two factory... This is for test purpose.
    // Normally, factory should only have 1 instance.
    public static final EntityNameFactory<TestDocument> NAME_FACTORY =
        EntityFactory.getEntityNameFactory(TestDocument.class,
            new EntityCreator<TestDocument>() {
        public TestDocument newInstance() {
            return new TestDocument();
        }
    });
    static ArrayList<EntityVersionManager<TestDocument>> versionManagerList = null;

    public static final EntityQueryFactory<TestDocument> QUERY =
        EntityQueryFactory.getInstance(TestDocument.class, NAME_FACTORY);

    static EntityIndexablePropertyInfo<TestDocument, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(TestDocument.class, "VERSION", 1);

    static EntityIndexablePropertyInfo<TestDocument, String> TITLE =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(TestDocument.class, String.class, "Title");

    static EntityIndexablePropertyInfo<TestDocument, TestIdPage> PAGE =
        EntityPropertyInfoFactory.getKeyLinkPropertyInfo(TestDocument.class, TestIdPage.class, TestIdPage.ID_FACTORY, "Page");

    static EntityIndexableCollectionPropertyInfo<TestDocument, List, TestChapter> CHAPTERS =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestDocument.class, List.class, TestChapter.class, TestChapter.NAME_FACTORY, "Chapters");

    EntityProperty<TestDocument, Long> version;
    EntityProperty<TestDocument, String> title;
    EntityProperty<TestDocument, TestIdPage> page;
    
    EntityCollectionProperty<TestDocument, List, TestChapter> chapters;

    // This should be a private contructor. I changed it to default for testing.
    TestDocument() {
        version = VERSION.newInstance(this.getEntityPropertyAccess());
        title = TITLE.newInstance(this.getEntityPropertyAccess());
        page = PAGE.newInstance(this.getEntityPropertyAccess());
        chapters = CHAPTERS.newInstance(this.getEntityPropertyAccess());

        initVersionManagers(versionManagerList);
    }
    
    public EntityBasePropertyAccess<TestDocument> getEntityPropertyAccess() {
        return super.getEntityPropertyAccess();
    }

    
}
