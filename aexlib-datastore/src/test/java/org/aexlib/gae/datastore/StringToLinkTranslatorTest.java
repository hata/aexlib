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
package org.aexlib.gae.datastore;

import com.google.appengine.api.datastore.Link;

import junit.framework.TestCase;

public class StringToLinkTranslatorTest extends TestCase {

    StringToLinkTranslator translator;

    protected void setUp() throws Exception {
        super.setUp();
        translator = new StringToLinkTranslator();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        translator = null;
    }


    public void testToStoreType() throws Exception {
        assertEquals(new Link("http://localhost"), translator.toStoreType("http://localhost"));
        assertNull(translator.toStoreType(null));
    }

    public void testToStoreTypeForLink() throws Exception {
        assertEquals(new Link("http://localhost"), translator.toStoreType(new Link("http://localhost")));
        assertNull(translator.toStoreType(new Object()));
    }

    public void testToUserType() throws Exception {
        assertEquals("http://localhost", translator.toUserType(new Link("http://localhost")));
        assertNull(translator.toUserType(null));
    }

    public void testToUserTypeForString() throws Exception {
        assertEquals("http://localhost", translator.toUserType("http://localhost"));
        assertNull(translator.toUserType(new Object()));
    }
}
