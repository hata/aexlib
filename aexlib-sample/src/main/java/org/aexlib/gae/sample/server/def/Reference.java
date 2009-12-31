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

import org.aexlib.gae.tool.anno.KeyType;
import org.aexlib.gae.tool.anno.KeyTypeValue;
import org.aexlib.gae.tool.anno.Kind;

// Kind is "Reference" and key is created by kind and name pair.
@Kind("Reference")
@KeyType(KeyTypeValue.NAME)
public class Reference {
    // Unindexed string.
    String title;

    // If EntityBase object is set like this, this value is
    // stored as a Key automatically.
    Document referer;
}
