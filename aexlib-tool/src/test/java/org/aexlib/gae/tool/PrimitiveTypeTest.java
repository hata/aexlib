/*
 * $Id$
 * 
 * Copyright 2010 Hiroki Ata
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
package org.aexlib.gae.tool;

import org.aexlib.gae.tool.sample.PrimitiveTypeDef;

public class PrimitiveTypeTest extends ToolGAEBaseTestCase {

    PrimitiveTypeDef entity;

    protected void setUp() throws Exception {
        super.setUp();
        
        entity = PrimitiveTypeDef.FACTORY.initInstance();
        entity.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        entity = null;
    }

    public void testGetBooleanNoIndex() throws Exception {
        assertEquals(false, entity.getBooleanNoIndex());
        entity.setBooleanNoIndex(true);
        entity.put();
        assertEquals(true, entity.getBooleanNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(true, entity.getBooleanNoIndex());

        entity.removeBooleanNoIndex();
        entity.put();
        assertEquals(false, entity.getBooleanNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(false, entity.getBooleanNoIndex());
    }

    public void testGetByteNoIndex() throws Exception {
        assertEquals(0, entity.getByteNoIndex());
        entity.setByteNoIndex((byte)1);
        entity.put();
        assertEquals(1, entity.getByteNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1, entity.getByteNoIndex());
        
        entity.removeByteNoIndex();
        entity.put();
        assertEquals(0, entity.getByteNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0, entity.getByteNoIndex());
    }

    public void testGetShortNoIndex() throws Exception {
        assertEquals(0, entity.getShortNoIndex());
        entity.setShortNoIndex((short)1);
        entity.put();
        assertEquals(1, entity.getShortNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1, entity.getShortNoIndex());
        
        entity.removeShortNoIndex();
        entity.put();
        assertEquals(0, entity.getShortNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0, entity.getShortNoIndex());
    }

    public void testGetCharNoIndex() throws Exception {
        assertEquals(0, entity.getCharNoIndex());
        entity.setCharNoIndex((char)1);
        entity.put();
        assertEquals(1, entity.getCharNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1, entity.getCharNoIndex());
        
        entity.removeCharNoIndex();
        entity.put();
        assertEquals(0, entity.getCharNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0, entity.getCharNoIndex());
    }

    public void testGetIntNoIndex() throws Exception {
        assertEquals(0, entity.getIntNoIndex());
        entity.setIntNoIndex((int)1);
        entity.put();
        assertEquals(1, entity.getIntNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1, entity.getIntNoIndex());
        
        entity.removeIntNoIndex();
        entity.put();
        assertEquals(0, entity.getIntNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0, entity.getIntNoIndex());
    }

    public void testGetLongNoIndex() throws Exception {
        assertEquals(0, entity.getLongNoIndex());
        entity.setLongNoIndex((long)1);
        entity.put();
        assertEquals(1, entity.getLongNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1, entity.getLongNoIndex());
        
        entity.removeLongNoIndex();
        entity.put();
        assertEquals(0, entity.getLongNoIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0, entity.getLongNoIndex());
    }

    public void testGetFloatNoIndex() throws Exception {
        assertEquals(0f, entity.getFloatNoIndex(), 0.1);
        entity.setFloatNoIndex((float)1);
        entity.put();
        assertEquals(1f, entity.getFloatNoIndex(), 0.1);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1f, entity.getFloatNoIndex(), 0.1);
        
        entity.removeFloatNoIndex();
        entity.put();
        assertEquals(0f, entity.getFloatNoIndex(), 0.1);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0f, entity.getFloatNoIndex(), 0.1);
    }

    public void testGetDoubleNoIndex() throws Exception {
        assertEquals(0.0, entity.getDoubleNoIndex(), 0.1);
        entity.setDoubleNoIndex((double)1);
        entity.put();
        assertEquals(1.0, entity.getDoubleNoIndex(), 0.1);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1.0, entity.getDoubleNoIndex(), 0.1);
        
        entity.removeDoubleNoIndex();
        entity.put();
        assertEquals(0.0, entity.getDoubleNoIndex(), 0.1);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(0.0, entity.getDoubleNoIndex(), 0.1);
    }

    public void testGetBooleanIndex() throws Exception {
        assertEquals(true, entity.getBooleanIndex());
        entity.setBooleanIndex(false);
        entity.put();
        assertEquals(false, entity.getBooleanIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(false, entity.getBooleanIndex());

        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.BOOLEAN_INDEX.equal(false)).countEntities();
        assertEquals(1, count);

        entity.removeBooleanIndex();
        entity.put();
        assertEquals(true, entity.getBooleanIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(true, entity.getBooleanIndex());
    }

    public void testGetByteIndex() throws Exception {
        assertEquals(1, entity.getByteIndex());
        entity.setByteIndex((byte)2);
        entity.put();
        assertEquals(2, entity.getByteIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(2, entity.getByteIndex());

        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.BYTE_INDEX.equal((byte)2)).countEntities();
        assertEquals(1, count);

        entity.removeByteIndex();
        entity.put();
        assertEquals(1, entity.getByteIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1, entity.getByteIndex());
    }

    public void testGetShortIndex() throws Exception {
        assertEquals(2, entity.getShortIndex());
        entity.setShortIndex((byte)3);
        entity.put();
        assertEquals(3, entity.getShortIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(3, entity.getShortIndex());

        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.SHORT_INDEX.equal((short)3)).countEntities();
        assertEquals(1, count);

        entity.removeShortIndex();
        entity.put();
        assertEquals(2, entity.getShortIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(2, entity.getShortIndex());
    }

    public void testGetCharIndex() throws Exception {
        assertEquals('a', entity.getCharIndex());
        entity.setCharIndex('b');
        entity.put();
        assertEquals('b', entity.getCharIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals('b', entity.getCharIndex());

        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.CHAR_INDEX.equal('b')).countEntities();
        assertEquals(1, count);

        entity.removeCharIndex();
        entity.put();
        assertEquals('a', entity.getCharIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals('a', entity.getCharIndex());
    }

    public void testGetIntIndex() throws Exception {
        assertEquals(3, entity.getIntIndex());
        entity.setIntIndex(4);
        entity.put();
        assertEquals(4, entity.getIntIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(4, entity.getIntIndex());

        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.INT_INDEX.equal(4)).countEntities();
        assertEquals(1, count);

        entity.removeIntIndex();
        entity.put();
        assertEquals(3, entity.getIntIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(3, entity.getIntIndex());
    }

    public void testGetLongIndex() throws Exception {
        assertEquals(4, entity.getLongIndex());
        entity.setLongIndex(5);
        entity.put();
        assertEquals(5, entity.getLongIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(5, entity.getLongIndex());

        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.LONG_INDEX.equal(5L)).countEntities();
        assertEquals(1, count);

        entity.removeLongIndex();
        entity.put();
        assertEquals(4, entity.getLongIndex());
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(4, entity.getLongIndex());
    }

    public void testGetFloatIndex() throws Exception {
        assertEquals(5.0f, entity.getFloatIndex(), 0.1f);
        entity.setFloatIndex(6.0f);
        entity.put();
        assertEquals(6.0f, entity.getFloatIndex(), 0.1f);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(6.0f, entity.getFloatIndex(), 0.1f);

        // This may need to use lessThan and greaterThan.
        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.FLOAT_INDEX.equal(6.0f)).countEntities();
        assertEquals(1, count);

        entity.removeFloatIndex();
        entity.put();
        assertEquals(5.0f, entity.getFloatIndex(), 0.1f);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(5.0f, entity.getFloatIndex(), 0.1f);
    }

    public void testGetDoubleIndex() throws Exception {
        assertEquals(6.0f, entity.getDoubleIndex(), 0.1);
        entity.setDoubleIndex(7.0);
        entity.put();
        assertEquals(7.0f, entity.getDoubleIndex(), 0.1);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(7.0f, entity.getDoubleIndex(), 0.1);

        // This may need to use lessThan and greaterThan.
        int count = PrimitiveTypeDef.QUERY.query().filter( PrimitiveTypeDef.DOUBLE_INDEX.equal(7.0)).countEntities();
        assertEquals(1, count);

        entity.removeDoubleIndex();
        entity.put();
        assertEquals(6.0, entity.getDoubleIndex(), 0.1);
        entity = PrimitiveTypeDef.FACTORY.initInstance(entity.getKey());
        assertEquals(6.0, entity.getDoubleIndex(), 0.1);
    }
}
