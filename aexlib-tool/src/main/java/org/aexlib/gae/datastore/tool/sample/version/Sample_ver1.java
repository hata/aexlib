package com.archtea.gae.datastore.tool.sample.version;

import com.archtea.gae.datastore.*;
import com.archtea.gae.datastore.tool.sample.*;

public class Sample_ver1 implements EntityVersionManager<Sample> {
    public static Sample_ver1 newInstance() {
        return new Sample_ver1();
    }

    public long getVersion() {
        return 1;
    }

    public void init(EntityBasePropertyAccess<Sample> newEntity) {
        // TODO: Auto-Generated. Edit for initializing entity if it is required.
    }

    public void up(EntityBasePropertyAccess<Sample> entity) {
        // TODO: Edit for upgrade if it is required.
    }

    public void down(EntityBasePropertyAccess<Sample> entity) {
        // TODO: Edit for downgrade if it is required.
    }
}

