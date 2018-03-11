package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public final class EpsilonFilter implements FilterFunction<Tuple2<Tuple2<Long, Double>, Tuple2<Long, Double>>> {

	private static final double EPSILON = 0.0001;

	@Override
	public boolean filter(Tuple2<Tuple2<Long, Double>, Tuple2<Long, Double>> value) {
		return Math.abs(value.f0.f1 - value.f1.f1) > EPSILON;
	}
}
