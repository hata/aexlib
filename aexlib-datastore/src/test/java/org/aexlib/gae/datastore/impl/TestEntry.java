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

import org.aexlib.gae.datastore.EntityCreator;
import org.aexlib.gae.datastore.EntityFactory;
import org.aexlib.gae.datastore.EntityIdBase;
import org.aexlib.gae.datastore.EntityIdFactory;

public class TestEntry extends EntityIdBase<TestEntry> {
    public static final EntityIdFactory<TestEntry> ID_FACTORY =
        EntityFactory.getEntityIdFactory(TestEntry.class,
            new EntityCreator<TestEntry>() {
        public TestEntry newInstance() {
            return new TestEntry();
        }
    });

}
