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

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

import com.archtea.gae.datastore.TransactionManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class TransactionManagerImpl extends TransactionManager {
    private static final Logger log = Logger.getLogger(TransactionManagerImpl.class.getName());

    private final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    private Set<Entity> putSet;
    private Set<Iterable<Entity>> putItSet;

    private Set<Key> deleteSet;
    private Set<Iterable<Key>> deleteItSet;
    private Transaction transaction;
    
    private static final ThreadLocal<TransactionManagerImpl> MANAGER =
        new ThreadLocal<TransactionManagerImpl>() {
        @Override
        protected TransactionManagerImpl initialValue() {
            return new TransactionManagerImpl();
        }
    };

    public static TransactionManagerImpl getInstance() {
        return MANAGER.get();
    }

    public void put(Iterable<Entity> entities) {
        if (transaction != null) {
            if (putItSet == null) {
                putItSet = new HashSet<Iterable<Entity>>();
            }
            if (!putItSet.contains(entities)) {
                putItSet.add(entities);
            }
        } else {
            ds.put(entities);
        }
    }

    public void put(Entity entity) {
        if (transaction != null) {
            if (putSet == null) {
                putSet = new HashSet<Entity>();
            }
            if (!putSet.contains(entity)) {
                putSet.add(entity);
            }
        } else {
            ds.put(entity);
        }
    }
    
    public void delete(Iterable<Key> keys) {
        if (transaction != null) {
            if (deleteItSet == null) {
                deleteItSet = new HashSet<Iterable<Key>>();
            }
            if (!deleteItSet.contains(keys)) {
                deleteItSet.add(keys);
            }
        } else {
            ds.delete(keys);
        }
    }

    public void delete(Key... keys) {
        if (transaction != null) {
            if (deleteSet == null) {
                deleteSet = new HashSet<Key>();
            }
            for (Key key : keys) {
                if (!deleteSet.contains(key)) {
                    deleteSet.add(key);
                }
            }
        } else {
            ds.delete(keys);
        }
    }
    
    public void begin() {
        transaction = ds.beginTransaction();
    }
    
    public void commit() {
        try {
            final Transaction tx = ds.getCurrentTransaction(null);
            if (tx == null) {
                log.warning("commit is called for null txn.");
            } else if (!tx.isActive()) {
                log.warning("commit is called for inactive txn.");
            } else {
                beforeCommit(tx);
                tx.commit();
            }
        } finally {
            afterTransaction();
        }
    }
    
    public void rollback() {
        try {
            final Transaction tx = ds.getCurrentTransaction(null);
            if (tx == null) {
                log.warning("rollback is called for null txn.");
            } else if (!tx.isActive()) {
                log.warning("rollback is called for inactive txn.");
            } else {
                tx.rollback();
            }
        } finally {
            afterTransaction();
        }
    }

    public void rollbackIfActive() throws NoSuchElementException {
        final Transaction tx = ds.getCurrentTransaction(null);
        if (tx != null && tx.isActive()) {
            try {
                tx.rollback();
            } finally {
                afterTransaction();
            }
        }
    }
    
    private void beforeCommit(Transaction tx) {
        if (putSet != null) {
            ds.put(tx, putSet);
        }
        if (putItSet != null) {
            for (Iterable<Entity> iterable : putItSet) {
                for (Entity entity : iterable) {
                    ds.put(tx, entity);
                }
            }
        }
        if (deleteSet != null) {
            ds.delete(tx, deleteSet);
        }
        if (deleteItSet != null) {
            for (Iterable<Key> iterable : deleteItSet) {
                ds.delete(tx, iterable);
            }
        }
    }
    
    private void afterTransaction() {
        transaction = null;
        if (putSet != null) {
            putSet.clear();
        }
        if (putItSet != null) {
            putItSet.clear();
        }
        if (deleteSet != null) {
            deleteSet.clear();
        }
        if (deleteItSet != null) {
            deleteItSet.clear();
        }
    }
}
