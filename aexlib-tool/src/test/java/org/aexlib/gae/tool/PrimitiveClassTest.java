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


public class PrimitiveClassTest extends ToolGAEBaseTestCase {

    org.aexlib.gae.tool.sample.PrimitiveClassDef entity;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance();
        entity.putIfAbsent();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        entity = null;
    }

    public void testGetBooleanNoIndex() throws Exception {
        assertNull(entity.getBooleanNoIndex());
        entity.setBooleanNoIndex(true);
        entity.put();
        assertEquals(Boolean.TRUE, entity.getBooleanNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals(Boolean.TRUE, entity.getBooleanNoIndex());
        
        entity.removeBooleanNoIndex();
        entity.put();
        assertNull(entity.getBooleanNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getBooleanNoIndex());
    }

    public void testGetByteNoIndex() throws Exception {
        assertNull(entity.getByteNoIndex());
        entity.setByteNoIndex((byte)1);
        entity.put();
        assertEquals((byte)1, (byte)entity.getByteNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((byte)1, (byte)entity.getByteNoIndex());
        
        entity.removeByteNoIndex();
        entity.put();
        assertNull(entity.getByteNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getByteNoIndex());
    }

    public void testGetShortNoIndex() throws Exception {
        assertNull(entity.getByteNoIndex());
        entity.setShortNoIndex((short)1);
        entity.put();
        assertEquals((short)1, (short)entity.getShortNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((short)1, (short)entity.getShortNoIndex());
        
        entity.removeShortNoIndex();
        entity.put();
        assertNull(entity.getShortNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getShortNoIndex());
    }

    public void testGetCharNoIndex() throws Exception {
        assertNull(entity.getCharNoIndex());
        entity.setCharNoIndex((char)1);
        entity.put();
        assertEquals((char)1, (char)entity.getCharNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((char)1, (char)entity.getCharNoIndex());

        entity.removeCharNoIndex();
        entity.put();
        assertNull(entity.getCharNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getCharNoIndex());
    }

    public void testGetIntNoIndex() throws Exception {
        assertNull(entity.getIntNoIndex());
        entity.setIntNoIndex((int)1);
        entity.put();
        assertEquals((int)1, (int)entity.getIntNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((int)1, (int)entity.getIntNoIndex());

        entity.removeIntNoIndex();
        entity.put();
        assertNull(entity.getIntNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getIntNoIndex());
    }

    public void testGetLongNoIndex() throws Exception {
        assertNull(entity.getLongNoIndex());
        entity.setLongNoIndex((long)1);
        entity.put();
        assertEquals((long)1, (long)entity.getLongNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((long)1, (long)entity.getLongNoIndex());

        entity.removeLongNoIndex();
        entity.put();
        assertNull(entity.getLongNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getLongNoIndex());
    }

    public void testGetFloatNoIndex() throws Exception {
        assertNull(entity.getFloatNoIndex());
        entity.setFloatNoIndex(1.0f);
        entity.put();
        assertEquals(1.0f, (float)entity.getFloatNoIndex(), 0.1f);
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1.0f, (float)entity.getFloatNoIndex(), 0.1f);

        entity.removeFloatNoIndex();
        entity.put();
        assertNull(entity.getFloatNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getFloatNoIndex());
    }

    public void testGetDoubleNoIndex() throws Exception {
        assertNull(entity.getDoubleNoIndex());
        entity.setDoubleNoIndex(1.0);
        entity.put();
        assertEquals(1.0, (double)entity.getDoubleNoIndex(), 0.1);
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1.0, (double)entity.getDoubleNoIndex(), 0.1);

        entity.removeDoubleNoIndex();
        entity.put();
        assertNull(entity.getDoubleNoIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getDoubleNoIndex());
    }

    public void testGetBooleanIndex() throws Exception {
        assertNull(entity.getBooleanIndex());
        entity.setBooleanIndex(true);
        entity.put();
        assertEquals(Boolean.TRUE, entity.getBooleanIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals(Boolean.TRUE, entity.getBooleanIndex());

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.BOOLEAN_INDEX.equal(Boolean.TRUE)).countEntities();
        assertEquals(1, count);
        
        entity.removeBooleanIndex();
        entity.put();
        assertNull(entity.getBooleanIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getBooleanIndex());
    }

    public void testGetByteIndex() throws Exception {
        assertNull(entity.getByteIndex());
        entity.setByteIndex((byte)1);
        entity.put();
        assertEquals((byte)1, (byte)entity.getByteIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((byte)1, (byte)entity.getByteIndex());

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.BYTE_INDEX.equal((byte)1)).countEntities();
        assertEquals(1, count);

        entity.removeByteIndex();
        entity.put();
        assertNull(entity.getByteIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getByteIndex());
    }

    public void testGetShortIndex() throws Exception {
        assertNull(entity.getShortIndex());
        entity.setShortIndex((short)1);
        entity.put();
        assertEquals((short)1, (short)entity.getShortIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((short)1, (short)entity.getShortIndex());

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.SHORT_INDEX.equal((short)1)).countEntities();
        assertEquals(1, count);

        entity.removeShortIndex();
        entity.put();
        assertNull(entity.getShortIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getShortIndex());
    }

    public void testGetCharIndex() throws Exception {
        assertNull(entity.getCharIndex());
        entity.setCharIndex((char)1);
        entity.put();
        assertEquals((char)1, (char)entity.getCharIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((char)1, (char)entity.getCharIndex());

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.CHAR_INDEX.equal((char)1)).countEntities();
        assertEquals(1, count);

        entity.removeCharIndex();
        entity.put();
        assertNull(entity.getCharIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getCharIndex());
    }

    public void testGetIntIndex() throws Exception {
        assertNull(entity.getIntIndex());
        entity.setIntIndex((int)1);
        entity.put();
        assertEquals((int)1, (int)entity.getIntIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((int)1, (int)entity.getIntIndex());

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.INT_INDEX.equal((int)1)).countEntities();
        assertEquals(1, count);

        entity.removeIntIndex();
        entity.put();
        assertNull(entity.getIntIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getIntIndex());
    }

    public void testGetLongIndex() throws Exception {
        assertNull(entity.getLongIndex());
        entity.setLongIndex((long)1);
        entity.put();
        assertEquals((long)1, (long)entity.getLongIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals((long)1, (long)entity.getLongIndex());

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.LONG_INDEX.equal((long)1)).countEntities();
        assertEquals(1, count);

        entity.removeLongIndex();
        entity.put();
        assertNull(entity.getLongIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getLongIndex());
    }

    public void testGetFloatIndex() throws Exception {
        assertNull(entity.getFloatIndex());
        entity.setFloatIndex(1.0f);
        entity.put();
        assertEquals(1.0f, (float)entity.getFloatIndex(), 0.1f);
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1.0f, (float)entity.getFloatIndex(), 0.1f);

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.FLOAT_INDEX.equal((float)1.0f)).countEntities();
        assertEquals(1, count);

        entity.removeFloatIndex();
        entity.put();
        assertNull(entity.getFloatIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getFloatIndex());
    }

    public void testGetDoubleIndex() throws Exception {
        assertNull(entity.getDoubleIndex());
        entity.setDoubleIndex(1.0);
        entity.put();
        assertEquals(1.0, (double)entity.getDoubleIndex(), 0.1);
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertEquals(1.0, (double)entity.getDoubleIndex(), 0.1);

        int count = org.aexlib.gae.tool.sample.PrimitiveClassDef.QUERY.query().filter( org.aexlib.gae.tool.sample.PrimitiveClassDef.DOUBLE_INDEX.equal(1.0)).countEntities();
        assertEquals(1, count);

        entity.removeDoubleIndex();
        entity.put();
        assertNull(entity.getDoubleIndex());
        entity = org.aexlib.gae.tool.sample.PrimitiveClassDef.FACTORY.initInstance(entity.getKey());
        assertNull(entity.getDoubleIndex());
    }
}
