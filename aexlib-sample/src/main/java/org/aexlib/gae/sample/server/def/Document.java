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
package com.archtea.gae.sample.server.def;

import java.util.List;
import java.util.Set;

import com.archtea.gae.datastore.tool.anno.Indexable;
import com.archtea.gae.datastore.tool.anno.KeyType;
import com.archtea.gae.datastore.tool.anno.KeyTypeValue;
import com.archtea.gae.datastore.tool.anno.Kind;
import com.archtea.gae.datastore.tool.anno.Property;
import com.archtea.gae.datastore.tool.anno.Version;
import com.archtea.gae.sample.server.AttachedObject;


// Kind name is Document. If this is not set, the class name is
// used for kind. And KeyType is NAME. In this case, Key is
// created kind and String(name) pair.
@Kind("Document")
@KeyType(KeyTypeValue.NAME)
public class Document {
    // Version object. The version number is 1L.
    @Version("Version")
    Long version = 1L;

    // This string is indexable. So, this configuration doesn't
    // convert String to Text. And this can use query parameters.
    // Property name is "Title".
    @Indexable
    @Property("Title")
    String title;

    // This string is indexable. So, this configuration doesn't
    // convert String to Text automatically.
    @Indexable
    String author;

    // Collection contains Reference and this will automatically
    // convert from EntityBase object to Key object when storing data.
    Set<Reference> references;
    

    // If the stored object in a collection or field class is an
    // serializable object and not indexable, the object is stored
    // as blob.
    List<AttachedObject> attachments;
}
