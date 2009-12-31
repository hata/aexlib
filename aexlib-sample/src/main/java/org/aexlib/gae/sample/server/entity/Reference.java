// generated code.
// x
package org.aexlib.gae.sample.server.entity;

import org.aexlib.gae.sample.server.entity.version.*;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.datastore.anno.Kind;


@Kind("Reference")
public class Reference extends EntityNameBase<Reference> {
    public static final EntityNameFactory<Reference> FACTORY =
        EntityFactory.getEntityNameFactory(Reference.class,
            new EntityCreator<Reference>() {
        public Reference newInstance() {
            return new Reference();
        }
    });
    
    public static final EntityQueryFactory<Reference> QUERY =
        EntityQueryFactory.getInstance(Reference.class, FACTORY);



    // Version Info field.


    // Property Info fields.
    public static final EntityPropertyInfo<Reference, java.lang.String> TITLE =
        EntityPropertyInfoFactory.getPropertyInfo(Reference.class, java.lang.String.class, "Title");

    public static final EntityIndexablePropertyInfo<Reference, Document> REFERER =
        EntityPropertyInfoFactory.getKeyLinkPropertyInfo(Reference.class, Document.class, Document.FACTORY, "Referer");



    // Version field.


    // Property fields.
    private final EntityProperty<Reference, java.lang.String> title;

    private final EntityProperty<Reference, Document> referer;



    // Constructor. This is private to be created by FACTORY.
    private Reference() {
        // Initialize a version.


        // Initialize fields.
        title = TITLE.newInstance(getEntityPropertyAccess());

        referer = REFERER.newInstance(getEntityPropertyAccess());




    }



    public java.lang.String getTitle() throws EntityNotFoundException {
        return title.get();
    }
    public Document getReferer() throws EntityNotFoundException {
        return referer.get();
    }


    public Reference setTitle(final java.lang.String value) throws EntityNotFoundException {
        title.set(value);
        return this;
    }
    public Reference setReferer(final Document value) throws EntityNotFoundException {
        referer.set(value);
        return this;
    }

}


