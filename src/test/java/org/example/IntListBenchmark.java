package org.example;

import org.apache.commons.lang3.ArrayUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;

public class IntListBenchmark {
	public static class Append {
		@Benchmark
		@BenchmarkMode(Mode.Throughput)
		public void append() {
			final IntList.Builder builder = IntList.of(1, 2, 3).toBuilder(0, 10);

			for (int i = 4; i <= 10; i++) {
				builder.append(i);
			}

			final IntList intList = builder.build();
		}

		@Benchmark
		@BenchmarkMode(Mode.Throughput)
		public void append_apache_commons() {
			int[] array = new int[]{1, 2, 3};

			for (int i = 4; i <= 10; i++) {
				array = ArrayUtils.add(array, i);
			}
		}
	}

	public static class Permutations {
		@State(Scope.Benchmark)
		public static class BenchmarkState {
			final IntList intList = IntList.range(0, 5);
		}

		@Benchmark
		@BenchmarkMode(Mode.Throughput)
		public void permutations(BenchmarkState state) {
			final List<IntList> permutations = state.intList.permutations();
		}
	}

	public static void main(String[] args) throws RunnerException {
		final Options options = new OptionsBuilder()
				.include(IntListBenchmark.Append.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(options).run();
	}
}
