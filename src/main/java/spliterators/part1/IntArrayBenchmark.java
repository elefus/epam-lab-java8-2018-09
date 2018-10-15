package spliterators.part1;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
public class IntArrayBenchmark {

    @Param({"100000000", "10000000", "1000000", "100000"})
    public int length;

    public int[] array;

    @Setup
    public void setup() {
        array = ThreadLocalRandom.current().ints(length).toArray();
    }


    @Benchmark
    public long baselineSequential(Blackhole blackhole) {
        return Arrays.stream(array)
                     .sequential()
                     .asLongStream()
                     .sum();
    }

    @Benchmark
    public long baselineParallel() {
        return Arrays.stream(array)
                     .parallel()
                     .asLongStream()
                     .sum();
    }

    @Benchmark
    public long arrayIntSpliteratorSequential() {
        return StreamSupport.intStream(new IntArraySpliterator(array), false)
                            .asLongStream()
                            .sum();
    }

    @Benchmark
    public long arrayIntSpliteratorParallel() {
        return StreamSupport.intStream(new IntArraySpliterator(array), true)
                            .asLongStream()
                            .sum();
    }
}
