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
package org.aexlib.gae.sample.server.def;

import java.io.File;

import org.aexlib.gae.tool.CodeGenerator;
import org.aexlib.gae.tool.CodeGeneratorFactory;

public class SampleCodeGenerator {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Please set a root directory to output generated sources.");
            return;
        }
        
        CodeGenerator generator = CodeGeneratorFactory.getInstance();
        generator.setOutputDir(new File(args[0]));
        generator.setRootPackage("org.aexlib.gae.sample.server.entity");
        generator.addDefinition(Document.class, Page.class, Reference.class);

        generator.generate();
    }
}
