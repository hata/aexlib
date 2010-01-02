/*
 * $Id$
 * 
 * Copyright 2009-${year} Hiroki Ata
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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.aexlib.gae.datastore.EntityBase;
import org.aexlib.gae.datastore.EntityBaseFactory;
import org.aexlib.gae.datastore.EntityPropertyFilter;
import org.aexlib.gae.datastore.EntityPropertySorter;
import org.aexlib.gae.datastore.EntityResultIterable;
import org.aexlib.gae.datastore.EntityResultIterator;
import org.aexlib.gae.datastore.EntityResultList;
import org.aexlib.gae.datastore.EntityResultQuery;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.datastore.Transaction;

public class EntityQueryImpl<ENTITY extends EntityBase<ENTITY>> implements EntityResultQuery<ENTITY> {
    private Query query;
    private EntityBaseFactory<ENTITY> factory;
    private PreparedQuery pq;

    // Maybe, I don't need to be care about caching query instance.
    // Right now, I used thread local. But, it may not good way
    // if a client just only set a value and then leave it...
    private final ThreadLocal<EntityQueryFetchOptions> threadLocalFetchOptions = new ThreadLocal<EntityQueryFetchOptions>();
    

    public EntityQueryImpl(Query query, EntityBaseFactory<ENTITY> factory) {
        this.query = query;
        this.factory = factory;
    }

    public <PROPERTY_TYPE> EntityQueryImpl<ENTITY> filter(EntityPropertyFilter<ENTITY, PROPERTY_TYPE> filter) {
        ((EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE>)filter).addFilter(this);
        return this;
    }

    public EntityResultQuery<ENTITY> sort(EntityPropertySorter<ENTITY> sort) {
        ((EntityPropertySorterImpl<ENTITY>)sort).addSort(this);
        return this;
    }
    
    public EntityResultQuery<ENTITY> chunkSize(int chunkSize) {
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        threadLocalFetchOptions.set(
                options != null ? options.chunkSize(chunkSize) :
                    EntityQueryFetchOptions.Factory.withChunkSize(chunkSize));
        return this;
    }

    public EntityResultQuery<ENTITY> limit(int limit) {
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        threadLocalFetchOptions.set(
                options != null ? options.limit(limit) :
                    EntityQueryFetchOptions.Factory.withLimit(limit));
        return this;
    }

    public EntityResultQuery<ENTITY> offset(int offset) {
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        threadLocalFetchOptions.set(
                options != null ? options.offset(offset) :
                    EntityQueryFetchOptions.Factory.withOffset(offset));
        return this;
    }

    public EntityResultQuery<ENTITY> prefetchSize(int prefetchSize) {
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        threadLocalFetchOptions.set(
                options != null ? options.prefetchSize(prefetchSize) :
                    EntityQueryFetchOptions.Factory.withPrefetchSize(prefetchSize));
        return this;
    }

    public EntityResultQuery<ENTITY> setKeysOnly() {
        if (!query.isKeysOnly()) {
            query.setKeysOnly();
        }
        return this;
    }

    public EntityResultQuery<ENTITY> cursor(Cursor cursor) {
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        threadLocalFetchOptions.set(
                options != null ? options.cursor(cursor) :
                    EntityQueryFetchOptions.Factory.withCursor(cursor));
        return this;
    }

    public EntityResultIterable<ENTITY> asIterable() {
        final PreparedQuery pq = getPreparedQuery();
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        return new EntityQueryIterable<ENTITY>(
                options != null ? pq.asQueryResultIterable(((EntityQueryFetchOptionsImpl)options).toFetchOptions()) :
                    pq.asQueryResultIterable(), query.isKeysOnly(), factory);
    }

    public EntityResultIterator<ENTITY> asIterator() {
        final PreparedQuery pq = getPreparedQuery();
        final EntityQueryFetchOptions options = threadLocalFetchOptions.get();
        return new EntityQueryIterator<ENTITY>(
                options != null ? pq.asQueryResultIterator(((EntityQueryFetchOptionsImpl)options).toFetchOptions()) :
                    pq.asQueryResultIterator(), query.isKeysOnly(), factory);
    }

    /**
     * Return a result as a list.
     * 
     * This operation is not efficient way to get a result
     * because it needs much memory than iterator because of copy.
     * So, it causes waste of memory.
     * @return a list instance.
     */
    public EntityResultList<ENTITY> asList() {
        return new EntityQueryList<ENTITY>(asIterable());
    }

    public ENTITY asSingleEntity() {
        return factory.initInstance(getPreparedQuery().asSingleEntity());
    }

    public int countEntities() {
        return getPreparedQuery().countEntities();
    }
    

    // Iterable.
    public Iterator<ENTITY> iterator() {
        return asIterable().iterator();
    }

    
    void addFilter(String propertyName, Query.FilterOperator op, Object value) {
        query.addFilter(propertyName, op, value);
    }
    
    void addSorter(String propertyName, Query.SortDirection direction) {
        query.addSort(propertyName, direction);
    }
    
    private PreparedQuery getPreparedQuery() {
        if (pq != null) {
            return pq;
        }

        final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        final Transaction tx = ds.getCurrentTransaction(null);
        return pq = tx != null ? ds.prepare(tx, query) : ds.prepare(query);
    }

    
    private static class EntityQueryIterable<ENTITY2 extends EntityBase<ENTITY2>> implements EntityResultIterable<ENTITY2> {
        private final QueryResultIterable<Entity> iterable;
        private final EntityBaseFactory<ENTITY2> entityFactory;
        private final boolean keysOnly;
        
        EntityQueryIterable(QueryResultIterable<Entity> iterable, boolean keysOnly, EntityBaseFactory<ENTITY2> entityFactory) {
            this.iterable = iterable;
            this.entityFactory = entityFactory;
            this.keysOnly = keysOnly;
        }

        public EntityResultIterator<ENTITY2> iterator() {
            return new EntityQueryIterator<ENTITY2>(iterable.iterator(), keysOnly, entityFactory);
        }
        
    }
    
    private static class EntityQueryIterator<ENTITY3 extends EntityBase<ENTITY3>> implements EntityResultIterator<ENTITY3> {
        private final QueryResultIterator<Entity> iterator;
        private final EntityBaseFactory<ENTITY3> entityFactory;
        private final boolean keysOnly;

        EntityQueryIterator(QueryResultIterator<Entity> iterator, boolean keysOnly, EntityBaseFactory<ENTITY3> entityFactory) {
            this.iterator = iterator;
            this.entityFactory = entityFactory;
            this.keysOnly = keysOnly;
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public ENTITY3 next() {
            if (keysOnly) {
                return entityFactory.initInstance(iterator.next().getKey());
            } else {
                return entityFactory.initInstance(iterator.next());
            }
        }

        public void remove() {
            iterator.remove();
        }
        
        public Cursor getCursor() {
            return iterator.getCursor();
        }

    }

    private static class EntityQueryList<ENTITY3 extends EntityBase<ENTITY3>> implements EntityResultList<ENTITY3> {
        private final List<ENTITY3> baseList;
        private final Cursor cursor;

        EntityQueryList(EntityResultIterable<ENTITY3> iterable) {
            this.baseList = new LinkedList<ENTITY3>();
 
            for (ENTITY3 entity : iterable) {
                baseList.add(entity);
            }
            
            cursor = iterable.iterator().getCursor();
        }

        public boolean add(ENTITY3 e) {
            return baseList.add(e);
        }

        public void add(int index, ENTITY3 element) {
            baseList.add(index, element);
        }

        public boolean addAll(Collection<? extends ENTITY3> c) {
            return baseList.addAll(c);
        }

        public boolean addAll(int index, Collection<? extends ENTITY3> c) {
            return baseList.addAll(index, c);
        }

        public void clear() {
            baseList.clear();
        }

        public boolean contains(Object o) {
            return baseList.contains(o);
        }

        public boolean containsAll(Collection<?> c) {
            return baseList.containsAll(c);
        }

        public ENTITY3 get(int index) {
            return baseList.get(index);
        }

        public int indexOf(Object o) {
            return baseList.indexOf(o);
        }

        public boolean isEmpty() {
            return baseList.isEmpty();
        }

        public Iterator<ENTITY3> iterator() {
            return baseList.iterator();
        }

        public int lastIndexOf(Object o) {
            return baseList.lastIndexOf(o);
        }

        public ListIterator<ENTITY3> listIterator() {
            return baseList.listIterator();
        }

        public ListIterator<ENTITY3> listIterator(int index) {
            return baseList.listIterator(index);
        }

        public boolean remove(Object o) {
            return baseList.remove(o);
        }

        public ENTITY3 remove(int index) {
            return baseList.remove(index);
        }

        public boolean removeAll(Collection<?> c) {
            return baseList.removeAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return baseList.retainAll(c);
        }

        public ENTITY3 set(int index, ENTITY3 element) {
            return baseList.set(index, element);
        }

        public int size() {
            return baseList.size();
        }

        public List<ENTITY3> subList(int fromIndex, int toIndex) {
            return baseList.subList(fromIndex, toIndex);
        }

        public Object[] toArray() {
            return baseList.toArray();
        }

        public <T> T[] toArray(T[] a) {
            return baseList.toArray(a);
        }
        
        public Cursor getCursor() {
            return cursor;
        }

    }
}
