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

import org.apache.flink.api.common.functions.*;
import org.apache.flink.runtime.operators.*;

public class DriverUtil {
	public static Driver getDriver(ClassLoader classLoader, Function stub) throws Exception {
		DriverCodeGenerator codeGenerator = new DriverCodeGenerator(classLoader);
		Object driver = null;
		String driverClassName =  getDriverClassName(stub, false);
		if(driverClassName == null)
			throw new NullPointerException();
		String driverClassSimpleName =  getDriverClassName(stub, true);;
		String customDriverName = driverClassName + stub.getClass().getSimpleName();
		try {
			driver = classLoader.loadClass(customDriverName).newInstance();
		} catch(ClassNotFoundException cnfe) {
			driver = codeGenerator.generateMapDriver( driverClassName, customDriverName, driverClassSimpleName).newInstance();
		}
		return (Driver)(driver);
	}

	//Method responsible for returning class Name.
	private static String getDriverClassName(Function stub, boolean simple) {
		if (stub instanceof MapFunction)
			return getClassName(MapDriver.class, simple);
		else if (stub instanceof FlatMapFunction)
			return getClassName(FlatMapDriver.class, simple);
		else
			return null;
	}

	private static String getClassName(Class classTypeObj, boolean simple) {
		if(simple)
			return classTypeObj.getSimpleName();
		else
			return classTypeObj.getName();
	}
}
