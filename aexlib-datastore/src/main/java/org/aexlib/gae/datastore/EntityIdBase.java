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
package org.aexlib.gae.datastore;

import java.util.ConcurrentModificationException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class EntityIdBase<ENTITY extends EntityIdBase<ENTITY>> extends EntityBase<ENTITY> {
    private  String kind;

    protected void init(String kind) {
        this.kind = kind;
    }

    /**
     * This method starts a new txn. So, don't call this method in a txn.
     * If this object can create a new entity, return true.
     * Otherwise, return false.
     */
    public boolean putIfAbsent() {
        final Key key = getKey();
        final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Transaction tx = ds.beginTransaction();

        try {
            // If there is a entity, return false.
            // If there is no key, create a new entity without key.
            // If there is a key, create a new entity with the key.
            try {
                if (key != null) {
                    ds.get(tx, key);
                    return false;
                }
            } catch (EntityNotFoundException e) {
                // This means there is no entity which has 'key' in datastore.
            }

            try {
                final Entity newEntity = key != null ? new Entity(key) : newEntity();
                initNewEntity(newEntity);
                ds.put(tx, newEntity);
                tx.commit();
                tx = null;
                return true;
            } catch (ConcurrentModificationException e2) {
                // Someone added the data.
                // In this case, clear entity data and set key only.
                init(key);
                return false;
            }
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    
    @Override
    protected Entity newEntity() {
        return new Entity(kind);
    }

    protected String getKind() {
        return kind;
    }
    
}
