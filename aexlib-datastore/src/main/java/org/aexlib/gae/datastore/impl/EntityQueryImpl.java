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

import java.util.Iterator;

import com.archtea.gae.datastore.EntityBase;
import com.archtea.gae.datastore.EntityBaseFactory;
import com.archtea.gae.datastore.EntityPropertyFilter;
import com.archtea.gae.datastore.EntityPropertySorter;
import com.archtea.gae.datastore.EntityQuery;
import com.archtea.gae.datastore.EntityQueryFetchOptions;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class EntityQueryImpl<ENTITY extends EntityBase<ENTITY>> implements EntityQuery<ENTITY> {
    private Query query;
    private EntityBaseFactory<ENTITY> factory;
    private PreparedQuery pq;

    public EntityQueryImpl(Query query, EntityBaseFactory<ENTITY> factory) {
        this.query = query;
        this.factory = factory;
    }

    public <PROPERTY_TYPE> EntityQueryImpl<ENTITY> filter(EntityPropertyFilter<ENTITY, PROPERTY_TYPE> filter) {
        ((EntityPropertyFilterImpl<ENTITY, PROPERTY_TYPE>)filter).addFilter(this);
        return this;
    }

    public EntityQueryImpl<ENTITY> sort(EntityPropertySorter<ENTITY> sort) {
        ((EntityPropertySorterImpl<ENTITY>)sort).addSort(this);
        return this;
    }
    
    public Iterable<ENTITY> asIterable() {
        return new EntityQueryIterable<ENTITY>(getPreparedQuery().asIterable(), factory);
    }

    public Iterable<ENTITY> asIterable(EntityQueryFetchOptions options) {
        PreparedQuery pq = getPreparedQuery();
        return new EntityQueryIterable<ENTITY>(
                pq.asIterable(((EntityQueryFetchOptionsImpl)options).toFetchOptions()), factory);
    }

    public Iterator<ENTITY> asIterator() {
        return new EntityQueryIterator<ENTITY>(getPreparedQuery().asIterator(), factory);
    }

    public Iterator<ENTITY> asIterator(EntityQueryFetchOptions options) {
        PreparedQuery pq = getPreparedQuery();
        return new EntityQueryIterator<ENTITY>(pq.asIterator(
                ((EntityQueryFetchOptionsImpl)options).toFetchOptions()), factory);
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

    
    private static class EntityQueryIterable<ENTITY2 extends EntityBase<ENTITY2>> implements Iterable<ENTITY2> {
        private Iterable<Entity> iterable;
        private EntityBaseFactory<ENTITY2> entityFactory;
        
        EntityQueryIterable(Iterable<Entity> iterable, EntityBaseFactory<ENTITY2> entityFactory) {
            this.iterable = iterable;
            this.entityFactory = entityFactory;
        }

        public Iterator<ENTITY2> iterator() {
            return new EntityQueryIterator<ENTITY2>(iterable.iterator(), entityFactory);
        }
        
    }
    
    private static class EntityQueryIterator<ENTITY3 extends EntityBase<ENTITY3>> implements Iterator<ENTITY3> {
        private final Iterator<Entity> iterator;
        private final EntityBaseFactory<ENTITY3> entityFactory;

        EntityQueryIterator(Iterator<Entity> iterator, EntityBaseFactory<ENTITY3> entityFactory) {
            this.iterator = iterator;
            this.entityFactory = entityFactory;
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public ENTITY3 next() {
            return entityFactory.initInstance(iterator.next());
        }

        public void remove() {
            iterator.remove();
        }
        
    }

}
