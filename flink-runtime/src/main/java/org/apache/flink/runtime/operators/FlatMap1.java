
package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public final class FlatMap1 implements FlatMapFunction<String, String> {

	@Override
	public void flatMap(String value, Collector<String> out) throws Exception {
		String[] tokens = value.toLowerCase().split("\\W+");
		if (tokens[1].length() > 0) {
			out.collect(tokens[1]);
		}
	}
}
