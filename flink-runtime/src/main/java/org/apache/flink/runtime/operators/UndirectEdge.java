package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public final class UndirectEdge implements FlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> {
	Tuple2<Long, Long> invertedEdge = new Tuple2<Long, Long>();

	@Override
	public void flatMap(Tuple2<Long, Long> edge, Collector<Tuple2<Long, Long>> out) {
		invertedEdge.f0 = edge.f1;
		invertedEdge.f1 = edge.f0;
		out.collect(edge);
		out.collect(invertedEdge);
	}
}
