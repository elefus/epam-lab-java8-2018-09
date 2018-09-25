package lambda.part3.example;

import lambda.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class Example3 {

    @SuppressWarnings("unused")
    public static class LazyFilterUtil<T> {

        /**
         * Статический фабричный метод.
         *
         * @param source Исходный список.
         * @param <T>    Тип элементов исходного списка.
         * @return Созданный объект.
         */
        public static <T> LazyFilterUtil<T> from(List<T> source) {
            throw new UnsupportedOperationException();
        }

        /**
         * Создает новый список на основе исходного, проверяя каждый элемент на соответствие заданному условию.
         * ([T], (T -> boolean)) -> [T]
         *
         * @param condition условие по которому производится отбор.
         */
        public LazyFilterUtil<T> filter(Predicate<T> condition) {
            throw new UnsupportedOperationException();
        }

        public List<T> force() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void findIvanWithDeveloperExperienceAndWorkedInEpamMoreThenYearAtOnePositionUsingLazyFilterUtil() {
        List<Employee> employees = Example1.getEmployees();

        List<Employee> result = null;

        assertThat(result, contains(employees.get(0), employees.get(5)));
    }
}
