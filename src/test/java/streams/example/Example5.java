package streams.example;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class Example5 {

    @Test
    void sequenceOfExecution() {
        Stream<String> stringStream = null;

        String collect = stringStream.collect(joining(" "));
    }
}
