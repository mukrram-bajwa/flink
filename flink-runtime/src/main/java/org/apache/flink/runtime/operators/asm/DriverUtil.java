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

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.Function;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.runtime.operators.Driver;
import org.apache.flink.runtime.operators.FlatMapDriver;
import org.apache.flink.runtime.operators.GroupReduceDriver;
import org.apache.flink.runtime.operators.MapDriver;

public class DriverUtil {
	public static Driver getDriver(ClassLoader classLoader, Function stub) throws Exception {
			DriverCodeGenerator codeGenerator = new DriverCodeGenerator(classLoader);
			Object driver = null;
			String driverClassName =  getDriverClassName(stub);
			if(driverClassName == null)
				throw new NullPointerException();
			String driverClassSimpleName =  getDriverClassSimpleName(stub);
			String customDriverName = driverClassName + stub.getClass().getSimpleName();
			try {
				driver = classLoader.loadClass(customDriverName).newInstance();
			} catch(ClassNotFoundException cnfe) {
				driver = codeGenerator.generateMapDriver( driverClassName, customDriverName, driverClassSimpleName).newInstance();
			}
			return (Driver)(driver);
	}

	private static String getDriverClassName(Function stub) {
		if (stub instanceof MapFunction)
			return MapDriver.class.getName();
		else if (stub instanceof FlatMapFunction)
			return FlatMapDriver.class.getName();
		else
			return null;
	}

	private static String getDriverClassSimpleName(Function stub) {
		if (stub instanceof MapFunction)
			return MapDriver.class.getSimpleName();
		else if (stub instanceof FlatMapFunction)
			return FlatMapDriver.class.getSimpleName();
		else
			return null;
	}
}
