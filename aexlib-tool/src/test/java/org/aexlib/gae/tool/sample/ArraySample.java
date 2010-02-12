//
// generated code.
//
package org.aexlib.gae.tool.sample;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.tool.sample.version.*;
import org.aexlib.gae.datastore.anno.Kind;


@Kind("ArraySample")
public class ArraySample extends EntityIdBase<ArraySample> {
    public static final EntityIdFactory<ArraySample> FACTORY =
        EntityFactory.getEntityIdFactory(ArraySample.class,
            new EntityCreator<ArraySample>() {
        public ArraySample newInstance() {
            return new ArraySample();
        }
    });
    
    public static final EntityQueryFactory<ArraySample> QUERY =
        EntityQueryFactory.getInstance(ArraySample.class, FACTORY);



    // Version Info field.


    // Property Info fields.
    public static final EntityPropertyInfo<ArraySample, boolean[]> BOOLEAN_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, boolean[].class, "BooleanArray");

    public static final EntityPropertyInfo<ArraySample, byte[]> BYTE_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, byte[].class, "ByteArray");

    public static final EntityPropertyInfo<ArraySample, short[]> SHORT_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, short[].class, "ShortArray");

    public static final EntityPropertyInfo<ArraySample, char[]> CHAR_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, char[].class, "CharArray");

    public static final EntityPropertyInfo<ArraySample, int[]> INT_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, int[].class, "IntArray");

    public static final EntityPropertyInfo<ArraySample, long[]> LONG_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, long[].class, "LongArray");

    public static final EntityPropertyInfo<ArraySample, float[]> FLOAT_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, float[].class, "FloatArray");

    public static final EntityPropertyInfo<ArraySample, double[]> DOUBLE_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, double[].class, "DoubleArray");

    public static final EntityPropertyInfo<ArraySample, java.lang.String[]> STRING_ARRAY =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, java.lang.String[].class, "StringArray");

    public static final EntityIndexablePropertyInfo<ArraySample, byte[]> BYTE_INDEXABLE_ARRAY =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(ArraySample.class, byte[].class, "ByteIndexableArray");

    public static final EntityPropertyInfo<ArraySample, byte[][]> BYTE_ARRAY2 =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, byte[][].class, "ByteArray2");

    public static final EntityPropertyInfo<ArraySample, java.lang.String[][]> STRING_ARRAY2 =
        EntityPropertyInfoFactory.getPropertyInfo(ArraySample.class, java.lang.String[][].class, "StringArray2");



    // Version field.


    // Property fields.
    private final EntityProperty<ArraySample, boolean[]> booleanArray;

    private final EntityProperty<ArraySample, byte[]> byteArray;

    private final EntityProperty<ArraySample, short[]> shortArray;

    private final EntityProperty<ArraySample, char[]> charArray;

    private final EntityProperty<ArraySample, int[]> intArray;

    private final EntityProperty<ArraySample, long[]> longArray;

    private final EntityProperty<ArraySample, float[]> floatArray;

    private final EntityProperty<ArraySample, double[]> doubleArray;

    private final EntityProperty<ArraySample, java.lang.String[]> stringArray;

    private final EntityProperty<ArraySample, byte[]> byteIndexableArray;

    private final EntityProperty<ArraySample, byte[][]> byteArray2;

    private final EntityProperty<ArraySample, java.lang.String[][]> stringArray2;



    // Constructor. This is private to be created by FACTORY.
    private ArraySample() {

        // Initialize a version.


        // Initialize fields.
        booleanArray = BOOLEAN_ARRAY.newInstance(getEntityPropertyAccess());

        byteArray = BYTE_ARRAY.newInstance(getEntityPropertyAccess());

        shortArray = SHORT_ARRAY.newInstance(getEntityPropertyAccess());

        charArray = CHAR_ARRAY.newInstance(getEntityPropertyAccess());

        intArray = INT_ARRAY.newInstance(getEntityPropertyAccess());

        longArray = LONG_ARRAY.newInstance(getEntityPropertyAccess());

        floatArray = FLOAT_ARRAY.newInstance(getEntityPropertyAccess());

        doubleArray = DOUBLE_ARRAY.newInstance(getEntityPropertyAccess());

        stringArray = STRING_ARRAY.newInstance(getEntityPropertyAccess());

        byteIndexableArray = BYTE_INDEXABLE_ARRAY.newInstance(getEntityPropertyAccess());

        byteArray2 = BYTE_ARRAY2.newInstance(getEntityPropertyAccess());

        stringArray2 = STRING_ARRAY2.newInstance(getEntityPropertyAccess());




    }



    public boolean[] getBooleanArray() throws EntityNotFoundException {
        return booleanArray.get();
    }
    public byte[] getByteArray() throws EntityNotFoundException {
        return byteArray.get();
    }
    public short[] getShortArray() throws EntityNotFoundException {
        return shortArray.get();
    }
    public char[] getCharArray() throws EntityNotFoundException {
        return charArray.get();
    }
    public int[] getIntArray() throws EntityNotFoundException {
        return intArray.get();
    }
    public long[] getLongArray() throws EntityNotFoundException {
        return longArray.get();
    }
    public float[] getFloatArray() throws EntityNotFoundException {
        return floatArray.get();
    }
    public double[] getDoubleArray() throws EntityNotFoundException {
        return doubleArray.get();
    }
    public java.lang.String[] getStringArray() throws EntityNotFoundException {
        return stringArray.get();
    }
    public byte[] getByteIndexableArray() throws EntityNotFoundException {
        return byteIndexableArray.get();
    }
    public byte[][] getByteArray2() throws EntityNotFoundException {
        return byteArray2.get();
    }
    public java.lang.String[][] getStringArray2() throws EntityNotFoundException {
        return stringArray2.get();
    }


    public ArraySample setBooleanArray(final boolean[] value) throws EntityNotFoundException {
        this.booleanArray.set(value);
        return this;
    }

    public ArraySample removeBooleanArray() throws EntityNotFoundException {
        booleanArray.remove();
        return this;
    }
    
    public ArraySample setByteArray(final byte[] value) throws EntityNotFoundException {
        this.byteArray.set(value);
        return this;
    }

    public ArraySample removeByteArray() throws EntityNotFoundException {
        byteArray.remove();
        return this;
    }
    
    public ArraySample setShortArray(final short[] value) throws EntityNotFoundException {
        this.shortArray.set(value);
        return this;
    }

    public ArraySample removeShortArray() throws EntityNotFoundException {
        shortArray.remove();
        return this;
    }
    
    public ArraySample setCharArray(final char[] value) throws EntityNotFoundException {
        this.charArray.set(value);
        return this;
    }

    public ArraySample removeCharArray() throws EntityNotFoundException {
        charArray.remove();
        return this;
    }
    
    public ArraySample setIntArray(final int[] value) throws EntityNotFoundException {
        this.intArray.set(value);
        return this;
    }

    public ArraySample removeIntArray() throws EntityNotFoundException {
        intArray.remove();
        return this;
    }
    
    public ArraySample setLongArray(final long[] value) throws EntityNotFoundException {
        this.longArray.set(value);
        return this;
    }

    public ArraySample removeLongArray() throws EntityNotFoundException {
        longArray.remove();
        return this;
    }
    
    public ArraySample setFloatArray(final float[] value) throws EntityNotFoundException {
        this.floatArray.set(value);
        return this;
    }

    public ArraySample removeFloatArray() throws EntityNotFoundException {
        floatArray.remove();
        return this;
    }
    
    public ArraySample setDoubleArray(final double[] value) throws EntityNotFoundException {
        this.doubleArray.set(value);
        return this;
    }

    public ArraySample removeDoubleArray() throws EntityNotFoundException {
        doubleArray.remove();
        return this;
    }
    
    public ArraySample setStringArray(final java.lang.String[] value) throws EntityNotFoundException {
        this.stringArray.set(value);
        return this;
    }

    public ArraySample removeStringArray() throws EntityNotFoundException {
        stringArray.remove();
        return this;
    }
    
    public ArraySample setByteIndexableArray(final byte[] value) throws EntityNotFoundException {
        this.byteIndexableArray.set(value);
        return this;
    }

    public ArraySample removeByteIndexableArray() throws EntityNotFoundException {
        byteIndexableArray.remove();
        return this;
    }
    
    public ArraySample setByteArray2(final byte[][] value) throws EntityNotFoundException {
        this.byteArray2.set(value);
        return this;
    }

    public ArraySample removeByteArray2() throws EntityNotFoundException {
        byteArray2.remove();
        return this;
    }
    
    public ArraySample setStringArray2(final java.lang.String[][] value) throws EntityNotFoundException {
        this.stringArray2.set(value);
        return this;
    }

    public ArraySample removeStringArray2() throws EntityNotFoundException {
        stringArray2.remove();
        return this;
    }
    

}


