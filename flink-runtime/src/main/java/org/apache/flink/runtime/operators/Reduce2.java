
package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.ReduceFunction;

public final class Reduce2 implements ReduceFunction<Long> {

	@Override
	public Long reduce(Long value1, Long value2) throws Exception {
		return value1 > value2 ? value1 : value2;
	}
}
