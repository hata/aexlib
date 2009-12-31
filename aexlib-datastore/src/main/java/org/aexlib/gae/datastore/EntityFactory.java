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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityFactory {
    public static <ENTITY extends EntityNameBase<ENTITY>> EntityNameFactory<ENTITY>
    getEntityNameFactory(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
        return new EntityNameFactoryImpl<ENTITY>(entityClass, creator);
    }

    public static <ENTITY extends EntityIdBase<ENTITY>> EntityIdFactory<ENTITY>
    getEntityIdFactory(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
        return new EntityIdFactoryImpl<ENTITY>(entityClass, creator);
    }

    public static <ENTITY extends EntityChildNameBase<ENTITY, PARENT_ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>>
    EntityChildNameFactory<ENTITY, PARENT_ENTITY>
    getEntityChildNameFactory(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
        return new EntityChildNameFactoryImpl<ENTITY, PARENT_ENTITY>(entityClass, creator);
    }

    public static <ENTITY extends EntityChildIdBase<ENTITY, PARENT_ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>>
    EntityChildIdFactory<ENTITY, PARENT_ENTITY>
    getEntityChildIdFactory(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
        return new EntityChildIdFactoryImpl<ENTITY, PARENT_ENTITY>(entityClass, creator);
    }

    
    private static class EntityNameFactoryImpl<ENTITY extends EntityNameBase<ENTITY>>
    extends AbstractEntityBaseFactory<ENTITY> implements EntityNameFactory<ENTITY> {
        private Class<ENTITY> entityClass;

        public EntityNameFactoryImpl(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
            super(creator);
            this.entityClass = entityClass;
        }

        public ENTITY initInstance(String name) {
            Key key = KeyFactory.createKey(EntityBase.getKindName(entityClass), name);
            return initInstance(key);
        }

    }

    private static class EntityIdFactoryImpl<ENTITY extends EntityIdBase<ENTITY>>
    extends AbstractEntityBaseFactory<ENTITY> implements EntityIdFactory<ENTITY> {
        private final Class<ENTITY> entityClass;

        public EntityIdFactoryImpl(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
            super(creator);
            this.entityClass = entityClass;
        }

        public ENTITY initInstance() {
            ENTITY entity = getCreator().newInstance();
            entity.init(EntityBase.getKindName(entityClass));
            return entity;
        }

        protected void init(ENTITY entity, String kind) {
            entity.init(kind);
        }
        
    /* TODO: Disable this now... I need to find a way for unique -id.
        public ENTITY createEntity(long id) {
            Key key = KeyFactory.createKey(EntityBaseImpl.getKindName(entityClass), id);
            ENTITY entity = newInstance();
            entity.setKey(key);
            return entity;
        }
    */    
    }
    
    
    private static class EntityChildIdFactoryImpl<ENTITY extends EntityChildIdBase<ENTITY, PARENT_ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>>
    extends AbstractEntityBaseFactory<ENTITY> implements EntityChildIdFactory<ENTITY, PARENT_ENTITY> {
        private final Class<ENTITY> entityClass;

        public EntityChildIdFactoryImpl(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
            super(creator);
            this.entityClass = entityClass;
        }

        public ENTITY initInstance(PARENT_ENTITY parent) {
            ENTITY entity = getCreator().newInstance();
            entity.init(parent.getKey(), EntityBase.getKindName(entityClass));
            return entity;
        }
    /* may not good until I know a way to allocate unique-id.
        public ENTITY getEntity(PARENT_ENTITY parent, long id) {
            Key key = KeyFactory.createKey(parent.getKey(), EntityBaseImpl.getKindName(entityClass), id);
            ENTITY entity = newInstance();
            entity.setKey(parent.getKey(), key);
            return entity;
        }
    */    
    }
    
    private static class EntityChildNameFactoryImpl<ENTITY extends EntityChildNameBase<ENTITY, PARENT_ENTITY>, PARENT_ENTITY extends EntityBase<PARENT_ENTITY>>
    extends AbstractEntityBaseFactory<ENTITY> implements EntityChildNameFactory<ENTITY, PARENT_ENTITY> {
        private final Class<ENTITY> entityClass;

        public EntityChildNameFactoryImpl(Class<ENTITY> entityClass, EntityCreator<ENTITY> creator) {
            super(creator);
            this.entityClass = entityClass;
        }

        public ENTITY initInstance(PARENT_ENTITY parent, String name) {
            Key key = KeyFactory.createKey(parent.getKey(), EntityBase.getKindName(entityClass), name);
            ENTITY entity = getCreator().newInstance();
            entity.init(key);
            return entity;
        }
    }


}
