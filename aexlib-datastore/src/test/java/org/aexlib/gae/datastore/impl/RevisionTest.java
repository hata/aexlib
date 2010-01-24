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

import java.util.ConcurrentModificationException;

import org.aexlib.gae.LocalDataStoreTestCase;
import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityNameBase;
import org.aexlib.gae.datastore.EntityNameFactory;
import org.aexlib.gae.datastore.EntityProperty;
import org.aexlib.gae.datastore.EntityPropertyInfo;
import org.aexlib.gae.datastore.EntityPropertyInfoFactory;
import org.aexlib.gae.datastore.TransactionManager;


public class RevisionTest extends LocalDataStoreTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testCheckValue() throws Exception {
        TestEntry entry = TestEntry.FACTORY.initInstance("test");
        entry.counter.set(1);
        entry.put();
        
        entry = TestEntry.FACTORY.initInstance("test");
        assertEquals(Integer.valueOf(1), entry.counter.get());
    }
    
    public void testConcurrency() throws Exception {
        TestEntry entry = TestEntry.FACTORY.initInstance("test");
        entry.counter.set(1);
        entry.put();

        entry = TestEntry.FACTORY.initInstance("test");
        assertEquals("Verify current value.", Integer.valueOf(1), entry.counter.get());


        // Update Using another instance.
        TestEntry entry2 = TestEntry.FACTORY.initInstance("test");
        entry2.counter.set(entry2.counter.get() + 1);
        entry2.put();
        assertEquals("Verify new value.", Integer.valueOf(2), entry2.counter.get());

        try {
            entry.counter.set(entry.counter.get() + 1);
            entry.put();
            fail("Concurrency failure.");
        } catch (ConcurrentModificationException e) {
            // This should be ok because revision is changed.
        }

        entry = TestEntry.FACTORY.initInstance("test");
        assertEquals("Verify new value.", Integer.valueOf(2), entry.counter.get());
    }

    public void testConcurrencyWithTxn() throws Exception {
        TestEntry entry = TestEntry.FACTORY.initInstance("test");
        entry.counter.set(1);
        entry.put();

        entry = TestEntry.FACTORY.initInstance("test");
        assertEquals("Verify current value.", Integer.valueOf(1), entry.counter.get());


        // Update Using another instance.
        TestEntry entry2 = TestEntry.FACTORY.initInstance("test");
        entry2.counter.set(entry2.counter.get() + 1);
        entry2.put();
        assertEquals("Verify new value.", Integer.valueOf(2), entry2.counter.get());

        try {
            TransactionManager.getInstance().begin();
            entry.counter.set(entry.counter.get() + 1);
            entry.put();
            TransactionManager.getInstance().commit();
            fail("Concurrency failure.");
        } catch (ConcurrentModificationException e) {
            // This should be ok because revision is changed.
        } finally {
            TransactionManager.getInstance().rollbackIfActive();
        }

        entry = TestEntry.FACTORY.initInstance("test");
        assertEquals("Verify new value.", Integer.valueOf(2), entry.counter.get());
    }

    public static class TestEntry extends EntityNameBase<TestEntry> {
        public static final EntityNameFactory<TestEntry> FACTORY =
            EntityFactory.getEntityNameFactory(TestEntry.class,
                new EntityCreator<TestEntry>() {
            public TestEntry newInstance() {
                return new TestEntry();
            }
        });

        static EntityPropertyInfo<TestEntry, Integer> COUNTER =
            EntityPropertyInfoFactory.getPropertyInfo(TestEntry.class, Integer.class, "counter");

        EntityProperty<TestEntry, Integer> counter;

        private TestEntry() {
            setRevisionPropertyName("revision");
            counter = COUNTER.newInstance(getEntityPropertyAccess());
        }
    }

}
