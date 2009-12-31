package org.aexlib.gae.sample.server.entity.version;

import org.aexlib.gae.sample.server.entity.*;

import org.aexlib.gae.datastore.*;

public class Document_ver1 implements EntityVersionManager<Document> {
    public static Document_ver1 newInstance() {
        return new Document_ver1();
    }

    public long getVersion() {
        return 1;
    }

    public void init(EntityBasePropertyAccess<Document> newEntity) {
        try {
            newEntity.setProperty(Document.TITLE.getName(), "unknown");
            newEntity.setProperty(Document.AUTHOR.getName(), "unkown author");
        } catch (Exception e) {
            // ignore here.
        }
    }

    public void up(EntityBasePropertyAccess<Document> entity) {
        // TODO: Edit for upgrade if it is required.
    }

    public void down(EntityBasePropertyAccess<Document> entity) {
        // TODO: Edit for downgrade if it is required.
    }
}

