//
// generated code.
//
package org.aexlib.gae.tool.sample;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.tool.sample.version.*;
import org.aexlib.gae.datastore.anno.Kind;


@Kind("NoVersion")
public class NoVersionSample extends EntityNameBase<NoVersionSample> {
    public static final EntityNameFactory<NoVersionSample> FACTORY =
        EntityFactory.getEntityNameFactory(NoVersionSample.class,
            new EntityCreator<NoVersionSample>() {
        public NoVersionSample newInstance() {
            return new NoVersionSample();
        }
    });
    
    public static final EntityQueryFactory<NoVersionSample> QUERY =
        EntityQueryFactory.getInstance(NoVersionSample.class, FACTORY);



    // Version Info field.


    // Property Info fields.
    public static final EntityIndexablePropertyInfo<NoVersionSample, java.lang.String> NAME =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(NoVersionSample.class, java.lang.String.class, "Name");



    // Version field.


    // Property fields.
    private final EntityProperty<NoVersionSample, java.lang.String> name;



    // Constructor. This is private to be created by FACTORY.
    private NoVersionSample() {

        // Initialize a version.


        // Initialize fields.
        name = NAME.newInstance(getEntityPropertyAccess());




    }



    public java.lang.String getName() throws EntityNotFoundException {
        return name.get();
    }


    public NoVersionSample setName(final java.lang.String value) throws EntityNotFoundException {
        this.name.set(value);
        return this;
    }

    public NoVersionSample removeName() throws EntityNotFoundException {
        name.remove();
        return this;
    }
    

}


