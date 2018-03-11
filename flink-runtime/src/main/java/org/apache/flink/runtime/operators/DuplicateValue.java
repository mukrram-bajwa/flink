package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public final class DuplicateValue<T> implements MapFunction<T, Tuple2<T, T>> {

	@Override
	public Tuple2<T, T> map(T vertex) {
		return new Tuple2<T, T>(vertex, vertex);
	}
}
