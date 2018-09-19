package lambda.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @see <a href="https://www.ibm.com/developerworks/library/j-java8idioms10/index.html">Java8 lambdas and closures</a>
 */
@SuppressWarnings("all")
class Example4 {

    private static String performInCurrentThread(Callable<String> task) throws Exception {
        return task.call();
    }

    @Test
    void closureAnonymousClass() throws Exception {
        // FIXME effectively final
        Person person = new Person("Иван", "Мельников", 33);

        String firstName = performInCurrentThread(new Callable<String>() {
            @Override
            public String call() {
                return person.getFirstName();
            }
        });

        assertThat(firstName, is("Иван"));
    }

    @Test
    void closureStatementLambda() throws Exception {
        Person person = new Person("Иван", "Мельников", 33);

        String greeting = performInCurrentThread(() -> {
            String prefix = person.getAge() > 30 ? "Добрый день" : "Привет";
            return prefix + ", " + person.getFirstName();
        });

        assertThat(greeting, is("Добрый день, Иван"));
    }

    @Test
    void closureExpressionLambda() throws Exception {
        Person person = new Person("Иван", "Мельников", 33);

        String firstName = performInCurrentThread(() -> person.getFirstName());

        assertThat(firstName, is("Иван"));
    }

    @Test
    void closureObjectMethodReferenceLambda() throws Exception {
        Person person = new Person("Иван", "Мельников", 33);

        String firstName = performInCurrentThread(person::getFirstName);

        assertThat(firstName, is("Иван"));
    }

    private Person person;

    @Test
    void closureReferenceByObjectMethodReferenceLambda() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        String firstName = performInCurrentThread(person::getFirstName);

        person = null;

        assertThat(firstName, is("Иван"));
    }

    @Test
    void closureThisReferenceByExpressionLambda() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        String firstName = performInCurrentThread(() -> person.getFirstName());

        assertThat(firstName, is("Иван"));
    }

    @Test
    void overwriteReferenceClosuredByExpressionLambdaAfterUsing() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        Callable<String> task = () -> person.getFirstName();
        String firstName = performInCurrentThread(task);

        assertThat(firstName, is(null));

        person = new Person("Алексей", "Игнатенко", 25);

        assertThat(task.call(), is(null));
    }

    @Test
    void overwriteReferenceClosuredByObjectMethodReferenceLambdaAfterUsing() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        Callable<String> task = person::getFirstName;
        String firstName = performInCurrentThread(task);

        assertThat(firstName, is(null));

        person = new Person("Алексей", "Игнатенко", 25);

        assertThat(task.call(), is(null));
    }

    private Callable<String> performLaterFromCurrentThread(Callable<String> task) {
        return () -> {
            System.out.println("Некий код перед выполнением задачи...");
            return task.call();
        };
    }

    @Test
    void overwriteReferenceClosuredByExpressionLambdaBeforeUsing() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        Callable<String> delayedTask = performLaterFromCurrentThread(() -> person.getFirstName());

        person = new Person("Алексей", "Игнатенко", 25);

        String firstName = delayedTask.call();

        assertThat(firstName, is(null));
    }

    @Test
    void overwriteReferenceClosuredByObjectMethodReferenceLambdaBeforeUsing() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        Callable<String> delayedTask = performLaterFromCurrentThread(person::getFirstName);

        person = new Person("Алексей", "Игнатенко", 25);

        String firstName = delayedTask.call();

        assertThat(firstName, is(null));
    }

    private Person getPerson() {
        return person;
    }

    @Test
    void overwriteReferenceClosuredByObjectMethodReferenceGetPersonBeforeUsing() throws Exception {
        person = new Person("Иван", "Мельников", 33);

        Callable<String> delayedTask = performLaterFromCurrentThread(getPerson()::getFirstName);

        person = new Person("Алексей", "Игнатенко", 25);

        String firstName = delayedTask.call();

        assertThat(firstName, is(null));
    }
}
