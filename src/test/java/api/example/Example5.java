package api.example;

import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@SuppressWarnings({"Java8CollectionRemoveIf", "ConstantConditions", "unused"})
class Example5 {

    @Test
    void putValueIfAbsentUsingJava7() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, Integer> personSalaries = new HashMap<>();

        if (!personSalaries.containsKey(alex)) {
            personSalaries.put(alex, 65_000);
        }

        assertThat(personSalaries, hasEntry(alex, 65_000));
    }

    @Test
    void putValueIfAbsentUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, Integer> personSalaries = new HashMap<>();

        // TODO putIfAbsent

        assertThat(personSalaries, hasEntry(alex, 65_000));
    }

    private static Integer hugeOperation(Person person) {
        try {
            TimeUnit.SECONDS.sleep(1);
            return person.getAge() * 3000;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void putHugeValueOnlyIfAbsentUsingJava7() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, Integer> personSalaries = new HashMap<>();

        if (!personSalaries.containsKey(alex)) {
            personSalaries.put(alex, hugeOperation(alex));
        }

        assertThat(personSalaries, hasEntry(alex, 60_000));
    }

    @Test
    void putHugeValueOnlyIfAbsentUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, Integer> personSalaries = new HashMap<>();

        // TODO computeIfAbsent

        assertThat(personSalaries, hasEntry(alex, 60_000));
    }

    // TODO functional descriptor
    private static int raiseSalary(Person person, int salary) {
        return salary + 10_000 + person.getAge() * 100;
    }

    @Test
    void reassignIfExistingKeyInMapUsingJava7() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);

        if (personSalaries.containsKey(alex)) {
            personSalaries.put(alex, raiseSalary(alex, personSalaries.get(alex)));
        }

        Integer ivanSalary = personSalaries.get(ivan);
        if (ivanSalary != null) {
            personSalaries.put(ivan, raiseSalary(alex, ivanSalary));
        }

        assertThat(personSalaries, hasEntry(alex, 77_000));
        assertThat(personSalaries, not(hasKey(ivan)));
    }

    @Test
    void reassignValueForKeyInMapUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);

        // TODO replace

        assertThat(personSalaries, hasEntry(alex, 77_000));
        assertThat(personSalaries, not(hasKey(ivan)));
    }

    @Test
    @SuppressWarnings("Convert2MethodRef")
    void reassignValueIfExistingKeyInMapUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);

        // TODO computeIfPresent

        assertThat(personSalaries, hasEntry(alex, 77_000));
        assertThat(personSalaries, not(hasKey(ivan)));
    }

    @Test
    void reassignOrDeleteValuesInMapUsingJava8() {
        int baseSalary = 65_000;
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person nick = new Person("Николай", "Очагов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, baseSalary);

        BiFunction<Person, Integer, Integer> getBasicOrRiseSalary  = (person, salary) -> salary == null ? baseSalary : raiseSalary(person, salary);
        // TODO implement using Optional

        // TODO compute
        assertThat(personSalaries, hasEntry(alex, 77_000));
        assertThat(personSalaries, hasEntry(ivan, 65_000));
        assertThat(personSalaries, hasEntry(nick, 65_000));

        // TODO remove pair using compute
        assertThat(personSalaries, not(hasKey(nick)));
    }

    @Test
    void mergeValuesUsingJava7() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, List<JobHistoryEntry>> personsExperience = new HashMap<>();
        personsExperience.put(alex, new ArrayList<>(Collections.singleton(new JobHistoryEntry(1, "tester", "EPAM"))));

        List<JobHistoryEntry> newEpamExperience = new ArrayList<>(Arrays.asList(
                new JobHistoryEntry(1, "QA", "EPAM"),
                new JobHistoryEntry(1, "dev", "EPAM")
        ));
        if (personsExperience.containsKey(alex)) {
            personsExperience.get(alex).addAll(newEpamExperience);
        } else {
            personsExperience.put(alex, newEpamExperience);
        }
        assertThat(personsExperience.get(alex).size(), is(3));

        List<JobHistoryEntry> newGoogleExperience = new ArrayList<>(Arrays.asList(
                new JobHistoryEntry(1, "QA", "google"),
                new JobHistoryEntry(1, "dev", "google")
        ));
        List<JobHistoryEntry> alexExperience;
        if ((alexExperience = personsExperience.get(alex)) != null) {
            alexExperience.addAll(newGoogleExperience);
        } else {
            personsExperience.put(alex, newGoogleExperience);
        }
        assertThat(personsExperience.get(alex).size(), is(5));
    }

    @Test
    void mergeValuesUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, List<JobHistoryEntry>> personsExperience = new HashMap<>();
        personsExperience.put(alex, new ArrayList<>(Collections.singleton(new JobHistoryEntry(1, "tester", "EPAM"))));

        List<JobHistoryEntry> newEpamExperience = new ArrayList<>(Arrays.asList(
                new JobHistoryEntry(1, "QA", "EPAM"),
                new JobHistoryEntry(1, "dev", "EPAM")
        ));
        personsExperience.merge(alex, newEpamExperience, Example5::mergeListsToLeftUsingAddAll);
        assertThat(personsExperience.get(alex).size(), is(3));

        List<JobHistoryEntry> newGoogleExperience = new ArrayList<>(Arrays.asList(
                new JobHistoryEntry(1, "QA", "google"),
                new JobHistoryEntry(1, "dev", "google")
        ));
        personsExperience.merge(alex, newGoogleExperience, Example5::mergeListsToNew);
        assertThat(personsExperience.get(alex).size(), is(5));
    }

    private static <T> List<T> mergeListsToLeftUsingAddAll(List<T> left, List<T> right) {
        left.addAll(right);
        return left;
    }

    private static <T> List<T> mergeListsToNew(List<T> left, List<T> right) {
        ArrayList<T> result = new ArrayList<>(left);
        result.addAll(right);
        return result;
    }

    @Test
    void updateAllValuesUsingJava7() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);
        personSalaries.put(ivan, 65_000);

        for (Person person : personSalaries.keySet()) {
            personSalaries.put(person, raiseSalary(person, personSalaries.get(person)));
        }

        Map<Person, Integer> expected = new HashMap<>();
        expected.put(alex, 77_000);
        expected.put(ivan, 77_400);
        assertThat(expected, is(personSalaries));
    }

    @Test
    void updateAllValuesUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);
        personSalaries.put(ivan, 65_000);

        // TODO replaceAll

        assertThat(personSalaries, hasEntry(alex, 77_000));
        assertThat(personSalaries, hasEntry(ivan, 77_000));
    }

    @Test
    void forEachEntryUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);
        personSalaries.put(ivan, 65_000);

        AtomicInteger sum = new AtomicInteger();
        personSalaries.forEach((person, salary) -> sum.addAndGet(raiseSalary(person, salary)));

        assertThat(sum.get(), is(154_400));
    }

    @Test
    void getOrDefaultUsingJava7() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);

        Integer salaryAlex = personSalaries.get(alex);
        if (salaryAlex == null) {
            salaryAlex = 30_000;
        }

        Integer salaryIvan = personSalaries.get(new Person("Иван", "Стрельцов", 24));
        if (salaryIvan == null) {
            salaryIvan = 30_000;
        }

        assertThat(salaryAlex, is(65_000));
        assertThat(salaryIvan, is(30_000));
    }

    @Test
    void getOrDefaultUsingJava8() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Map<Person, Integer> personSalaries = new HashMap<>();
        personSalaries.put(alex, 65_000);

        Person ivan = new Person("Иван", "Стрельцов", 24);

        assertThat(personSalaries.getOrDefault(alex, 30_000), is(65_000));
        assertThat(personSalaries.getOrDefault(ivan, 30_000), is(30_000));
    }
}
