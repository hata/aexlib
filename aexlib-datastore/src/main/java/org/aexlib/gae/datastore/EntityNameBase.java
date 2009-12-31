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
package com.archtea.gae.datastore;

import java.util.ConcurrentModificationException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class EntityNameBase<ENTITY extends EntityNameBase<ENTITY>> extends EntityBase<ENTITY> {
    

    /**
     * This method starts a new txn. So, don't call this method in a txn.
     * If this object can create a new entity, return true.
     * Otherwise, return false.
     */
    public boolean putIfAbsent() {
        final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Transaction tx = ds.beginTransaction();
        final Key key = getKey();
        
        try {
            if (key != null) {
                ds.get(tx, key);
            } else {
                // TODO: assert
            }
        } catch (EntityNotFoundException e) {
            // If there is not the data, this exception is thrown.
            try {
                final Entity newEntity = newEntity();
                initNewEntity(newEntity);
                ds.put(tx, newEntity);
                tx.commit();
                tx = null;
                return true;
            } catch (ConcurrentModificationException e2) {
                // Someone added the data.
                // In this case, clear entity data and set key only.
                init(key);
            }
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }

        return false;
    }

    @Override
    protected Entity newEntity() {
        return new Entity(getKey());
    }
}
