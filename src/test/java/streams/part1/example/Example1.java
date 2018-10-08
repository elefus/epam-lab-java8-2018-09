package streams.part1.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see <a href="https://habrahabr.ru/company/luxoft/blog/270383">Шпаргалка по Stream-API</a>
 */
@SuppressWarnings({"RedundantStreamOptionalCall", "ResultOfMethodCallIgnored", "unused"})
class Example1 {

    @Test
    void operationsOnStreamExample() {
//        Stream<Employee> stream1 = getEmployees().stream();


//        ArrayList<Integer> values = new ArrayList<>();
//        values.add(0);
//        values.add(2);
//        values.add(3);
//
//        Stream<Integer> stream = values.stream();
//
//
//
//        values.add(4);
//
//        stream.forEach(System.out::println);
//        System.out.println();


        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 0
        // 0 + 1 = 1
        //         1 + 2 = 3

        // 1 + 2 + 3
        // (1 - 2) - 3
        // 1 - (2 - 3)

//        System.out.println(integers.stream()
//                                   .parallel()
//                                   .reduce(0, Integer::sum));

        System.out.println(integers.stream()
                                   .parallel()
                                   .reduce(new ArrayList<Integer>(),
                                           (accum, value) -> {
                                               ArrayList<Integer> result = new ArrayList<>(accum);
                                               result.add(value);
                                               return result;
                                           }, (integers1, integers2) -> {
                                               ArrayList<Integer> result = new ArrayList<>(integers1);
                                               result.addAll(integers2);
                                               return result;
                                           }));
//                                           },
////                                           (leftAccum, rightAccum) -> {
////                                               System.out.println("combine");
////                                               return leftAccum + rightAccum;
//                                           }=


    }

    // 0 1 2 3 4 5 6 7 8
    // 1 2 2 3 4 1 2 3 4
    // 1 2 3 4

    /**
     *            filter map flatMap peek distinct unordered sorted skip limit sequential parallel
     * IMMUTABLE        |   |       |    |        |         |      |    |     |          |
     * CONCURRENT       |   |       |    |        |         |      |    |     |          |
     * DISTINCT         | - |   -   |    |   +    |         |      |    |     |          |
     * NONNULL          | - |   -   |    |        |         |      |    |     |          |
     * ORDERED          |   |       |    |        |    -    |  +   |    |     |          |
     * SORTED           | - |   -   |    |        |    -    |  +   |    |     |          |
     * SIZED        -   |   |   -   |    |   -    |         |      |    |     |          |
     * SUBSIZED         |   |       |    |        |         |      |    |     |          |
     */
    @Test
    void singleUsageStream() {
        List<Employee> employees = getEmployees();

        Stream<Employee> stream = employees.stream();
        Stream<Employee> filteredStream = stream.filter(employee -> employee.getJobHistory().size() > 2);

        stream.forEach(System.out::println);
        filteredStream.forEach(System.out::println);
    }

    public static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(
                        new Person("Иван", "Мельников", 30),
                        Arrays.asList(
                                new JobHistoryEntry(2, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Александр", "Дементьев", 28),
                        Arrays.asList(
                                new JobHistoryEntry(1, "tester", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Дмитрий", "Осинов", 40),
                        Arrays.asList(
                                new JobHistoryEntry(3, "QA", "yandex"),
                                new JobHistoryEntry(1, "QA", "mail.ru"),
                                new JobHistoryEntry(1, "dev", "mail.ru")
                        )),
                new Employee(
                        new Person("Анна", "Светличная", 21),
                        Collections.singletonList(
                                new JobHistoryEntry(1, "tester", "T-Systems")
                        )),
                new Employee(
                        new Person("Игорь", "Толмачёв", 50),
                        Arrays.asList(
                                new JobHistoryEntry(5, "tester", "EPAM"),
                                new JobHistoryEntry(6, "QA", "EPAM")
                        )),
                new Employee(
                        new Person("Иван", "Александров", 33),
                        Arrays.asList(
                                new JobHistoryEntry(2, "QA", "T-Systems"),
                                new JobHistoryEntry(3, "QA", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM")
                        ))
        );
    }
}
