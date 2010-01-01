// generated code.
//
package org.aexlib.gae.tool.sample;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.tool.sample.version.*;
import org.aexlib.gae.datastore.anno.Kind;


@Kind("Sample")
public class Sample extends EntityChildIdBase<Sample, ParentSample> {
    public static final EntityChildIdFactory<Sample, ParentSample> FACTORY =
        EntityFactory.getEntityChildIdFactory(Sample.class,
            new EntityCreator<Sample>() {
        public Sample newInstance() {
            return new Sample();
        }
    });
    
    public static final EntityChildQueryFactory<Sample, ParentSample> QUERY =
        EntityChildQueryFactory.getInstance(Sample.class, FACTORY);

    private static final java.util.List<EntityVersionManager<Sample>> VERSION_MANAGERS;
    
    static {
        VERSION_MANAGERS = new java.util.ArrayList<EntityVersionManager<Sample>>();
        VERSION_MANAGERS.add(Sample_ver1.newInstance());

        sortVersionManagers(VERSION_MANAGERS);
    }


    // Version Info field.
    public static final EntityPropertyInfo<Sample, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(Sample.class, "Version", 1L);


    // Property Info fields.
    public static final EntityPropertyInfo<Sample, java.lang.String> TITLE =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(Sample.class, java.lang.String.class, "Title");

    public static final EntityPropertyInfo<Sample, java.lang.String> TEXT =
        EntityPropertyInfoFactory.getPropertyInfo(Sample.class, java.lang.String.class, "Text");

    public static final EntityPropertyInfo<Sample, java.util.Date> INSERT_DATE =
        EntityPropertyInfoFactory.getPropertyInfo(Sample.class, java.util.Date.class, "InsertDate");

    public static final EntityCollectionPropertyInfo<Sample, java.util.List, java.lang.String> REFERENCES =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(Sample.class, java.util.List.class, java.lang.String.class, "References");



    // Version field.
    private final EntityProperty<Sample, Long> version;


    // Property fields.
    private final EntityProperty<Sample, java.lang.String> title;

    private final EntityProperty<Sample, java.lang.String> text;

    private final EntityProperty<Sample, java.util.Date> insertDate;

    private final EntityCollectionProperty<Sample, java.util.List, java.lang.String> references;



    // Constructor. This is private to be created by FACTORY.
    private Sample() {
        setParentEntityBaseFactory(ParentSample.FACTORY);
        // Initialize a version.
        version = VERSION.newInstance(getEntityPropertyAccess());


        // Initialize fields.
        title = TITLE.newInstance(getEntityPropertyAccess());

        text = TEXT.newInstance(getEntityPropertyAccess());

        insertDate = INSERT_DATE.newInstance(getEntityPropertyAccess());

        references = REFERENCES.newInstance(getEntityPropertyAccess());



        initVersionManagers(VERSION_MANAGERS);

    }

    public Long getVersion() throws EntityNotFoundException {
        return version.get();
    }


    public java.lang.String getTitle() throws EntityNotFoundException {
        return title.get();
    }
    public java.lang.String getText() throws EntityNotFoundException {
        return text.get();
    }
    public java.util.Date getInsertDate() throws EntityNotFoundException {
        return insertDate.get();
    }
    public java.util.List<java.lang.String> getReferences() throws EntityNotFoundException {
        return references.get();
    }


    public Sample setTitle(final java.lang.String value) throws EntityNotFoundException {
        title.set(value);
        return this;
    }

    public Sample removeTitle() throws EntityNotFoundException {
        title.remove();
        return this;
    }
    
    public Sample setText(final java.lang.String value) throws EntityNotFoundException {
        text.set(value);
        return this;
    }

    public Sample removeText() throws EntityNotFoundException {
        text.remove();
        return this;
    }
    
    public Sample setInsertDate(final java.util.Date value) throws EntityNotFoundException {
        insertDate.set(value);
        return this;
    }

    public Sample removeInsertDate() throws EntityNotFoundException {
        insertDate.remove();
        return this;
    }
    
    public Sample setReferences(final java.util.List<java.lang.String> value) throws EntityNotFoundException {
        references.set(value);
        return this;
    }

    public Sample removeReferences() throws EntityNotFoundException {
        references.remove();
        return this;
    }
    

}
