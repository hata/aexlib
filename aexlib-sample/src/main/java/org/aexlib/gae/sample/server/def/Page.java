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
package org.aexlib.gae.sample.server.def;

import java.util.Set;

import org.aexlib.gae.tool.anno.KeyType;
import org.aexlib.gae.tool.anno.KeyTypeValue;
import org.aexlib.gae.tool.anno.Kind;
import org.aexlib.gae.tool.anno.ParentKind;
import org.aexlib.gae.tool.anno.Version;

// This kind is Page and it is created kind and long pair.
// And the parent is "Document". It will becomes the same
// entity group.
@Kind("Page")
@KeyType(KeyTypeValue.ID)
@ParentKind(Document.class)
public class Page {
    // Version object. The version number is 1L.
    @Version("Version")
    Long version = 1L;

    // This is String set object and there is no Indexable
    // Annotation. So, the value will be converted String to Text.
    Set<String> tags;

    // This is not indexable. So, text is converted from String
    // to Text if it is required.
    String text;

    // This value is short. And this value is converted to
    // Long when string data.
    Short number;
}
