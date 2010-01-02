// generated code.
// x
package org.aexlib.gae.sample.server.entity;

import com.google.appengine.api.datastore.EntityNotFoundException;
import org.aexlib.gae.datastore.*;
import org.aexlib.gae.sample.server.entity.version.*;
import org.aexlib.gae.datastore.anno.Kind;


@Kind("Document")
public class Document extends EntityNameBase<Document> {
    public static final EntityNameFactory<Document> FACTORY =
        EntityFactory.getEntityNameFactory(Document.class,
            new EntityCreator<Document>() {
        public Document newInstance() {
            return new Document();
        }
    });
    
    public static final EntityQueryFactory<Document> QUERY =
        EntityQueryFactory.getInstance(Document.class, FACTORY);

    private static final java.util.List<EntityVersionManager<Document>> VERSION_MANAGERS;
    
    static {
        VERSION_MANAGERS = new java.util.ArrayList<EntityVersionManager<Document>>();
        VERSION_MANAGERS.add(Document_ver1.newInstance());

        sortVersionManagers(VERSION_MANAGERS);
    }


    // Version Info field.
    public static final EntityIndexablePropertyInfo<Document, Long> VERSION =
        EntityPropertyInfoFactory.getVersionPropertyInfo(Document.class, "Version", 1L);


    // Property Info fields.
    public static final EntityIndexablePropertyInfo<Document, java.lang.String> TITLE =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(Document.class, java.lang.String.class, "Title");

    public static final EntityIndexablePropertyInfo<Document, java.lang.String> AUTHOR =
        EntityPropertyInfoFactory.getIndexablePropertyInfo(Document.class, java.lang.String.class, "Author");

    public static final EntityCollectionPropertyInfo<Document, java.util.Set, Reference> REFERENCES =
        EntityPropertyInfoFactory.getKeyLinkCollectionPropertyInfo(Document.class, java.util.Set.class, Reference.class, Reference.FACTORY, "References");

    public static final EntityCollectionPropertyInfo<Document, java.util.List, org.aexlib.gae.sample.server.AttachedObject> ATTACHMENTS =
        EntityPropertyInfoFactory.getCollectionPropertyInfo(Document.class, java.util.List.class, org.aexlib.gae.sample.server.AttachedObject.class, "Attachments");



    // Version field.
    private final EntityProperty<Document, Long> version;


    // Property fields.
    private final EntityProperty<Document, java.lang.String> title;

    private final EntityProperty<Document, java.lang.String> author;

    private final EntityCollectionProperty<Document, java.util.Set, Reference> references;

    private final EntityCollectionProperty<Document, java.util.List, org.aexlib.gae.sample.server.AttachedObject> attachments;



    // Constructor. This is private to be created by FACTORY.
    private Document() {
        // Initialize a version.
        version = VERSION.newInstance(getEntityPropertyAccess());


        // Initialize fields.
        title = TITLE.newInstance(getEntityPropertyAccess());

        author = AUTHOR.newInstance(getEntityPropertyAccess());

        references = REFERENCES.newInstance(getEntityPropertyAccess());

        attachments = ATTACHMENTS.newInstance(getEntityPropertyAccess());



        initVersionManagers(VERSION_MANAGERS);

    }

    public Long getVersion() throws EntityNotFoundException {
        return version.get();
    }


    public java.lang.String getTitle() throws EntityNotFoundException {
        return title.get();
    }
    public java.lang.String getAuthor() throws EntityNotFoundException {
        return author.get();
    }
    public java.util.Set<Reference> getReferences() throws EntityNotFoundException {
        return references.get();
    }
    public java.util.List<org.aexlib.gae.sample.server.AttachedObject> getAttachments() throws EntityNotFoundException {
        return attachments.get();
    }


    public Document setTitle(final java.lang.String value) throws EntityNotFoundException {
        title.set(value);
        return this;
    }

    public Document removeTitle() throws EntityNotFoundException {
        title.remove();
        return this;
    }
    
    public Document setAuthor(final java.lang.String value) throws EntityNotFoundException {
        author.set(value);
        return this;
    }

    public Document removeAuthor() throws EntityNotFoundException {
        author.remove();
        return this;
    }
    
    public Document setReferences(final java.util.Set<Reference> value) throws EntityNotFoundException {
        references.set(value);
        return this;
    }

    public Document removeReferences() throws EntityNotFoundException {
        references.remove();
        return this;
    }
    
    public Document setAttachments(final java.util.List<org.aexlib.gae.sample.server.AttachedObject> value) throws EntityNotFoundException {
        attachments.set(value);
        return this;
    }

    public Document removeAttachments() throws EntityNotFoundException {
        attachments.remove();
        return this;
    }
    

}


