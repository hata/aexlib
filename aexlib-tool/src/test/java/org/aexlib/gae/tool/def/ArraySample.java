/*
 * $Id$
 * 
 * Copyright 2010 Hiroki Ata
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
package org.aexlib.gae.tool.def;

import org.aexlib.gae.tool.anno.Indexable;
import org.aexlib.gae.tool.anno.KeyType;
import org.aexlib.gae.tool.anno.KeyTypeValue;
import org.aexlib.gae.tool.anno.Kind;

@Kind("ArraySample")
@KeyType(KeyTypeValue.ID)
public class ArraySample {

    boolean[] booleanArray;

    byte[] byteArray;

    short[] shortArray;
    
    char[] charArray;
    
    int[] intArray;
    
    long[] longArray;
    
    float[] floatArray;
    
    double[] doubleArray;
    
    String[] stringArray;

    @Indexable
    byte[] byteIndexableArray;
    
    byte[][] byteArray2;
    
    String[][] stringArray2;
}
