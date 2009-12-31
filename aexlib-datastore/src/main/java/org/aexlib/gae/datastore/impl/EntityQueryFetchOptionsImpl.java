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

import com.archtea.gae.datastore.EntityQueryFetchOptions;
import com.google.appengine.api.datastore.FetchOptions;

public class EntityQueryFetchOptionsImpl extends EntityQueryFetchOptions {
    
    private FetchOptions options;

    public EntityQueryFetchOptionsImpl() {
    }

    FetchOptions toFetchOptions() {
        return options;
    }
    
    public EntityQueryFetchOptions limit(int limit) {
        if (options != null) {
            options.limit(limit);
        } else {
            options = FetchOptions.Builder.withLimit(limit);
        }
        return this;
    }

    public EntityQueryFetchOptions offset(int offset) {
        if (options != null) {
            options.offset(offset);
        } else {
            options = FetchOptions.Builder.withOffset(offset);
        }
        return this;
    }
    
    public EntityQueryFetchOptions chunkSize(int chunkSize) {
        if (options != null) {
            options.offset(chunkSize);
        } else {
            options = FetchOptions.Builder.withChunkSize(chunkSize);
        }
        return this;
    }
}
