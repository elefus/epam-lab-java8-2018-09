package api.exercise;

import lambda.data.Person;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static api.exercise.Exercise1.Status.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@SuppressWarnings({"ConstantConditions", "unused", "MismatchedQueryAndUpdateOfCollection"})
class Exercise1 {

    enum Status {
        UNKNOWN,
        PENDING,
        COMMENTED,
        ACCEPTED,
        DECLINED
    }

    @Test
    void acceptGreaterThan21OthersDecline() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, PENDING);
        candidates.put(ivan, PENDING);
        candidates.put(helen, PENDING);

        candidates.replaceAll((key, value) -> key.getAge() > 21 ? ACCEPTED : DECLINED);

        assertThat(candidates, Matchers.hasEntry(ivan, ACCEPTED));
        assertThat(candidates, Matchers.hasEntry(helen, ACCEPTED));
        assertThat(candidates, Matchers.hasEntry(alex, DECLINED));
    }

    @Test
    void acceptGreaterThan21OthersRemove() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, PENDING);
        candidates.put(ivan, PENDING);
        candidates.put(helen, PENDING);

        candidates.put(new Person("a", "a", 19), PENDING);
        candidates.put(new Person("b", "c", 16), PENDING);
        candidates.put(new Person("b", "c", 5), PENDING);

        candidates.entrySet().removeIf(entry -> entry.getKey().getAge() < 21);
        candidates.replaceAll((key, value) -> ACCEPTED);

        assertThat(candidates, Matchers.hasEntry(ivan, ACCEPTED));
        assertThat(candidates, Matchers.hasEntry(helen, ACCEPTED));
        assertThat(candidates, not(hasKey(alex)));
    }

    @Test
    void getStatus() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Map<Person, Status> candidates = new HashMap<>();
        candidates.put(alex, PENDING);
        candidates.put(ivan, PENDING);

        Status alexStatus = candidates.getOrDefault(alex, UNKNOWN);
        Status ivanStatus = candidates.getOrDefault(ivan, UNKNOWN);;
        Status helenStatus = candidates.getOrDefault(helen, UNKNOWN);;

        assertThat(alexStatus, is(PENDING));
        assertThat(ivanStatus, is(PENDING));
        assertThat(helenStatus, is(UNKNOWN));
    }

    @Test
    void putToNewValuesIfNotExists() {
        Person alex = new Person("Алексей", "Мельников", 20);
        Person ivan = new Person("Иван", "Стрельцов", 24);
        Person helen = new Person("Елена", "Рощина", 22);
        Person dmitry = new Person("Дмитрий", "Егоров", 30);
        Map<Person, Status> oldValues = new HashMap<>();
        oldValues.put(alex, PENDING);
        oldValues.put(dmitry, DECLINED);
        oldValues.put(ivan, ACCEPTED);

        Map<Person, Status> newValues = new HashMap<>();
        newValues.put(alex, DECLINED);
        newValues.put(helen, PENDING);

        oldValues.forEach(newValues::putIfAbsent);

        assertThat(newValues, hasEntry(alex, DECLINED));
        assertThat(newValues, hasEntry(ivan, ACCEPTED));
        assertThat(newValues, hasEntry(helen, PENDING));
    }
}
