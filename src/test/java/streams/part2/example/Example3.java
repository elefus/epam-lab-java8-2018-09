package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.jupiter.api.Test;
import streams.part2.example.data.PersonPositionDuration;
import streams.part2.example.data.PersonPositionPair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"ConstantConditions", "AssignmentToLambdaParameter"})
class Example3 {

    /*
     * Вход:
     * [
     *     {
     *         {Иван Мельников 30},
     *         [dev, dev]
     *     },
     *     {
     *         {Александр Дементьев 28},
     *         [tester, dev, dev]
     *     },
     *     {
     *         {Дмитрий Осинов 40},
     *         [QA, QA, dev]
     *     },
     *     {
     *         {Анна Светличная 21},
     *         [tester]
     *     }
     * ]
     *
     * Выход:
     * [
     *     "dev" -> [ {Иван Мельников 30}, {Александр Дементьев 28}, {Дмитрий Осинов 40} ],
     *     "QA" -> [ {Дмитрий Осинов 40} ],
     *     "tester" -> [ {Александр Дементьев 28}, {Анна Светличная 21} ]
     * ]
     */
    @Test
    void splitPersonsByPositionExperienceUsingReduce() {
        List<Employee> employees = Example1.getEmployees();

        HashMap<String, Set<Person>> result = employees.stream()
                                                       .flatMap(Example3::toPersonPositionStream)
                                                       .reduce(new HashMap<>(), Example3::addToMap, Example3::mergeMaps);

        assertThat(result, is(prepareExpected(employees)));
    }

    private static Stream<PersonPositionPair> toPersonPositionStream(Employee employee) {
        Person person = employee.getPerson();
        return employee.getJobHistory()
                       .stream()
                       .map(entry -> new PersonPositionPair(person, entry.getPosition()));
    }

    private static HashMap<String, Set<Person>> addToMap(HashMap<String, Set<Person>> container, PersonPositionPair pair) {
        HashMap<String, Set<Person>> result = new HashMap<>(container);
        result.compute(pair.getPosition(), (position, set) -> {
            set = Optional.ofNullable(set).orElseGet(HashSet::new);
            set.add(pair.getPerson());
            return set;
        });
        return result;
    }

    private static HashMap<String, Set<Person>> mergeMaps(HashMap<String, Set<Person>> left, HashMap<String, Set<Person>> right) {
        HashMap<String, Set<Person>> result = new HashMap<>(left);
        right.forEach((position, people) -> result.merge(position, people, (resultSet, rightSet) -> {
            resultSet.addAll(rightSet);
            return resultSet;
        }));
        return result;
    }

    @Test
    void splitPersonsByPositionExperienceUsingCollect() {
        List<Employee> employees = Example1.getEmployees();

        HashMap<String, Set<Person>> result = employees.stream()
                                                       .flatMap(Example3::toPersonPositionStream)
                                                       .collect(HashMap::new, Example3::addToMapMutable, Example3::mergeMapsMutable);

        assertThat(result, is(prepareExpected(employees)));
    }

    private static void addToMapMutable(HashMap<String, Set<Person>> container, PersonPositionPair pair) {
        container.compute(pair.getPosition(), (position, set) -> {
            set = Optional.ofNullable(set).orElseGet(HashSet::new);
            set.add(pair.getPerson());
            return set;
        });
    }

    private static void mergeMapsMutable(HashMap<String, Set<Person>> left, HashMap<String, Set<Person>> right) {
        right.forEach((position, people) -> left.merge(position, people, (resultSet, rightSet) -> {
            resultSet.addAll(rightSet);
            return resultSet;
        }));
    }

    @Test
    void splitPersonsByPositionExperienceUsingCollectors() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                                                   .flatMap(Example3::toPersonPositionStream)
                                                   .collect(groupingBy(PersonPositionPair::getPosition,
                                                                       mapping(PersonPositionPair::getPerson, toSet())));

        assertThat(result, is(prepareExpected(employees)));
    }

    private static Map<String, Set<Person>> prepareExpected(List<Employee> employees) {
        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("dev", new HashSet<>(Arrays.asList(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(2).getPerson(),
                employees.get(5).getPerson()))
        );
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()
        )));
        expected.put("QA", new HashSet<>(Arrays.asList(
                employees.get(2).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        )));
        return expected;
    }
}
