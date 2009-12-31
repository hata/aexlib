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

import com.archtea.gae.LocalDataStoreTestCase;

public class EntityVersionPropertyImplTest extends LocalDataStoreTestCase {

    TestDocument doc;

    protected void setUp() throws Exception {
        super.setUp();
        doc = TestDocument.NAME_FACTORY.initInstance("test1");
        doc.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        doc = null;
        super.tearDown();
    }

    public void testSetInteger() throws Exception {
        // Set doesn't update the version.
        doc.version.set(2L);
        assertEquals((Long)1L, doc.version.get());
    }

    public void testSetVersionForce() throws Exception {
        ((EntityVersionPropertyImpl)doc.version).setVersionForce(2L);
        assertEquals((Long)2L, doc.version.get());
    }

    public void testGet() throws Exception {
        assertEquals((Long)1L, doc.version.get());
    }

}
