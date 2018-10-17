package spliterators.part5;

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class Example1 {

    public static void main(String[] args) {
        LongStream fibb = StreamSupport.longStream(new SerialFibonacciSpliterator(), false);

        fibb.forEachOrdered(System.out::println);
    }
}
