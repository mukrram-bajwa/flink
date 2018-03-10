
package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public final class FlatMap2 implements FlatMapFunction<String, String> {

	@Override
	public void flatMap(String value, Collector<String> out) throws Exception {
		String[] tokens = value.toLowerCase().split("\\W+");
		if (tokens[2].length() > 1) {
			out.collect(tokens[2]);
		}
	}
}
