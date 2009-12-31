// generated code.
//
package com.archtea.gae.sample.server.entity;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.archtea.gae.datastore.*;
import com.archtea.gae.sample.server.entity.version.*;
import com.archtea.gae.datastore.anno.Kind;


@Kind("Page")
public class Page extends EntityChildIdBase<Page, Document> {
    public static final EntityChildIdFactory<Page, Document> FACTORY =
        EntityFactory.getEntityChildIdFactory(Page.class,
            new EntityCreator<Page>() {
        public Page newInstance() {
            return new Page();
        }
    });
    
    public static final EntityChildQueryFactory<Page, Document> QUERY =
        EntityChildQueryFactory.getInstance(Page.class, FACTORY);

    private static final java.util.List<EntityVersionManager<Page>> VERSION_MANAGERS;
    
    static {
        VERSION_MANAGERS = new java.util.ArrayList<EntityVersionManager<Page>>();
        VERSION_MANAGERS.add(Page_ver1.newInstance());

        sortVersionManagers(VERSION_MANAGERS);
    }


    // Version Info field.
    public static final EntityPropertyInfo<Page, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(Page.class, "Version", 1L);


    // Property Info fields.
    public static final EntityCollectionPropertyInfo<Page, java.util.Set, java.lang.String> TAGS =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(Page.class, java.util.Set.class, java.lang.String.class, "Tags");

    public static final EntityPropertyInfo<Page, java.lang.String> TEXT =
        EntityPropertyInfoFactory.getPropertyInfo(Page.class, java.lang.String.class, "Text");

    public static final EntityPropertyInfo<Page, java.lang.Short> NUMBER =
        EntityPropertyInfoFactory.getPropertyInfo(Page.class, java.lang.Short.class, "Number");



    // Version field.
    private final EntityProperty<Page, Long> version;


    // Property fields.
    private final EntityCollectionProperty<Page, java.util.Set, java.lang.String> tags;

    private final EntityProperty<Page, java.lang.String> text;

    private final EntityProperty<Page, java.lang.Short> number;



    // Constructor. This is private to be created by FACTORY.
    private Page() {
        setParentEntityBaseFactory(Document.FACTORY);
        // Initialize a version.
        version = VERSION.newInstance(getEntityPropertyAccess());


        // Initialize fields.
        tags = TAGS.newInstance(getEntityPropertyAccess());

        text = TEXT.newInstance(getEntityPropertyAccess());

        number = NUMBER.newInstance(getEntityPropertyAccess());



        initVersionManagers(VERSION_MANAGERS);

    }

    public Long getVersion() throws EntityNotFoundException {
        return version.get();
    }


    public java.util.Set<java.lang.String> getTags() throws EntityNotFoundException {
        return tags.get();
    }
    public java.lang.String getText() throws EntityNotFoundException {
        return text.get();
    }
    public java.lang.Short getNumber() throws EntityNotFoundException {
        return number.get();
    }


    public Page setTags(final java.util.Set<java.lang.String> value) throws EntityNotFoundException {
        tags.set(value);
        return this;
    }
    public Page setText(final java.lang.String value) throws EntityNotFoundException {
        text.set(value);
        return this;
    }
    public Page setNumber(final java.lang.Short value) throws EntityNotFoundException {
        number.set(value);
        return this;
    }

}
