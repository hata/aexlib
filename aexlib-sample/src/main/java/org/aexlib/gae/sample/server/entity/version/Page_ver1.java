package org.aexlib.gae.sample.server.entity.version;

import org.aexlib.gae.sample.server.entity.*;

import org.aexlib.gae.datastore.*;

public class Page_ver1 implements EntityVersionManager<Page> {
    public static Page_ver1 newInstance() {
        return new Page_ver1();
    }

    public long getVersion() {
        return 1;
    }

    public void init(EntityBasePropertyAccess<Page> newEntity) {
        // TODO: Auto-Generated. Edit for initializing entity if it is required.
    }

    public void up(EntityBasePropertyAccess<Page> entity) {
        // TODO: Edit for upgrade if it is required.
    }

    public void down(EntityBasePropertyAccess<Page> entity) {
        // TODO: Edit for downgrade if it is required.
    }
}

