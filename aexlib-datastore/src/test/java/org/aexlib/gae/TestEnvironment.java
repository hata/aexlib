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
package com.archtea.gae;

import java.util.HashMap;
import java.util.Map;

import com.google.apphosting.api.ApiProxy;

public class TestEnvironment implements ApiProxy.Environment {
    private String defaultNamespace = "ns";

    public String getAppId() {
        return "Unit Tests";
    }

    public String getVersionId() {
        return "1.0";
    }

    public void setDefaultNamespace(String s) {
        defaultNamespace = s;
    }

    public String getRequestNamespace() {
        return "";
    }

    public String getDefaultNamespace() { 
        return defaultNamespace;
    }

    public String getAuthDomain() {
      return "example.com";
    }

    public boolean isLoggedIn() {
      return true;
    }

    public String getEmail() {
      return "test@example.com";
    }

    public boolean isAdmin() {
      return false;
    }

    public Map<String, Object> getAttributes() {
        return new HashMap<String, Object>();
    }
}


