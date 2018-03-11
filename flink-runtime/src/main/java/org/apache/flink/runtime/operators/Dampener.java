package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public final class Dampener implements MapFunction<Tuple2<Long, Double>, Tuple2<Long, Double>> {

	private static  double dampening;
	private static double randomJump;

	public Dampener() { }

	public Dampener(double dampening, double numVertices) {
		dampening = dampening;
		randomJump = (1 - dampening) / numVertices;
	}

	@Override
	public Tuple2<Long, Double> map(Tuple2<Long, Double> value) {
		value.f1 = (value.f1 * dampening) + randomJump;
		return value;
	}
}
