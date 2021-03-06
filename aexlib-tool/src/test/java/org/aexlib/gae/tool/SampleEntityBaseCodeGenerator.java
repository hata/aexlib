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
package org.aexlib.gae.tool;

import java.io.File;
import java.io.IOException;

import org.aexlib.gae.tool.def.ArraySample;
import org.aexlib.gae.tool.def.NoVersionSample;
import org.aexlib.gae.tool.def.ParentSample;
import org.aexlib.gae.tool.def.PrimitiveClassDef;
import org.aexlib.gae.tool.def.PrimitiveTypeDef;
import org.aexlib.gae.tool.def.Sample;

public class SampleEntityBaseCodeGenerator {
    private final String outputDir;

    public static void main(String[] args) throws Exception {
        new SampleEntityBaseCodeGenerator(args.length > 0 ? args[0] : ".").generate();
    }

    SampleEntityBaseCodeGenerator(String outputDir) {
        this.outputDir = outputDir;
    }

    void generate() throws Exception {
        CodeGenerator generator = CodeGeneratorFactory.getInstance();
        generator.setOutputDir(getOutputSourceDir());
        generator.setRootPackage(getGeneratedClassPackage());
        generator.addDefinition(getEntityDefinitions());
        generator.generate();
    }

    private Class<?>[] getEntityDefinitions() {
        return new Class<?>[]{
                ParentSample.class,
                Sample.class,
                NoVersionSample.class,
                PrimitiveClassDef.class,
                PrimitiveTypeDef.class,
                ArraySample.class
                };
    }

    private String getGeneratedClassPackage() {
        return this.getClass().getPackage().getName() + ".sample";
    }

    private File getOutputSourceDir() throws IOException {
        return new File(outputDir);
    }

}
