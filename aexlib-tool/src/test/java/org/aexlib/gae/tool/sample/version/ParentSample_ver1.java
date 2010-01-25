package org.aexlib.gae.tool.sample.version;

import org.aexlib.gae.tool.sample.*;

import org.aexlib.gae.datastore.*;

public class ParentSample_ver1 implements EntityVersionManager<ParentSample> {
    public static ParentSample_ver1 newInstance() {
        return new ParentSample_ver1();
    }

    public long getVersion() {
        return 1;
    }

    public void init(EntityBasePropertyAccess<ParentSample> newEntity) {
        // TODO: Auto-Generated. Edit for initializing entity if it is required.
    }

    public void up(EntityBasePropertyAccess<ParentSample> entity) {
        // TODO: Edit for upgrade if it is required.
    }

    public void down(EntityBasePropertyAccess<ParentSample> entity) {
        // TODO: Edit for downgrade if it is required.
    }
}

