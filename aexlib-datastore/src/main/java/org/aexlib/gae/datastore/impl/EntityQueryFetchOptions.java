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


abstract class EntityQueryFetchOptions {
    static class Factory {
        public static EntityQueryFetchOptions withLimit(int limit) {
            return new EntityQueryFetchOptionsImpl().limit(limit);
        }
        
        public static EntityQueryFetchOptions withOffset(int offset) {
            return new EntityQueryFetchOptionsImpl().offset(offset);
        }
        
        public static EntityQueryFetchOptions withChunkSize(int chunkSize) {
            return new EntityQueryFetchOptionsImpl().chunkSize(chunkSize);
        }
        
        public static EntityQueryFetchOptions withPrefetchSize(int prefetchSize) {
            return new EntityQueryFetchOptionsImpl().prefetchSize(prefetchSize);
        }

        public static EntityQueryFetchOptions withCursor(Cursor cursor) {
            return new EntityQueryFetchOptionsImpl().cursor(cursor);
        }
    }

    public abstract EntityQueryFetchOptions limit(int limit);
    public abstract EntityQueryFetchOptions offset(int offset);
    public abstract EntityQueryFetchOptions chunkSize(int limit);
    public abstract EntityQueryFetchOptions prefetchSize(int limit);
    public abstract EntityQueryFetchOptions cursor(Cursor cursor);
    
}
