// generated code.
// x
package org.aexlib.gae.tool.sample;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.tool.sample.version.*;
import org.aexlib.gae.datastore.anno.Kind;


@Kind("ParentSample")
public class ParentSample extends EntityNameBase<ParentSample> {
    public static final EntityNameFactory<ParentSample> FACTORY =
        EntityFactory.getEntityNameFactory(ParentSample.class,
            new EntityCreator<ParentSample>() {
        public ParentSample newInstance() {
            return new ParentSample();
        }
    });
    
    public static final EntityQueryFactory<ParentSample> QUERY =
        EntityQueryFactory.getInstance(ParentSample.class, FACTORY);

    private static final java.util.List<EntityVersionManager<ParentSample>> VERSION_MANAGERS;
    
    static {
        VERSION_MANAGERS = new java.util.ArrayList<EntityVersionManager<ParentSample>>();
        VERSION_MANAGERS.add(ParentSample_ver1.newInstance());

        sortVersionManagers(VERSION_MANAGERS);
    }


    // Version Info field.
    public static final EntityPropertyInfo<ParentSample, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(ParentSample.class, "version", 1L);


    // Property Info fields.
    public static final EntityIndexableCollectionPropertyInfo<ParentSample, java.util.List, Sample> CHILDREN =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(ParentSample.class, java.util.List.class, Sample.class, Sample.FACTORY, "Children");

    public static final EntityIndexablePropertyInfo<ParentSample, Sample> CHILD =
        EntityPropertyInfoFactory.getKeyLinkPropertyInfo(ParentSample.class, Sample.class, Sample.FACTORY, "Child");



    // Version field.
    private final EntityProperty<ParentSample, Long> version;


    // Property fields.
    private final EntityCollectionProperty<ParentSample, java.util.List, Sample> children;

    private final EntityProperty<ParentSample, Sample> child;



    // Constructor. This is private to be created by FACTORY.
    private ParentSample() {
        // Initialize a version.
        version = VERSION.newInstance(getEntityPropertyAccess());


        // Initialize fields.
        children = CHILDREN.newInstance(getEntityPropertyAccess());

        child = CHILD.newInstance(getEntityPropertyAccess());



        initVersionManagers(VERSION_MANAGERS);

    }

    public Long getVersion() throws EntityNotFoundException {
        return version.get();
    }


    public java.util.List<Sample> getChildren() throws EntityNotFoundException {
        return children.get();
    }
    public Sample getChild() throws EntityNotFoundException {
        return child.get();
    }


    public ParentSample setChildren(final java.util.List<Sample> value) throws EntityNotFoundException {
        children.set(value);
        return this;
    }

    public ParentSample removeChildren() throws EntityNotFoundException {
        children.remove();
        return this;
    }
    
    public ParentSample setChild(final Sample value) throws EntityNotFoundException {
        child.set(value);
        return this;
    }

    public ParentSample removeChild() throws EntityNotFoundException {
        child.remove();
        return this;
    }
    

}


