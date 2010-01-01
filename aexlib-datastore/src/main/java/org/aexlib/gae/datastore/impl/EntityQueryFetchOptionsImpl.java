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


import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.FetchOptions;

public class EntityQueryFetchOptionsImpl extends EntityQueryFetchOptions {
    
    private FetchOptions options;

    public EntityQueryFetchOptionsImpl() {
    }

    FetchOptions toFetchOptions() {
        return options;
    }
    
    @Override
    public EntityQueryFetchOptions limit(int limit) {
        if (options != null) {
            options.limit(limit);
        } else {
            options = FetchOptions.Builder.withLimit(limit);
        }
        return this;
    }

    @Override
    public EntityQueryFetchOptions offset(int offset) {
        if (options != null) {
            options.offset(offset);
        } else {
            options = FetchOptions.Builder.withOffset(offset);
        }
        return this;
    }
    
    @Override
    public EntityQueryFetchOptions chunkSize(int chunkSize) {
        if (options != null) {
            options.chunkSize(chunkSize);
        } else {
            options = FetchOptions.Builder.withChunkSize(chunkSize);
        }
        return this;
    }

    @Override
    public EntityQueryFetchOptions prefetchSize(int prefetchSize) {
        if (options != null) {
            options.prefetchSize(prefetchSize);
        } else {
            options = FetchOptions.Builder.withPrefetchSize(prefetchSize);
        }
        return this;
    }

    @Override
    public EntityQueryFetchOptions cursor(Cursor cursor) {
        if (options != null) {
            options.cursor(cursor);
        } else {
            options = FetchOptions.Builder.withCursor(cursor);
        }
        return this;
    }
}
