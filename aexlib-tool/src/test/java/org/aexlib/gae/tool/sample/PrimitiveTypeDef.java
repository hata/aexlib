//
// generated code.
//
package org.aexlib.gae.tool.sample;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.tool.sample.version.*;



public class PrimitiveTypeDef extends EntityIdBase<PrimitiveTypeDef> {
    public static final EntityIdFactory<PrimitiveTypeDef> FACTORY =
        EntityFactory.getEntityIdFactory(PrimitiveTypeDef.class,
            new EntityCreator<PrimitiveTypeDef>() {
        public PrimitiveTypeDef newInstance() {
            return new PrimitiveTypeDef();
        }
    });
    
    public static final EntityQueryFactory<PrimitiveTypeDef> QUERY =
        EntityQueryFactory.getInstance(PrimitiveTypeDef.class, FACTORY);



    // Version Info field.


    // Property Info fields.
    public static final EntityPropertyInfo<PrimitiveTypeDef, Byte> BYTE_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Byte.class, "ByteNoIndex");

    public static final EntityPropertyInfo<PrimitiveTypeDef, Short> SHORT_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Short.class, "ShortNoIndex");

    public static final EntityPropertyInfo<PrimitiveTypeDef, Character> CHAR_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Character.class, "CharNoIndex");

    public static final EntityPropertyInfo<PrimitiveTypeDef, Integer> INT_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Integer.class, "IntNoIndex");

    public static final EntityPropertyInfo<PrimitiveTypeDef, Long> LONG_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Long.class, "LongNoIndex");

    public static final EntityPropertyInfo<PrimitiveTypeDef, Float> FLOAT_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Float.class, "FloatNoIndex");

    public static final EntityPropertyInfo<PrimitiveTypeDef, Double> DOUBLE_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveTypeDef.class, Double.class, "DoubleNoIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Byte> BYTE_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Byte.class, "ByteIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Short> SHORT_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Short.class, "ShortIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Character> CHAR_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Character.class, "CharIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Integer> INT_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Integer.class, "IntIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Long> LONG_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Long.class, "LongIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Float> FLOAT_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Float.class, "FloatIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveTypeDef, Double> DOUBLE_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveTypeDef.class, Double.class, "DoubleIndex");



    // Version field.


    // Property fields.
    private final EntityProperty<PrimitiveTypeDef, Byte> byteNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Short> shortNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Character> charNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Integer> intNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Long> longNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Float> floatNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Double> doubleNoIndex;

    private final EntityProperty<PrimitiveTypeDef, Byte> byteIndex;

    private final EntityProperty<PrimitiveTypeDef, Short> shortIndex;

    private final EntityProperty<PrimitiveTypeDef, Character> charIndex;

    private final EntityProperty<PrimitiveTypeDef, Integer> intIndex;

    private final EntityProperty<PrimitiveTypeDef, Long> longIndex;

    private final EntityProperty<PrimitiveTypeDef, Float> floatIndex;

    private final EntityProperty<PrimitiveTypeDef, Double> doubleIndex;



    // Constructor. This is private to be created by FACTORY.
    private PrimitiveTypeDef() {

        // Initialize a version.


        // Initialize fields.
        byteNoIndex = BYTE_NO_INDEX.newInstance(getEntityPropertyAccess());

        shortNoIndex = SHORT_NO_INDEX.newInstance(getEntityPropertyAccess());

        charNoIndex = CHAR_NO_INDEX.newInstance(getEntityPropertyAccess());

        intNoIndex = INT_NO_INDEX.newInstance(getEntityPropertyAccess());

        longNoIndex = LONG_NO_INDEX.newInstance(getEntityPropertyAccess());

        floatNoIndex = FLOAT_NO_INDEX.newInstance(getEntityPropertyAccess());

        doubleNoIndex = DOUBLE_NO_INDEX.newInstance(getEntityPropertyAccess());

        byteIndex = BYTE_INDEX.newInstance(getEntityPropertyAccess());

        shortIndex = SHORT_INDEX.newInstance(getEntityPropertyAccess());

        charIndex = CHAR_INDEX.newInstance(getEntityPropertyAccess());

        intIndex = INT_INDEX.newInstance(getEntityPropertyAccess());

        longIndex = LONG_INDEX.newInstance(getEntityPropertyAccess());

        floatIndex = FLOAT_INDEX.newInstance(getEntityPropertyAccess());

        doubleIndex = DOUBLE_INDEX.newInstance(getEntityPropertyAccess());




    }



    public byte getByteNoIndex() throws EntityNotFoundException {
        final Byte value =  byteNoIndex.get();
        return value != null ? value.byteValue() : 0;
    }
    public short getShortNoIndex() throws EntityNotFoundException {
        final Short value =  shortNoIndex.get();
        return value != null ? value.shortValue() : 0;
    }
    public char getCharNoIndex() throws EntityNotFoundException {
        final Character value =  charNoIndex.get();
        return value != null ? value.charValue() : '\u0000';
    }
    public int getIntNoIndex() throws EntityNotFoundException {
        final Integer value =  intNoIndex.get();
        return value != null ? value.intValue() : 0;
    }
    public long getLongNoIndex() throws EntityNotFoundException {
        final Long value =  longNoIndex.get();
        return value != null ? value.longValue() : 0L;
    }
    public float getFloatNoIndex() throws EntityNotFoundException {
        final Float value =  floatNoIndex.get();
        return value != null ? value.floatValue() : 0.0f;
    }
    public double getDoubleNoIndex() throws EntityNotFoundException {
        final Double value =  doubleNoIndex.get();
        return value != null ? value.doubleValue() : 0.0;
    }
    public byte getByteIndex() throws EntityNotFoundException {
        final Byte value =  byteIndex.get();
        return value != null ? value.byteValue() : 1;
    }
    public short getShortIndex() throws EntityNotFoundException {
        final Short value =  shortIndex.get();
        return value != null ? value.shortValue() : 2;
    }
    public char getCharIndex() throws EntityNotFoundException {
        final Character value =  charIndex.get();
        return value != null ? value.charValue() : '\u0061';
    }
    public int getIntIndex() throws EntityNotFoundException {
        final Integer value =  intIndex.get();
        return value != null ? value.intValue() : 3;
    }
    public long getLongIndex() throws EntityNotFoundException {
        final Long value =  longIndex.get();
        return value != null ? value.longValue() : 4L;
    }
    public float getFloatIndex() throws EntityNotFoundException {
        final Float value =  floatIndex.get();
        return value != null ? value.floatValue() : 5.0f;
    }
    public double getDoubleIndex() throws EntityNotFoundException {
        final Double value =  doubleIndex.get();
        return value != null ? value.doubleValue() : 6.0;
    }


    public PrimitiveTypeDef setByteNoIndex(final byte value) throws EntityNotFoundException {
        this.byteNoIndex.set(Byte.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeByteNoIndex() throws EntityNotFoundException {
        byteNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setShortNoIndex(final short value) throws EntityNotFoundException {
        this.shortNoIndex.set(Short.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeShortNoIndex() throws EntityNotFoundException {
        shortNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setCharNoIndex(final char value) throws EntityNotFoundException {
        this.charNoIndex.set(Character.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeCharNoIndex() throws EntityNotFoundException {
        charNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setIntNoIndex(final int value) throws EntityNotFoundException {
        this.intNoIndex.set(Integer.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeIntNoIndex() throws EntityNotFoundException {
        intNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setLongNoIndex(final long value) throws EntityNotFoundException {
        this.longNoIndex.set(Long.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeLongNoIndex() throws EntityNotFoundException {
        longNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setFloatNoIndex(final float value) throws EntityNotFoundException {
        this.floatNoIndex.set(Float.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeFloatNoIndex() throws EntityNotFoundException {
        floatNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setDoubleNoIndex(final double value) throws EntityNotFoundException {
        this.doubleNoIndex.set(Double.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeDoubleNoIndex() throws EntityNotFoundException {
        doubleNoIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setByteIndex(final byte value) throws EntityNotFoundException {
        this.byteIndex.set(Byte.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeByteIndex() throws EntityNotFoundException {
        byteIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setShortIndex(final short value) throws EntityNotFoundException {
        this.shortIndex.set(Short.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeShortIndex() throws EntityNotFoundException {
        shortIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setCharIndex(final char value) throws EntityNotFoundException {
        this.charIndex.set(Character.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeCharIndex() throws EntityNotFoundException {
        charIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setIntIndex(final int value) throws EntityNotFoundException {
        this.intIndex.set(Integer.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeIntIndex() throws EntityNotFoundException {
        intIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setLongIndex(final long value) throws EntityNotFoundException {
        this.longIndex.set(Long.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeLongIndex() throws EntityNotFoundException {
        longIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setFloatIndex(final float value) throws EntityNotFoundException {
        this.floatIndex.set(Float.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeFloatIndex() throws EntityNotFoundException {
        floatIndex.remove();
        return this;
    }
    public PrimitiveTypeDef setDoubleIndex(final double value) throws EntityNotFoundException {
        this.doubleIndex.set(Double.valueOf(value));
        return this;
    }

    public PrimitiveTypeDef removeDoubleIndex() throws EntityNotFoundException {
        doubleIndex.remove();
        return this;
    }

}


