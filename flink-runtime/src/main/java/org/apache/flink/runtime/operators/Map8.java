
package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.MapFunction;

public final class Map8 implements MapFunction<Long, Long> {

	@Override
	public Long map(Long value) {
		return value + 8;
	}
}