package org.apache.flink.runtime.operators;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public final class RankAssigner implements MapFunction<Long, Tuple2<Long, Double>> {
	static Tuple2<Long, Double> outPageWithRank;

	public RankAssigner() {
	}

	public RankAssigner(double rank) {
		outPageWithRank = new Tuple2<Long, Double>(-1L, rank);
	}

	@Override
	public Tuple2<Long, Double> map(Long page) {
		outPageWithRank.f0 = page;
		return outPageWithRank;
	}
}
