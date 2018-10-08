package streams.part1.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

/**
 * @see <a href="https://youtu.be/kxgo7Y4cdA8">Через тернии к лямбдам, часть 1</a>
 * @see <a href="https://youtu.be/JRBWBJ6S4aU">Через тернии к лямбдам, часть 2</a>
 * @see <a href="https://youtu.be/O8oN4KSZEXE">Stream API, часть 1</a>
 * @see <a href="https://youtu.be/i0Jr2l3jrDA">Stream API, часть 2</a>
 */
@SuppressWarnings("ConstantConditions")
class Example2 {

    @Test
    void getIvansLastNames() {
        List<Employee> employees = Example1.getEmployees();

        String[] ivansLastNames = employees.stream()
                                           .map(Employee::getPerson)
                                           .filter(person -> "Иван".equals(person.getFirstName()))
                                           .map(Person::getLastName)
                                           .toArray(String[]::new);

        assertThat(ivansLastNames, arrayContaining("Мельников", "Александров"));
    }

    @Test
    void checkAny25AgedIvanHasDevExperience() {
        List<Employee> employees = Example1.getEmployees();

        boolean any25IvanHasDevExperience =
                employees.stream()
//                         .filter(employee -> {
//                             Person person = employee.getPerson();
//                             return "Иван".equals(person.getFirstName()) && person.getAge() > 25;
//                         })
//                         .filter(employee -> "Иван".equals(employee.getPerson().getFirstName()))
//                         .filter(employee -> employee.getPerson().getAge() > 25)
//                         .flatMap(employee -> employee.getJobHistory().stream())
//                         .anyMatch(entry -> "dev".equals(entry.getPosition()));

                         .filter(employee -> "Иван".equals(employee.getPerson().getFirstName()))
                         .filter(employee -> employee.getPerson().getAge() > 25)
                         .map(Employee::getJobHistory)
                         .flatMap(Collection::stream)
                         .map(JobHistoryEntry::getPosition)
//                         .filter("dev"::equals)
//                         .findAny()
//                         .isPresent();
                         .anyMatch("dev"::equals);


        boolean res = employees.stream()
                               .filter(employee -> employee.getJobHistory()
                                                           .stream()
                                                           .map(JobHistoryEntry::getPosition)
                                                           .anyMatch("dev"::equals))
                               .map(Employee::getPerson)
                               .filter(person -> "Иван".equals(person.getFirstName()))
                               .anyMatch(person -> person.getAge() > 25);


        assertThat(any25IvanHasDevExperience, is(true));
        assertThat(res, is(true));
    }
}
