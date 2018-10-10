package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("ResultOfMethodCallIgnored")
class Example2 {

    @Test
    void toListCollector() {
        Integer[] source = {1, 2, 3, 4, 5};

        List<Integer> result = Arrays.stream(source)
                                     .limit(2)
                                     .collect(toList());

        assertThat(result, contains(1, 2));
    }

    @Test
    void toSetCollector() {
        Set<Integer> result = Stream.of(1, 1, 1, 1)
                                    .collect(toSet());

        assertThat(result, contains(1));
    }

    @Test
    void toHashMapKeyValueCollector() {
        Map<Integer, String> result = Stream.of(1, 2, 3)
                                            .collect(toMap(identity(), Object::toString));

        assertThat(result, hasEntry(1, "1"));
        assertThat(result, hasEntry(2, "2"));
        assertThat(result, hasEntry(3, "3"));
    }

    @Test
    void toHashMapKeyValueCollectorFailsWhenExistsSameKeys() {
        assertThrows(IllegalStateException.class, () -> Stream.of(1, 1, 2, 3)
                                                              .collect(toMap(identity(), Object::toString)));
    }

    @Test
    void toHashMapKeyValueMergeCollector() {
        Map<Integer, String> result = Stream.of(1, 1, 2, 3, 4)
                                            .collect(toMap(identity(), Object::toString, String::concat));

        assertThat(result, hasEntry(1, "11"));
        assertThat(result, hasEntry(2, "2"));
        assertThat(result, hasEntry(3, "3"));
        assertThat(result, hasEntry(4, "4"));
    }

    @Test
    void toTreeMapKeyValueMergeCollector() {
        Map<Integer, String> result = Stream.of(1, 1, 2, 3, 4)
                                            .collect(toMap(identity(), Object::toString, String::concat, TreeMap::new));

        assertThat(result, hasEntry(1, "11"));
        assertThat(result, hasEntry(2, "2"));
        assertThat(result, hasEntry(3, "3"));
        assertThat(result, hasEntry(4, "4"));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    void countingCollector() {
        long countUsingCollector = Stream.of(1, 1, 2, 3, 4).collect(counting());
        long countUsingMethod = Stream.of(1, 1, 2, 3, 4).count();

        assertThat(countUsingCollector, is(5L));
        assertThat(countUsingCollector, is(countUsingMethod));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    // TODO summingDoubleCollector
    // TODO summingLongCollector
    void summingIntCollector() {
        Integer sumUsingCollector = Example1.getEmployees()
                                            .stream()
                                            .map(Employee::getPerson)
                                            .collect(summingInt(Person::getAge));

        int sumUsingIntStream = Example1.getEmployees()
                                        .stream()
                                        .map(Employee::getPerson)
                                        .mapToInt(Person::getAge)
                                        .sum();

        assertThat(sumUsingIntStream, is(202));
        assertThat(sumUsingIntStream, is(sumUsingCollector));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    // TODO averagingDoubleCollector
    // TODO averagingLongCollector
    void averagingIntCollector() {
        Double averageUsingCollector = Example1.getEmployees()
                                               .stream()
                                               .map(Employee::getPerson)
                                               .collect(averagingInt(Person::getAge));

        OptionalDouble averageUsingIntStream = Example1.getEmployees()
                                                       .stream()
                                                       .map(Employee::getPerson)
                                                       .mapToInt(Person::getAge)
                                                       .average();

        assertThat(averageUsingCollector, closeTo(33.6666, 0.0001));
        assertThat(averageUsingCollector, closeTo(averageUsingIntStream.orElseThrow(IllegalStateException::new), 0.001));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    void maxByCollector() {
        Optional<Integer> maxUsingCollector = Example1.getEmployees()
                                                      .stream()
                                                      .map(Employee::getPerson)
                                                      .map(Person::getAge)
                                                      .collect(maxBy(Integer::compare));

        OptionalInt maxUsingIntStream = Example1.getEmployees()
                                                .stream()
                                                .map(Employee::getPerson)
                                                .mapToInt(Person::getAge)
                                                .max();

        assertThat(maxUsingCollector.orElseThrow(IllegalStateException::new), is(50));
        assertThat(maxUsingCollector.get(), is(maxUsingIntStream.orElseThrow(IllegalStateException::new)));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    void minByCollector() {
        Optional<Integer> maxUsingCollector = Example1.getEmployees()
                                                      .stream()
                                                      .map(Employee::getPerson)
                                                      .map(Person::getAge)
                                                      .collect(minBy(Integer::compare));

        OptionalInt maxUsingIntStream = Example1.getEmployees()
                                                .stream()
                                                .map(Employee::getPerson)
                                                .mapToInt(Person::getAge)
                                                .min();

        assertThat(maxUsingCollector.orElseThrow(IllegalStateException::new), is(21));
        assertThat(maxUsingCollector.get(), is(maxUsingIntStream.orElseThrow(IllegalStateException::new)));
    }

    @Test
    // TODO summarizingDoubleCollector
    // TODO summarizingLongCollector
    void summarizingIntCollector() {
        IntSummaryStatistics ageStatisticsUsingCollector = Example1.getEmployees()
                                                                   .stream()
                                                                   .map(Employee::getPerson)
                                                                   .collect(summarizingInt(Person::getAge));

        IntSummaryStatistics ageStatisticsUsingIntStream = Example1.getEmployees()
                                                                   .stream()
                                                                   .map(Employee::getPerson)
                                                                   .mapToInt(Person::getAge)
                                                                   .summaryStatistics();

        assertThat(ageStatisticsUsingCollector.getCount(), is(6L));
        assertThat(ageStatisticsUsingCollector.getMax(), is(50));
        assertThat(ageStatisticsUsingCollector.getMin(), is(21));
        assertThat(ageStatisticsUsingCollector.getSum(), is(202L));
        assertThat(ageStatisticsUsingCollector.getAverage(), closeTo(33.66666, 0.0001));
        assertThat(ageStatisticsUsingCollector.getCount(), is(ageStatisticsUsingIntStream.getCount()));
    }

    @Test
    void joiningCollector() {
        String result = Stream.of("a", "b", "c", "d")
                              .collect(joining());

        assertThat(result, is("abcd"));
    }

    @Test
    void joiningWithDelimiterCollector() {
        String result = Stream.of("a", "b", "c", "d")
                              .collect(joining("~"));

        assertThat(result, is("a~b~c~d"));
    }

    @Test
    void joiningWithDelimiterAndBordersCollector() {
        String result = Stream.of("a", "b", "c", "d")
                              .collect(joining("~", "[", "]"));

        assertThat(result, is("[a~b~c~d]"));
    }

    @Test
    void groupingByCollector() {
        Map<String, List<Person>> nameToPersons = Example1.getEmployees()
                                                          .stream()
                                                          .map(Employee::getPerson)
                                                          .collect(groupingBy(Person::getFirstName));

        assertThat(nameToPersons.get("Иван").size(), is(2));
        assertThat(nameToPersons, not(hasKey("Алексей")));

        Map<Boolean, List<Person>> moreThan40Years = Example1.getEmployees()
                                                             .stream()
                                                             .map(Employee::getPerson)
                                                             .collect(groupingBy(person -> person.getAge() < 60));

        assertThat(moreThan40Years.get(true).size(), is(6));
        assertThat(moreThan40Years.get(false), nullValue());
    }

    @Test
    void partitionByCollector() {
        Map<Boolean, List<Person>> moreThan40Years = Example1.getEmployees()
                                                             .stream()
                                                             .map(Employee::getPerson)
                                                             .collect(partitioningBy(person -> person.getAge() < 60));

        assertThat(moreThan40Years.get(true).size(), is(6));
        assertThat(moreThan40Years.get(false), empty());
    }

    @Test
    void groupingByWithDownstreamCollector() {
        Map<String, Set<Person>> nameToPersons = Example1.getEmployees()
                                                         .stream()
                                                         .map(Employee::getPerson)
                                                         .collect(groupingBy(Person::getFirstName, toSet()));


        Map<String, Long> nameToNumbersPersons = Example1.getEmployees()
                                                         .stream()
                                                         .map(Employee::getPerson)
                                                         .collect(groupingBy(Person::getFirstName, counting()));

        assertThat(nameToPersons.get("Иван").size(), is(2));
        assertThat(nameToPersons, not(hasKey("Алексей")));

    }

    @Test
    void groupingByWithMapFactoryAndDownstreamCollector() {
        TreeMap<String, Set<Person>> nameToPersons = Example1.getEmployees()
                                                             .stream()
                                                             .map(Employee::getPerson)
                                                             .collect(groupingBy(Person::getFirstName, () -> new TreeMap<>(comparingInt(String::length)), toSet()));

        assertThat(nameToPersons.get("Иван").size(), is(2));
        assertThat(nameToPersons, not(hasKey("Алексей")));
    }

    @Test
    void collectingAndThenCollector() {
        Long negatedSize = Example1.getEmployees()
                                   .stream()
                                   .map(Employee::getPerson)
                                   .collect(collectingAndThen(counting(), size -> -size));

        assertThat(negatedSize, is(-6));
    }

    @Test
    @SuppressWarnings("SimplifyStreamApiCallChains")
    void mappingCollector() {
        Set<String> names = Example1.getEmployees()
                                    .stream()
                                    .map(Employee::getPerson)
                                    .collect(mapping(Person::getFirstName, toSet()));

        assertThat(names.size(), is(5));
    }
}
