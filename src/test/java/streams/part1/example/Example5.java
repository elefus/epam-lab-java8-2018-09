package streams.part1.example;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.stream.Stream;

class Example5 {

    @Test
    void sequenceOfExecution() {
        Stream<String> stringStream = Stream.of("3232", "1", "ba", "asd", "12312", "asd", "12", "23", "43434q", "12321", "3232");

        Stream<String> result = stringStream.filter(Pattern.compile("^\\d+$").asPredicate())
                                    .peek(System.out::println)
                                    .mapToInt(Integer::parseInt)
                                    .filter(value -> value > 15)
                                    .mapToObj(String::valueOf)
                                    .filter(s -> s.length() > 3)
//                                    .sorted(Comparator.reverseOrder())
                                    .distinct()
                                    .peek(System.out::println);
//                                    .collect(joining(" "));


        System.out.println(result);
    }
}
