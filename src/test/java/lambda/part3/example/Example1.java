package lambda.part3.example;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class Example1 {

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

    @Test
    public void findIvanWithDeveloperExperienceAndWorkedInEpamMoreThenYearAtOnePosition() {
        List<Employee> employees = getEmployees();

        List<Employee> result = null;

        assertThat(result, contains(employees.get(0), employees.get(5)));
    }
}
