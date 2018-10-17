package spliterators.part4;

import java.util.stream.Stream;

public class Example2 {

    public static void main(String[] args) {
        Stream<String> sourceStream = Stream.of("a", "b", "c", "123", "1");

        AdvancedStream<String> advancedStream = AdvancedStream.of(sourceStream);

        advancedStream.dropWhile(s -> s.length() < 2)
                      .forEachOrdered(System.out::println);

    }
}
