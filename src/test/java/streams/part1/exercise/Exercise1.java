package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise1 {

    @Test
    void findPersonsEverWorkedInEpam() {
        List<Employee> employees = getEmployees();

        // реализация, использовать Collectors.toList()

        List<Person> personsEverWorkedInEpam = employees.stream()
                .filter(employee -> employee.getJobHistory().stream()
                        .anyMatch(job -> "EPAM".equals(job.getEmployer())))
                .map(Employee::getPerson)
                .collect(Collectors.toList());

        assertThat(personsEverWorkedInEpam, contains(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson())
        );
    }

    @Test
    void findPersonsBeganCareerInEpam() {
        List<Employee> employees = getEmployees();

        // реализация, использовать Collectors.toList()

        List<Person> startedFromEpam = employees.stream()
                .filter(employee -> "EPAM".equals(employee.getJobHistory().get(0).getEmployer()))
                .map(Employee::getPerson)
                .collect(Collectors.toList());

        assertThat(startedFromEpam, contains(
                employees.get(0).getPerson(),
                employees.get(1).getPerson(),
                employees.get(4).getPerson()
        ));
    }

    @Test
    void findAllCompanies() {
        List<Employee> employees = getEmployees();

        // реализация, использовать Collectors.toSet()

        Set<String> companies = employees.stream()
                .map(employee -> employee.getJobHistory().stream()
                        .map(JobHistoryEntry::getEmployer)
                        .collect(Collectors.toSet()))
                .collect(HashSet::new, AbstractCollection::addAll, AbstractCollection::addAll);

        assertThat(companies, containsInAnyOrder("EPAM", "google", "yandex", "mail.ru", "T-Systems"));
    }

    @Test
    void findMinimalAgeOfEmployees() {
        List<Employee> employees = getEmployees();

        Integer minimalAge = employees.stream()
                .map(Employee::getPerson)
                .mapToInt(Person::getAge)
                .min().getAsInt();

        assertThat(minimalAge, is(21));
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