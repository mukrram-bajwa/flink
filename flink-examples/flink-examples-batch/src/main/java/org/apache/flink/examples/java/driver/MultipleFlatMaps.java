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

package org.apache.flink.examples.java.driver;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.DiscardingOutputFormat;
import org.apache.flink.runtime.operators.FlatMap1;

@SuppressWarnings("serial")
public class MultipleFlatMaps {

	private static int n = 20;
	private static long[] times = new long[n];

	public static void main(String[] args) throws Exception {

		for (int i=0; i<n; i++)
			run(i, args[0]);
		for (int i=0; i<n; i++)
			System.out.println(times[i]);
	}

	public static void run(int i, String filePath) throws Exception {

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		env.setParallelism(1);
		DataSet<String> xs = env.readTextFile(filePath);

		xs.flatMap(new org.apache.flink.runtime.operators.FlatMap1()).output(new DiscardingOutputFormat<>());
		xs.flatMap(new org.apache.flink.runtime.operators.FlatMap2()).output(new DiscardingOutputFormat<>());
		xs.flatMap(new org.apache.flink.runtime.operators.FlatMap3()).output(new DiscardingOutputFormat<>());

		long start = System.nanoTime();
		env.execute();
		long end = System.nanoTime();
		long elapsed = end - start;
		System.out.println(String.format("Run %s Done in %d nano seconds.", i, elapsed));
		times[i] = elapsed;
	}

}
