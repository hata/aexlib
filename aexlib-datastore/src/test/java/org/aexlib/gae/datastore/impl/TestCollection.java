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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.aexlib.gae.datastore.EntityBasePropertyAccess;
import org.aexlib.gae.datastore.EntityCollectionProperty;
import org.aexlib.gae.datastore.EntityCollectionPropertyInfo;
import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityNameBase;
import org.aexlib.gae.datastore.EntityNameFactory;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfo;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;
import org.aexlib.gae.datastore.EntityQueryFactory;
import org.aexlib.gae.datastore.EntityVersionManager;


public class TestCollection extends EntityNameBase<TestCollection> {
    // There are two factory... This is for test purpose.
    // Normally, factory should only have 1 instance.
    public static final EntityNameFactory<TestCollection> NAME_FACTORY =
        EntityFactory.getEntityNameFactory(TestCollection.class,
            new EntityCreator<TestCollection>() {
        public TestCollection newInstance() {
            return new TestCollection();
        }
    });

    public static final EntityQueryFactory<TestCollection> QUERY =
        EntityQueryFactory.getInstance(TestCollection.class, NAME_FACTORY);

    static EntityCollectionPropertyInfo<TestCollection, Collection, TestChapter> CHAPTERS_COLLECTION =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, Collection.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterCollection");

    static EntityCollectionPropertyInfo<TestCollection, List, TestChapter> CHAPTERS_LIST =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, List.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterList");

    static EntityCollectionPropertyInfo<TestCollection, SortedSet, TestChapter> CHAPTERS_SORTEDSET =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, SortedSet.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterSortedSet");

    static EntityCollectionPropertyInfo<TestCollection, Set, TestChapter> CHAPTERS_SET =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, Set.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterSet");

    final EntityCollectionProperty<TestCollection, Collection, TestChapter> chapterCollection;
    final EntityCollectionProperty<TestCollection, List, TestChapter> chapterList;
    final EntityCollectionProperty<TestCollection, SortedSet, TestChapter> chapterSortedSet;
    final EntityCollectionProperty<TestCollection, Set, TestChapter> chapterSet;

    
    static EntityCollectionPropertyInfo<TestCollection, ArrayList, TestChapter> CHAPTERS_ARRAYLIST =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, ArrayList.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterArrayList");

    static EntityCollectionPropertyInfo<TestCollection, LinkedList, TestChapter> CHAPTERS_LINKEDLIST =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, LinkedList.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterLinkedList");

    static EntityCollectionPropertyInfo<TestCollection, HashSet, TestChapter> CHAPTERS_HASHSET =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, HashSet.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterHashSet");
    
    static EntityCollectionPropertyInfo<TestCollection, LinkedHashSet, TestChapter> CHAPTERS_LINKEDHASHSET =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, LinkedHashSet.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterLinkedHashSet");

    static EntityCollectionPropertyInfo<TestCollection, Stack, TestChapter> CHAPTERS_STACK =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, Stack.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterStack");
    
    static EntityCollectionPropertyInfo<TestCollection, TreeSet, TestChapter> CHAPTERS_TREESET =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, TreeSet.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterTreeSet");

    static EntityCollectionPropertyInfo<TestCollection, Vector, TestChapter> CHAPTERS_VECTOR =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(TestCollection.class, Vector.class, TestChapter.class, TestChapter.NAME_FACTORY, "ChapterVector");

    
    final EntityCollectionProperty<TestCollection, ArrayList, TestChapter> chapterArrayList;
    final EntityCollectionProperty<TestCollection, LinkedList, TestChapter> chapterLinkedList;
    final EntityCollectionProperty<TestCollection, HashSet, TestChapter> chapterHashSet;
    final EntityCollectionProperty<TestCollection, LinkedHashSet, TestChapter> chapterLinkedHashSet;
    final EntityCollectionProperty<TestCollection, Stack, TestChapter> chapterStack;
    final EntityCollectionProperty<TestCollection, TreeSet, TestChapter> chapterTreeSet;
    final EntityCollectionProperty<TestCollection, Vector, TestChapter> chapterVector;

    
    static EntityCollectionPropertyInfo<TestCollection, Collection, String> STRING_COLLECTION =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, Collection.class, String.class, "StringList");
    static EntityCollectionPropertyInfo<TestCollection, List, String> STRING_LIST =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, List.class, String.class, "StringList");
    static EntityCollectionPropertyInfo<TestCollection, Set, String> STRING_SET =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, Set.class, String.class, "StringSet");
    static EntityCollectionPropertyInfo<TestCollection, SortedSet, String> STRING_SORTEDSET =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, SortedSet.class, String.class, "StringSortedSet");
    
    static EntityCollectionPropertyInfo<TestCollection, ArrayList, String> STRING_ARRAYLIST =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, ArrayList.class, String.class, "StringArrayList");
    static EntityCollectionPropertyInfo<TestCollection, LinkedList, String> STRING_LINKEDLIST =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, LinkedList.class, String.class, "StringLinkedList");
    static EntityCollectionPropertyInfo<TestCollection, HashSet, String> STRING_HASHSET =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, HashSet.class, String.class, "StringHashSet");
    static EntityCollectionPropertyInfo<TestCollection, LinkedHashSet, String> STRING_LINKEDHASHSET =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, LinkedHashSet.class, String.class, "StringLinkedHashSet");
    static EntityCollectionPropertyInfo<TestCollection, Stack, String> STRING_STACK =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, Stack.class, String.class, "StringStack");
    static EntityCollectionPropertyInfo<TestCollection, TreeSet, String> STRING_TREESET =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, TreeSet.class, String.class, "StringTreeSet");
    static EntityCollectionPropertyInfo<TestCollection, Vector, String> STRING_VECTOR =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, Vector.class, String.class, "StringVector");
    
    final EntityCollectionProperty<TestCollection, Collection, String> stringCollection;
    final EntityCollectionProperty<TestCollection, List, String> stringList;
    final EntityCollectionProperty<TestCollection, Set, String> stringSet;
    final EntityCollectionProperty<TestCollection, SortedSet, String> stringSortedSet;
    
    final EntityCollectionProperty<TestCollection, ArrayList, String> stringArrayList;
    final EntityCollectionProperty<TestCollection, LinkedList, String> stringLinkedList;
    final EntityCollectionProperty<TestCollection, HashSet, String> stringHashSet;
    final EntityCollectionProperty<TestCollection, LinkedHashSet, String> stringLinkedHashSet;
    final EntityCollectionProperty<TestCollection, Stack, String> stringStack;
    final EntityCollectionProperty<TestCollection, TreeSet, String> stringTreeSet;
    final EntityCollectionProperty<TestCollection, Vector, String> stringVector;
    
    
    static EntityCollectionPropertyInfo<TestCollection, Set, Serializable> SERIALIZABLE_SET =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(TestCollection.class, Set.class, Serializable.class, "SerializableSet");
    final EntityCollectionProperty<TestCollection, Set, Serializable> serializableSet;


    // This should be a private contructor. I changed it to default for testing.
    TestCollection() {
        chapterCollection = CHAPTERS_COLLECTION.newInstance(this.getEntityPropertyAccess());
        chapterList = CHAPTERS_LIST.newInstance(this.getEntityPropertyAccess());
        chapterSortedSet = CHAPTERS_SORTEDSET.newInstance(this.getEntityPropertyAccess());
        chapterSet = CHAPTERS_SET.newInstance(this.getEntityPropertyAccess());

        chapterArrayList = CHAPTERS_ARRAYLIST.newInstance(this.getEntityPropertyAccess());
        chapterLinkedList = CHAPTERS_LINKEDLIST.newInstance(this.getEntityPropertyAccess());
        chapterHashSet = CHAPTERS_HASHSET.newInstance(this.getEntityPropertyAccess());
        chapterLinkedHashSet = CHAPTERS_LINKEDHASHSET.newInstance(this.getEntityPropertyAccess());
        chapterStack = CHAPTERS_STACK.newInstance(this.getEntityPropertyAccess());
        chapterTreeSet = CHAPTERS_TREESET.newInstance(this.getEntityPropertyAccess());
        chapterVector = CHAPTERS_VECTOR.newInstance(this.getEntityPropertyAccess());

        stringCollection = STRING_COLLECTION.newInstance(this.getEntityPropertyAccess());
        stringList = STRING_LIST.newInstance(this.getEntityPropertyAccess());
        stringSet = STRING_SET.newInstance(this.getEntityPropertyAccess());
        stringSortedSet = STRING_SORTEDSET.newInstance(this.getEntityPropertyAccess());

        stringArrayList = STRING_ARRAYLIST.newInstance(this.getEntityPropertyAccess());
        stringLinkedList = STRING_LINKEDLIST.newInstance(this.getEntityPropertyAccess());
        stringHashSet = STRING_HASHSET.newInstance(this.getEntityPropertyAccess());
        stringLinkedHashSet = STRING_LINKEDHASHSET.newInstance(this.getEntityPropertyAccess());
        stringStack = STRING_STACK.newInstance(this.getEntityPropertyAccess());
        stringTreeSet = STRING_TREESET.newInstance(this.getEntityPropertyAccess());
        stringVector = STRING_VECTOR.newInstance(this.getEntityPropertyAccess());
        
        serializableSet = SERIALIZABLE_SET.newInstance(this.getEntityPropertyAccess());
    }

    public EntityBasePropertyAccess<TestCollection> getEntityPropertyAccess() {
        return super.getEntityPropertyAccess();
    }

}
