package spliterators.part4;

import java.util.stream.Stream;

public class Example2 {

    public static void main(String[] args) {
        Stream<String> source = Stream.of("a", "b", "c");


        AdvancedStream<String> advancedStream = new AdvancedStreamImpl<>(source);

        advancedStream.forEachOrdered(System.out::println);
    }
}
