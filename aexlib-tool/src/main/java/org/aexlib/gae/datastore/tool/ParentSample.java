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

import java.util.List;

import com.archtea.gae.datastore.tool.anno.KeyType;
import com.archtea.gae.datastore.tool.anno.KeyTypeValue;
import com.archtea.gae.datastore.tool.anno.Kind;
import com.archtea.gae.datastore.tool.anno.Version;

@KeyType(KeyTypeValue.NAME)
@Kind("ParentSample")
public class ParentSample {
    @Version("version")
    Long version;

    List<Sample> children;
    
    Sample child;
}
