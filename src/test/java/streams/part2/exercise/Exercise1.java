package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise1 {

    @Test
    void calcTotalYearsSpentInEpam() {
        List<Employee> employees = getEmployees();

        Long years = employees.stream()
                .parallel()
                .flatMap(employee -> employee.getJobHistory().stream())
                .filter(entry -> "EPAM".equals(entry.getEmployer()))
                .mapToLong(JobHistoryEntry::getDuration)
                .sum();

        assertThat(years, is(19L));
    }

    @Test
    void findPersonsWithQaExperience() {
        List<Employee> employees = getEmployees();

        Set<Person> workedAsQa = employees.stream()
                .parallel()
                .filter(employee -> employee.getJobHistory().stream()
                        .anyMatch(entry -> "QA".equals(entry.getPosition())))
                .map(Employee::getPerson)
                .collect(Collectors.toSet());

        assertThat(workedAsQa, containsInAnyOrder(
                employees.get(2).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        ));
    }

    @Test
    void composeFullNamesOfEmployeesUsingLineSeparatorAsDelimiter() {
        List<Employee> employees = getEmployees();

        String result = employees.stream()
                .parallel()
                .map(Employee::getPerson)
                .map(Person::getFullName)
                .collect(Collectors.joining("\n"));

        assertThat(result, is(
                "Иван Мельников\n"
                        + "Александр Дементьев\n"
                        + "Дмитрий Осинов\n"
                        + "Анна Светличная\n"
                        + "Игорь Толмачёв\n"
                        + "Иван Александров"));
    }

    @Test
    @SuppressWarnings("Duplicates")
    void groupPersonsByFirstPositionUsingToMap() {
        List<Employee> employees = getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                .parallel()
                .collect(Collectors.toMap(Employee::getPerson,
                        employee -> employee.getJobHistory().get(0).getPosition()))
                .entrySet().stream()
                .collect(
                        HashMap::new,
                        (map, entry) -> {
                            map.compute(entry.getValue(), (s, set) -> {
                                set = set == null ? new HashSet<>() : set;
                                set.add(entry.getKey());
                                return set;
                            });
                        },
                        (map1, map2) -> {
                            Function<String, Set<Person>> mergeSets = key -> {
                                Set<Person> set = map1.get(key);
                                set.addAll(map2.get(key));
                                return set;
                            };
                            map1.forEach((key, value) ->
                                    map1.merge(key, mergeSets.apply(key), (set1, set2) -> set1 = set2));
                        }
                );

        assertThat(result, hasEntry(is("dev"), contains(employees.get(0).getPerson())));
        assertThat(result, hasEntry(is("QA"), containsInAnyOrder(employees.get(2).getPerson(), employees.get(5).getPerson())));
        assertThat(result, hasEntry(is("tester"), containsInAnyOrder(employees.get(1).getPerson(), employees.get(3).getPerson(), employees.get(4).getPerson())));
    }

    @Value
    class Pair {
        Person person;
        String position;
    }

    @Test
    @SuppressWarnings("Duplicates")
    void groupPersonsByFirstPositionUsingGroupingByCollector() {
        List<Employee> employees = getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                .parallel()
                .map(employee -> new Pair(employee.getPerson(), employee.getJobHistory().get(0).getPosition()))
                .collect(Collectors.groupingBy(Pair::getPosition, new SetCollector()));

        assertThat(result, hasEntry(is("dev"), contains(employees.get(0).getPerson())));
        assertThat(result, hasEntry(is("QA"), containsInAnyOrder(employees.get(2).getPerson(), employees.get(5).getPerson())));
        assertThat(result, hasEntry(is("tester"), containsInAnyOrder(employees.get(1).getPerson(), employees.get(3).getPerson(), employees.get(4).getPerson())));
    }

     class SetCollector implements Collector<Pair, Set<Person>, Set<Person>>  {
        @Override
        public Supplier<Set<Person>> supplier() {
            return HashSet::new;
        }

        @Override
        public BiConsumer<Set<Person>, Pair> accumulator() {
            return (set, pair) -> {
                set.add(pair.getPerson());
            };
        }

        @Override
        public BinaryOperator<Set<Person>> combiner() {
            return (set1, set2) -> {
                set1.addAll(set2);
                return set1;
            };
        }

        @Override
        public Function<Set<Person>, Set<Person>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Collector.Characteristics> characteristics() {
            return new HashSet<>(Arrays.asList(IDENTITY_FINISH, UNORDERED));
        }
    }


    private static List<Employee> getEmployees() {
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
