package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public final class JoinVertexWithEdgesMatch implements FlatMapFunction<Tuple2<Tuple2<Long, Double>, Tuple2<Long, Long[]>>, Tuple2<Long, Double>> {

	@Override
	public void flatMap(Tuple2<Tuple2<Long, Double>, Tuple2<Long, Long[]>> value, Collector<Tuple2<Long, Double>> out){
		Long[] neighbors = value.f1.f1;
		double rank = value.f0.f1;
		double rankToDistribute = rank / ((double) neighbors.length);

		for (Long neighbor: neighbors) {
			out.collect(new Tuple2<Long, Double>(neighbor, rankToDistribute));
		}
	}
}
