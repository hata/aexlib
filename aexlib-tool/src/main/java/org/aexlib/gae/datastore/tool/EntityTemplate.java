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
package com.archtea.gae.datastore.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class EntityTemplate {
    private static final String DEFAULT_CHARSET = "utf-8";
    
    private String template;

    public EntityTemplate(String resourcePath) throws IOException {
        InputStream in = null;
        try {
            in = EntityTemplate.class.getResourceAsStream("resource/" + resourcePath);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            final StringWriter writer = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(writer);
            String line = reader.readLine();
            while (line != null) {
                // Replace return code to be the same as the platform.
                printWriter.println(line);
                line = reader.readLine();
            }
            printWriter.flush();
            template = writer.toString();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
    
    public String apply(Map<String, String> tokenToKeywordMap) {
        String text = template;
        for (Map.Entry<String,String> entry : tokenToKeywordMap.entrySet()) {
            text = text.replaceAll(getRegexText(entry.getKey()), entry.getValue());
        }
        return text;
    }
    
    private String getRegexText(String key) {
        key = key.replace("${", "\\$\\{");
        key = key.replace("}", "\\}");
        key = key.replaceAll("\\.", "\\.");
        return key;
    }
}
