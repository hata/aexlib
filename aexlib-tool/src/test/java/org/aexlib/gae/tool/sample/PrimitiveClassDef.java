//
// generated code.
//
package org.aexlib.gae.tool.sample;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.tool.sample.version.*;



public class PrimitiveClassDef extends EntityIdBase<PrimitiveClassDef> {
    public static final EntityIdFactory<PrimitiveClassDef> FACTORY =
        EntityFactory.getEntityIdFactory(PrimitiveClassDef.class,
            new EntityCreator<PrimitiveClassDef>() {
        public PrimitiveClassDef newInstance() {
            return new PrimitiveClassDef();
        }
    });
    
    public static final EntityQueryFactory<PrimitiveClassDef> QUERY =
        EntityQueryFactory.getInstance(PrimitiveClassDef.class, FACTORY);



    // Version Info field.


    // Property Info fields.
    public static final EntityPropertyInfo<PrimitiveClassDef, java.lang.Byte> BYTE_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveClassDef.class, java.lang.Byte.class, "ByteNoIndex");

    public static final EntityPropertyInfo<PrimitiveClassDef, java.lang.Short> SHORT_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveClassDef.class, java.lang.Short.class, "ShortNoIndex");

    public static final EntityPropertyInfo<PrimitiveClassDef, java.lang.Character> CHAR_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveClassDef.class, java.lang.Character.class, "CharNoIndex");

    public static final EntityPropertyInfo<PrimitiveClassDef, java.lang.Integer> INT_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveClassDef.class, java.lang.Integer.class, "IntNoIndex");

    public static final EntityPropertyInfo<PrimitiveClassDef, java.lang.Long> LONG_NO_INDEX =
        EntityPropertyInfoFactory.getPropertyInfo(PrimitiveClassDef.class, java.lang.Long.class, "LongNoIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveClassDef, java.lang.Byte> BYTE_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveClassDef.class, java.lang.Byte.class, "ByteIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveClassDef, java.lang.Short> SHORT_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveClassDef.class, java.lang.Short.class, "ShortIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveClassDef, java.lang.Character> CHAR_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveClassDef.class, java.lang.Character.class, "CharIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveClassDef, java.lang.Integer> INT_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveClassDef.class, java.lang.Integer.class, "IntIndex");

    public static final EntityIndexablePropertyInfo<PrimitiveClassDef, java.lang.Long> LONG_INDEX =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(PrimitiveClassDef.class, java.lang.Long.class, "LongIndex");



    // Version field.


    // Property fields.
    private final EntityProperty<PrimitiveClassDef, java.lang.Byte> byteNoIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Short> shortNoIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Character> charNoIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Integer> intNoIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Long> longNoIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Byte> byteIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Short> shortIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Character> charIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Integer> intIndex;

    private final EntityProperty<PrimitiveClassDef, java.lang.Long> longIndex;



    // Constructor. This is private to be created by FACTORY.
    private PrimitiveClassDef() {

        // Initialize a version.


        // Initialize fields.
        byteNoIndex = BYTE_NO_INDEX.newInstance(getEntityPropertyAccess());

        shortNoIndex = SHORT_NO_INDEX.newInstance(getEntityPropertyAccess());

        charNoIndex = CHAR_NO_INDEX.newInstance(getEntityPropertyAccess());

        intNoIndex = INT_NO_INDEX.newInstance(getEntityPropertyAccess());

        longNoIndex = LONG_NO_INDEX.newInstance(getEntityPropertyAccess());

        byteIndex = BYTE_INDEX.newInstance(getEntityPropertyAccess());

        shortIndex = SHORT_INDEX.newInstance(getEntityPropertyAccess());

        charIndex = CHAR_INDEX.newInstance(getEntityPropertyAccess());

        intIndex = INT_INDEX.newInstance(getEntityPropertyAccess());

        longIndex = LONG_INDEX.newInstance(getEntityPropertyAccess());




    }



    public java.lang.Byte getByteNoIndex() throws EntityNotFoundException {
        return byteNoIndex.get();
    }
    public java.lang.Short getShortNoIndex() throws EntityNotFoundException {
        return shortNoIndex.get();
    }
    public java.lang.Character getCharNoIndex() throws EntityNotFoundException {
        return charNoIndex.get();
    }
    public java.lang.Integer getIntNoIndex() throws EntityNotFoundException {
        return intNoIndex.get();
    }
    public java.lang.Long getLongNoIndex() throws EntityNotFoundException {
        return longNoIndex.get();
    }
    public java.lang.Byte getByteIndex() throws EntityNotFoundException {
        return byteIndex.get();
    }
    public java.lang.Short getShortIndex() throws EntityNotFoundException {
        return shortIndex.get();
    }
    public java.lang.Character getCharIndex() throws EntityNotFoundException {
        return charIndex.get();
    }
    public java.lang.Integer getIntIndex() throws EntityNotFoundException {
        return intIndex.get();
    }
    public java.lang.Long getLongIndex() throws EntityNotFoundException {
        return longIndex.get();
    }


    public PrimitiveClassDef setByteNoIndex(final java.lang.Byte value) throws EntityNotFoundException {
        this.byteNoIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeByteNoIndex() throws EntityNotFoundException {
        byteNoIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setShortNoIndex(final java.lang.Short value) throws EntityNotFoundException {
        this.shortNoIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeShortNoIndex() throws EntityNotFoundException {
        shortNoIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setCharNoIndex(final java.lang.Character value) throws EntityNotFoundException {
        this.charNoIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeCharNoIndex() throws EntityNotFoundException {
        charNoIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setIntNoIndex(final java.lang.Integer value) throws EntityNotFoundException {
        this.intNoIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeIntNoIndex() throws EntityNotFoundException {
        intNoIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setLongNoIndex(final java.lang.Long value) throws EntityNotFoundException {
        this.longNoIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeLongNoIndex() throws EntityNotFoundException {
        longNoIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setByteIndex(final java.lang.Byte value) throws EntityNotFoundException {
        this.byteIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeByteIndex() throws EntityNotFoundException {
        byteIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setShortIndex(final java.lang.Short value) throws EntityNotFoundException {
        this.shortIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeShortIndex() throws EntityNotFoundException {
        shortIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setCharIndex(final java.lang.Character value) throws EntityNotFoundException {
        this.charIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeCharIndex() throws EntityNotFoundException {
        charIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setIntIndex(final java.lang.Integer value) throws EntityNotFoundException {
        this.intIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeIntIndex() throws EntityNotFoundException {
        intIndex.remove();
        return this;
    }
    
    public PrimitiveClassDef setLongIndex(final java.lang.Long value) throws EntityNotFoundException {
        this.longIndex.set(value);
        return this;
    }

    public PrimitiveClassDef removeLongIndex() throws EntityNotFoundException {
        longIndex.remove();
        return this;
    }
    

}


