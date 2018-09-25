package lambda.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("unused")
class Example7 {

    @Test
    void name() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 5, 4);

        assertThat(integers, Matchers.containsInAnyOrder(1, 2, 3, 4, 5));
        Matcher<Integer> matcher = Matchers.greaterThan(5);

        matcher.matches(50);
        assertThat(10, matcher);
    }

    static void main(String[] args) {
//        IntFunction<Predicate<Employee>> greaterThan = age -> employee -> employee.getPerson().getAge() > age;

        Predicate<Employee> olderThan30 = employee -> Matchers.greaterThan(30).matches(employee.getPerson().getAge());

        Predicate<Integer> greaterThan40 = Matchers.greaterThan(40)::matches;
        Function<Employee, Integer> employeeToAge = employee -> employee.getPerson().getAge();

        Predicate<Employee> olderThan40 = employeeToAge.andThen(greaterThan40::test)::apply;

        Predicate<Employee> nameEqualsAlex = employee -> "Alex".equals(employee.getPerson().getFirstName());
        selectPeopleOnCondition(Collections.emptyList(), olderThan30.and(nameEqualsAlex));
    }

    private static List<Person> selectPeopleOnCondition(List<Employee> employees, Predicate<Employee> condition) {
        ArrayList<Person> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (condition.test(employee)) {
                result.add(employee.getPerson());
            }
        }
        return result;
    }
}
