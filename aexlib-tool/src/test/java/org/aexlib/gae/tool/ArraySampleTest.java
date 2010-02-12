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

import org.aexlib.gae.tool.sample.ArraySample;

public class ArraySampleTest extends ToolGAEBaseTestCase {

    ArraySample entity;

    protected void setUp() throws Exception {
        super.setUp();
        
        entity = ArraySample.FACTORY.initInstance();
        entity.putIfAbsent();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        entity = null;
    }
    

    public void testGetBooleanArray() throws Exception {
        assertNull(entity.getBooleanArray());
        entity.setBooleanArray(new boolean[]{true, false});
        entity.put();
        boolean[] values = entity.getBooleanArray();
        assertEquals(2, values.length);
        assertTrue(values[0]);
        assertFalse(values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getBooleanArray();
        assertEquals(2, values.length);
        assertTrue(values[0]);
        assertFalse(values[1]);
        entity.removeBooleanArray();
        assertNull(entity.getBooleanArray());
    }

    public void testGetByteArray() throws Exception {
        assertNull(entity.getByteArray());
        entity.setByteArray(new byte[]{1, 2});
        entity.put();
        byte[] values = entity.getByteArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getByteArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity.removeByteArray();
        assertNull(entity.getByteArray());
    }

    public void testGetShortArray() throws Exception {
        assertNull(entity.getShortArray());
        entity.setShortArray(new short[]{1, 2});
        entity.put();
        short[] values = entity.getShortArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getShortArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity.removeShortArray();
        assertNull(entity.getShortArray());
    }

    public void testGetCharArray() throws Exception {
        assertNull(entity.getCharArray());
        entity.setCharArray(new char[]{1, 2});
        entity.put();
        char[] values = entity.getCharArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getCharArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity.removeCharArray();
        assertNull(entity.getCharArray());
    }

    public void testGetIntArray() throws Exception {
        assertNull(entity.getIntArray());
        entity.setIntArray(new int[]{1, 2});
        entity.put();
        int[] values = entity.getIntArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getIntArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity.removeIntArray();
        assertNull(entity.getIntArray());
    }

    public void testGetLongArray() throws Exception {
        assertNull(entity.getLongArray());
        entity.setLongArray(new long[]{1, 2});
        entity.put();
        long[] values = entity.getLongArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getLongArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity.removeLongArray();
        assertNull(entity.getLongArray());
    }

    public void testGetFloatArray() throws Exception {
        assertNull(entity.getFloatArray());
        entity.setFloatArray(new float[]{1.0f, 2.0f});
        entity.put();
        float[] values = entity.getFloatArray();
        assertEquals(2, values.length);
        assertEquals(1.0f, values[0], 0.1);
        assertEquals(2.0f, values[1], 0.1);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getFloatArray();
        assertEquals(2, values.length);
        assertEquals(1.0f, values[0], 0.1);
        assertEquals(2.0f, values[1], 0.1);
        entity.removeFloatArray();
        assertNull(entity.getFloatArray());
    }

    public void testGetDoubleArray() throws Exception {
        assertNull(entity.getDoubleArray());
        entity.setDoubleArray(new double[]{1.0, 2.0});
        entity.put();
        double[] values = entity.getDoubleArray();
        assertEquals(2, values.length);
        assertEquals(1.0, values[0], 0.1);
        assertEquals(2.0, values[1], 0.1);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getDoubleArray();
        assertEquals(2, values.length);
        assertEquals(1.0, values[0], 0.1);
        assertEquals(2.0, values[1], 0.1);
        entity.removeDoubleArray();
        assertNull(entity.getDoubleArray());
    }

    public void testGetStringArray() throws Exception {
        assertNull(entity.getStringArray());
        entity.setStringArray(new String[]{"1", "2"});
        entity.put();
        String[] values = entity.getStringArray();
        assertEquals(2, values.length);
        assertEquals("1", values[0]);
        assertEquals("2", values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getStringArray();
        assertEquals(2, values.length);
        assertEquals("1", values[0]);
        assertEquals("2", values[1]);
        entity.removeStringArray();
        assertNull(entity.getStringArray());
    }

    public void testGetByteIndexableArray() throws Exception {
        assertNull(entity.getByteIndexableArray());
        entity.setByteIndexableArray(new byte[]{1, 2});
        entity.put();
        byte[] values = entity.getByteIndexableArray();
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getByteIndexableArray();

        int count = ArraySample.QUERY.query().filter(ArraySample.BYTE_INDEXABLE_ARRAY.equal(new byte[]{1, 2})).countEntities();
        assertEquals(1, count);
        
        assertEquals(2, values.length);
        assertEquals(1, values[0]);
        assertEquals(2, values[1]);
        entity.removeByteIndexableArray();
        assertNull(entity.getByteIndexableArray());
    }

    public void testGetByteArray2() throws Exception {
        assertNull(entity.getByteArray2());
        entity.setByteArray2(new byte[][]{{1}, {2}});
        entity.put();
        byte[][] values = entity.getByteArray2();
        assertEquals(2, values.length);
        assertEquals(1, values[0][0]);
        assertEquals(2, values[1][0]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getByteArray2();
        
        assertEquals(2, values.length);
        assertEquals(1, values[0][0]);
        assertEquals(2, values[1][0]);
        entity.removeByteArray2();
        assertNull(entity.getByteArray2());
    }

    public void testGetStringArray2() throws Exception {
        assertNull(entity.getStringArray2());
        entity.setStringArray2(new String[][]{{"1"}, {"2"}});
        entity.put();
        String[][] values = entity.getStringArray2();
        assertEquals(2, values.length);
        assertEquals("1", values[0][0]);
        assertEquals("2", values[1][0]);
        entity = ArraySample.FACTORY.initInstance(entity.getKey());
        values = entity.getStringArray2();
        assertEquals(2, values.length);
        assertEquals("1", values[0][0]);
        assertEquals("2", values[1][0]);
        entity.removeStringArray2();
        assertNull(entity.getStringArray2());
    }

}
