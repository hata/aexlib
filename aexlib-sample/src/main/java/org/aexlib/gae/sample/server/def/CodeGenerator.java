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
package com.archtea.gae.sample.server.def;

import java.io.File;
import java.io.IOException;

import com.archtea.gae.datastore.tool.EntityBaseCodeGenerator;

public class CodeGenerator extends EntityBaseCodeGenerator {
    private String outDir;

    public static void main(String[] args) throws Exception {
        new CodeGenerator(args[0]).generate();
    }
    
    private CodeGenerator(String outDir) {
        this.outDir = outDir;
    }

    @Override
    protected Class<?>[] getEntityDefinitions() {
        return new Class<?>[] {
                Document.class,
                Page.class,
                Reference.class
        };
    }

    @Override
    protected String getGeneratedClassPackage() {
        return "com.archtea.gae.sample.server.entity";
    }

    @Override
    protected File getOutputSourceDir() throws IOException {
        return new File(outDir);
    }

}
