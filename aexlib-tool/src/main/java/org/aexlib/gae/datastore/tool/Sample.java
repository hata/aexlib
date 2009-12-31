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
package com.archtea.gae.datastore.tool;

import java.util.Date;
import java.util.List;

import com.archtea.gae.datastore.tool.anno.Indexable;
import com.archtea.gae.datastore.tool.anno.KeyType;
import com.archtea.gae.datastore.tool.anno.KeyTypeValue;
import com.archtea.gae.datastore.tool.anno.Kind;
import com.archtea.gae.datastore.tool.anno.ParentKind;
import com.archtea.gae.datastore.tool.anno.Property;
import com.archtea.gae.datastore.tool.anno.Version;


// If there is not Kind, the kind name comes from Interface name.
// Factory type is only two types, id or name.
@KeyType(KeyTypeValue.ID)
@Kind("Sample")
@ParentKind(ParentSample.class)
class Sample {
    // Version should have long type.
    // System time and serializer may help to generate version number.
    @Version("Version")
    Long version;

    // @Property may not need to set here..
    // No default is null. If there is a value, it is a default.
    // Maybe, it is difficult to get the value via annotation/reflection...
    // Proxy generator may hlep it.
    // Default may need to be able to use a class.
    @Property("Title")
    @Indexable
    String title;

    String text;
    
    Date insertDate;
    
    List<String> references;
}
