package lambda.part3.example;

import lambda.data.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class Example2 {

    public static class FilterUtil<T> {

        /**
         * Статический фабричный метод.
         * @param source Исходный список.
         * @param <T> Тип элементов исходного списка.
         * @return Созданный объект.
         */
        public static <T> FilterUtil<T> from(List<T> source) {
            throw new UnsupportedOperationException();
        }

        /**
         * Создает объект для фильтрации, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент проверяется на соответствие заданному условию.
         * ([T], (T → boolean)) → [T]
         * @param condition условие по которому производится отбор.
         */
        public FilterUtil<T> filter(Predicate<T> condition) {
            throw new UnsupportedOperationException();
        }

        public List<T> getResult() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void findIvanWithDeveloperExperienceAndWorkedInEpamMoreThenYearAtOnePositionUsingFilterUtil() {
        List<Employee> employees = Example1.getEmployees();

        List<Employee> result = null;

        assertThat(result, contains(employees.get(0), employees.get(5)));
    }
}
