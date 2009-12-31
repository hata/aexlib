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

    private Key key;
    private Entity entity;
    private String versionPropertyName;
    private long currentVersion;
    private Map<String, Object> initialProperties;
    
    private List<EntityVersionManager<ENTITY>> versionManagerList;
    private EntityVersionManager<ENTITY> currentVersionManager;
    
    private final EntityBasePropertyAccess<ENTITY> propertyAccess = new EntityPropertyAccessImpl();

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
        }
    }
    
    protected EntityBasePropertyAccess<ENTITY> getEntityPropertyAccess() {
        return propertyAccess;
    }

    public void put() {
        if (entity == null) {
            initNewEntity(newEntity());
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
    }
    
    protected void setVersionToEntity(Entity entity) {
        setVersionToEntity(entity, currentVersion);
    }

    private void setVersionToEntity(Entity entity, long version) {
        if (versionPropertyName != null) {
            entity.setProperty(versionPropertyName, Long.valueOf(version));
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
    }
}
