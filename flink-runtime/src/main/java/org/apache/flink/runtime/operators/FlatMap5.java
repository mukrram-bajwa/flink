
package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public final class FlatMap5 implements FlatMapFunction<Long, Long> {

	@Override
	public void flatMap(Long value, Collector<Long> out) throws Exception {
		out.collect(value - 2);
		out.collect(value);
		out.collect(value + 2);
	}
}
