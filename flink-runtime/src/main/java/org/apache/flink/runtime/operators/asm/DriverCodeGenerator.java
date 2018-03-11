/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.operators.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.InputStream;

public class DriverCodeGenerator extends ClassLoader {

	public DriverCodeGenerator(ClassLoader cl) {
		super(cl);
	}

	public Class generateMapDriver( String oldName, String newName, String driverClassSimpleName) throws Exception {
		//Get streams of the existing XXXDriver Class.
		InputStream compiledClass = getResourceAsStream(oldName.replace('.', '/') + ".class");
		ClassReader classReader = new ClassReader(compiledClass);                        // Event generator.
		ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);    // Event consumer.
		DriverClassApopter ca = new DriverClassApopter(newName.replace('.', '/'), driverClassSimpleName, classWriter);    //Adopter responsible for driver generation at run time.
		classReader.accept(ca, 0);
		byte[] bytes = classWriter.toByteArray();      // Loads newly created driver class to the classLoader.
		return defineClass(newName, bytes, 0, bytes.length);
	}
}
