/*
 * $Id$
 * 
 * Copyright 2009 Hiroki Ata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aexlib.gae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

public class LocalDataStoreTestCase extends GAEBaseTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
        proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
    }

    @Override
    protected void tearDown() throws Exception {
        ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
        LocalDatastoreService datastoreService = (LocalDatastoreService) proxy.getService("datastore_v3");
        datastoreService.clearProfiles();
        super.tearDown();
    }

    public void testDummy() {
        
    }
    
    
    public String getResource(String name) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(this.getClass().getResourceAsStream(name), "utf-8"));
            StringBuffer buff = new StringBuffer();
            String line = in.readLine();
            while (line != null) {
                buff.append(line);
                line = in.readLine();
            }
            return buff.toString();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
