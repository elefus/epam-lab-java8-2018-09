package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        Map<String, Set<Person>> result = null;

        assertThat(result, is(prepareExpected(employees)));
    }

    @Test
    void splitPersonsByPositionExperienceUsingCollect() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = null;

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
