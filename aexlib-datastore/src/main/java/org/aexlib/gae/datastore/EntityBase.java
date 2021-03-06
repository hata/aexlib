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

import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aexlib.gae.datastore.anno.Kind;
import org.aexlib.gae.datastore.impl.TransactionManagerImpl;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;


/**
 * This is a root object for this wrapper classes for Entity.
 * 
 * <pre>
 * EntityBaseImpl                                          EntityInstanceFactory ---> EntityCreator
 *    |                                                             |
 *    A___________________________                           _______A________
 *    |                           |                         |                |
 * EntityIdBase            EntityNameBase          EntityIdFactory    EntityNameFactory
 *    |                           |
 *    A                           A                        EntityChildInstanceFactory     
 *    |                           |                                |          |
 * EntityChildIdBase   EntityChildNameBase         EntityChildIdFactory EntityChildNameFactory
 * </pre>
 */
public abstract class EntityBase<ENTITY extends EntityBase<ENTITY>>
    implements Comparable<ENTITY> {
    private static final Logger log = Logger.getLogger(EntityBase.class.getName());
    private static final Long INITIAL_REVISION = Long.valueOf(0L);

    private Key key;
    private Entity entity;
    private String versionPropertyName;
    private long currentVersion;
    private Map<String, Object> initialProperties;
    private Map<String, Object> initialUnindexedProperties;
    
    private List<EntityVersionManager<ENTITY>> versionManagerList;
    private EntityVersionManager<ENTITY> currentVersionManager;
    
    private final EntityBasePropertyAccess<ENTITY> propertyAccess = new EntityPropertyAccessImpl();

    private Long revision = -1L;
    private String revisionPropertyName = null;
    
    

    protected static <T extends EntityBase<T>> void sortVersionManagers(
            List<EntityVersionManager<T>> managerList) {
        if (managerList == null) {
            return;
        }
        Collections.sort(managerList, new Comparator<EntityVersionManager<T>>() {

            public int compare(EntityVersionManager<T> o1,
                    EntityVersionManager<T> o2) {
                long diff = o1.getVersion() - o2.getVersion();
                if (diff < 0L) {
                    return -1;
                } else if (diff == 0L) {
                    return 0;
                } else {
                    return 1;
                }
            }
            
        });
    }

    public static <T2 extends EntityBase<T2>> String getKindName(Class<T2> entityClass) {
        Kind kind = entityClass.getAnnotation(Kind.class);
        return kind != null ? kind.value().trim() : entityClass.getSimpleName();
    }
    
    
    public Key getKey() {
        return key;
    }
    
    protected void init(Key key) {
        this.key = key;
        this.entity = null;
    }

    protected void init(Entity entity) {
        this.entity = entity;
        key = entity != null ? entity.getKey() : null;
        if (entity != null) {
            checkEntityVersion(entity);
        }
    }

    /**
     * This method is called when a new entity is created and
     * put it to datastore.
     * 
     * @param entity is a new entity which is not stored datastore yet.
     */
    protected void initNewEntity(Entity entity) {
        this.entity = entity;
        key = entity != null ? entity.getKey() : null;
        if (entity != null) {
            setVersionToEntity(entity);
            if (currentVersionManager != null) {
                currentVersionManager.init(this.getEntityPropertyAccess());
            }
            if (initialProperties != null) {
                for (Map.Entry<String, Object> entry : initialProperties.entrySet()) {
                    entity.setProperty(entry.getKey(), entry.getValue());
                }
                initialProperties = null;
            }
            if (initialUnindexedProperties != null) {
                for (Map.Entry<String, Object> entry : initialUnindexedProperties.entrySet()) {
                    entity.setUnindexedProperty(entry.getKey(), entry.getValue());
                }
                initialUnindexedProperties = null;
            }
            initRevision(entity);
        }
    }
    
    protected EntityBasePropertyAccess<ENTITY> getEntityPropertyAccess() {
        return propertyAccess;
    }

    public void put() {
        if (entity == null) {
            initNewEntity(newEntity());
        } else {
            verifyRevision();
        }
        TransactionManagerImpl.getInstance().put(entity);
    }
    

    public void delete() {
        final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        final Transaction tx = ds.getCurrentTransaction(null);
        if (tx != null) {
            TransactionManagerImpl.getInstance().delete(getKey());
        } else {
            ds.delete(getKey());
        }
        init((Entity)null);
    }
    
    public boolean isExists() {
        try {
            final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            final Transaction tx = ds.getCurrentTransaction(null);
            return (tx != null ? ds.get(tx, getKey()) : ds.get(getKey()))  != null;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    protected void initVersionManagers(List<EntityVersionManager<ENTITY>> versionManagerList) {
        this.versionManagerList = versionManagerList;
        if (versionManagerList != null) {
            ListIterator<EntityVersionManager<ENTITY>> it = versionManagerList.listIterator(versionManagerList.size());
            while (it.hasPrevious()) {
                EntityVersionManager<ENTITY> manager = it.previous();
                if (manager.getVersion() == currentVersion) {
                    currentVersionManager = manager;
                    break;
                }
            }
        }
        
    }

    public int compareTo(ENTITY o) {
        return getKey().compareTo(o.getKey());
    }
    

    @Override
    public int hashCode() {
        final Key key = getKey();
        return key != null ? key.hashCode() : 0; //TODO: May not good to return 0.
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        
        if (object == null) {
            return false;
        }
        
        if (!(object instanceof EntityBase)) {
            return false;
        }

        final Key key = getKey();
        final Key otherKey = ((EntityBase<?>)object).getKey();
        
        if (key == null || otherKey == null) {
            return false;
        }

        return key.equals(otherKey);
    }

    
    protected abstract Entity newEntity();

    // TODO: How to set initial value ???
    private void applyVersionUp(long storedVersion, long currentVersion) {
        for (EntityVersionManager<ENTITY> manager : versionManagerList) {
            if (manager.getVersion() > currentVersion) {
                break;
            } else if (manager.getVersion() > storedVersion) {
                manager.up(this.getEntityPropertyAccess());
                setVersionToEntity(this.entity, manager.getVersion());
            }
        }
    }

    private void applyVersionDown(long storedVersion, long currentVersion) {
        ListIterator<EntityVersionManager<ENTITY>> it = versionManagerList.listIterator(versionManagerList.size());
        while (it.hasPrevious()) {
            final EntityVersionManager<ENTITY> manager = it.previous();
            if (manager.getVersion() <= currentVersion) {
                break;
            } else if (manager.getVersion() <= storedVersion) {
                manager.down(this.getEntityPropertyAccess());
                setVersionToEntity(this.entity, manager.getVersion());
            }
        }
    }


    private Entity getEntity(DatastoreService ds, Transaction tx) throws EntityNotFoundException {
        final Key localKey = getKey();
        if (entity == null && localKey != null) {
            entity = tx != null ? ds.get(tx, localKey) : ds.get(localKey);
            checkEntityVersion(entity);
        }
        return entity;
    }


    private void checkEntityVersion(Entity localEntity) {
        if (versionPropertyName != null) {
            Object o = localEntity.getProperty(versionPropertyName);
            if (o instanceof Long) {
                final long storedVersion = (Long)o;
                if (currentVersion != storedVersion) {
                    if (currentVersion > storedVersion) {
                        // TODO: transaction
                        try {
                            if (versionManagerList != null) {
                                applyVersionUp(storedVersion, currentVersion);
                            }
                            localEntity.setProperty(versionPropertyName, Long.valueOf(currentVersion));
                        } catch (Throwable t) {
                            t.printStackTrace();
                            log.warning("version up failed.");
                        }
                    } else {
                        // TODO: transaction
                        try {
                            if (versionManagerList != null) {
                                applyVersionDown(storedVersion, currentVersion);
                            }
                            localEntity.setProperty(versionPropertyName, Long.valueOf(currentVersion));
                        } catch (Throwable t) {
                            t.printStackTrace();
                            log.warning("version down failed.");
                        }
                    }
                }
            } else if (o == null) {
                localEntity.setProperty(versionPropertyName, Long.valueOf(currentVersion));
            } else {
                // TODO: WARNING: Type is not expected one.
                log.warning("Version type info is not expected value. Stored type is " + o.getClass());
            }
        }
        getRevisionToThisInstance(localEntity);
    }
    
    protected void setVersionToEntity(Entity entity) {
        setVersionToEntity(entity, currentVersion);
    }

    private void setVersionToEntity(Entity entity, long version) {
        if (versionPropertyName != null) {
            entity.setProperty(versionPropertyName, Long.valueOf(version));
        }
    }

    
    protected void setRevisionPropertyName(String revisionPropertyName) {
        this.revisionPropertyName = revisionPropertyName;
    }

    private void initRevision(Entity entity) {
        if (revisionPropertyName != null) {
            entity.setProperty(revisionPropertyName, INITIAL_REVISION);
            revision = INITIAL_REVISION;
        }
    }
    
    private void getRevisionToThisInstance(Entity entity) {
        if (revisionPropertyName != null) {
            Object o = entity.getProperty(revisionPropertyName);
            if (o instanceof Long) {
                revision = ((Long)o).longValue();
            } else {
                revision = INITIAL_REVISION;
            }
        }
    }

    /**
     * If revision property is set, this method checks datastore is changed or not.
     * @exception ConcurrentModificationException is thrown when stored data is changed
     * since this instance is initialized.
     */
    private void verifyRevision() {
        if (revisionPropertyName != null) {
            final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            final Transaction tx = ds.getCurrentTransaction(null);
            try {
                Entity entity = tx != null ? ds.get(tx, getKey()) : ds.get(getKey());
                Object o = entity.getProperty(revisionPropertyName);
                if ((o != null && revision != null && !revision.equals(o)) ||
                        ((o == null && revision != null) || (o != null && revision == null))) {
                    throw new ConcurrentModificationException("Entity is updated.");
                }

                // If revision is ok, set a new revision to a local entity instance.
                Long newRev = revision != null ? Long.valueOf(revision.longValue() + 1L) : INITIAL_REVISION;
                this.entity.setProperty(revisionPropertyName, newRev);
            } catch (EntityNotFoundException e) {
                log.log(Level.WARNING, "verifyRevision is called for non existing entity.");
            }
        }
    }
    
    private class EntityPropertyAccessImpl implements EntityBasePropertyAccess<ENTITY> {
        public void setVersion(String propertyName, long currentVersion) {
            versionPropertyName = propertyName;
            EntityBase.this.currentVersion = currentVersion;
        }

        public Object getProperty(String property) throws EntityNotFoundException {
            final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            final Transaction tx = ds.getCurrentTransaction(null);
            return getEntity(ds, tx).getProperty(property);
        }

        public void setProperty(String property, Object value) throws EntityNotFoundException {
            try {
                final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
                final Transaction tx = ds.getCurrentTransaction(null);
                final Entity entity = getEntity(ds, tx);
                if (entity != null) {
                    entity.setProperty(property, value);
                    return;
                } else {
                    // If the key is id, Key object is not created until a new creation.
                    // So, entity may be null if the object is not created yet.
                }
            } catch (EntityNotFoundException e) {
                if (log.isLoggable(Level.FINEST)) {
                    log.finest("No entity object found. A new name entity object will be created." + e);
                }
                // If EntityNotFoundException is thrown, name key exists and the
                // entity is not created. So, it becomes to initialized process.
            }
            if (initialProperties == null) {
                initialProperties = new HashMap<String, Object>();
            }
            initialProperties.put(property, value);
        }
        
        public void setUnindexedProperty(String property, Object value) throws EntityNotFoundException {
            try {
                final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
                final Transaction tx = ds.getCurrentTransaction(null);
                final Entity entity = getEntity(ds, tx);
                if (entity != null) {
                    entity.setUnindexedProperty(property, value);
                    return;
                } else {
                    // If the key is id, Key object is not created until a new creation.
                    // So, entity may be null if the object is not created yet.
                }
            } catch (EntityNotFoundException e) {
                if (log.isLoggable(Level.FINEST)) {
                    log.finest("No entity object found. An unindexed name property will be created." + e);
                }
                // If EntityNotFoundException is thrown, name key exists and the
                // entity is not created. So, it becomes to initialized process.
            }
            if (initialUnindexedProperties == null) {
                initialUnindexedProperties = new HashMap<String, Object>();
            }
            initialUnindexedProperties.put(property, value);
        }

        public void removeProperty(String property) throws EntityNotFoundException {
            final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            final Transaction tx = ds.getCurrentTransaction(null);
            try {
                final Entity entity = getEntity(ds, tx);
                if (entity != null) {
                    entity.removeProperty(property);
                }
            } catch (EntityNotFoundException e) {
                if (log.isLoggable(Level.FINEST)) {
                    log.finest("No entity object found. Removed a property for non-created entity." + e);
                }
            }
        }

    }
}
