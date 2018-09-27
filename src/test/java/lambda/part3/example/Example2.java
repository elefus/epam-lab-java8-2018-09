package lambda.part3.example;

import com.google.common.io.Files;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class Example2 {

    public static class FilterUtil<T> {

        private final List<T> source;

        private FilterUtil(List<T> source) {
            this.source = source;
        }

        /**
         * Статический фабричный метод.
         *
         * @param source Исходный список.
         * @param <T>    Тип элементов исходного списка.
         * @return Созданный объект.
         */
        public static <T> FilterUtil<T> from(List<T> source) {
            return new FilterUtil<>(source);
        }

        /**
         * Создает объект для фильтрации, передавая ему новый список, построенный на основе исходного.
         * Для добавления в новый список каждый элемент проверяется на соответствие заданному условию.
         * ([T], (T → boolean)) → [T]
         *
         * @param condition условие по которому производится отбор.
         */
        public FilterUtil<T> filter(Predicate<T> condition) {
            ArrayList<T> result = new ArrayList<>();

            source.forEach(element -> {
                if (condition.test(element)) {
                    result.add(element);
                }
            });

//            for (T element : source) {
//                if (condition.test(element)) {
//                    result.add(element);
//                }
//            }

//            for (Iterator<T> iterator = source.iterator(); iterator.hasNext(); ) {
//                T element = iterator.next();
//                if (condition.test(element)) {
//                    result.add(element);
//                }
//            }
            return from(result);
        }

        public List<T> getResult() {
            return new ArrayList<>(source);
        }
    }

    @Test
    public void findIvanWithDeveloperExperienceAndWorkedInEpamMoreThenYearAtOnePositionUsingFilterUtil() {
        List<Employee> employees = Example1.getEmployees();

        List<Employee> result = FilterUtil.from(employees)
                                          .filter(employee -> "Иван".equals(employee.getPerson().getFirstName()))
                                          .filter(this::hasDeveloperExpirience)
                                          .filter(this::workedInEpamMoreThenYear)
                                          .getResult();

        assertThat(result, contains(employees.get(0), employees.get(5)));
    }

    private boolean workedInEpamMoreThenYear(Employee employee) {
        return !FilterUtil.from(employee.getJobHistory())
                          .filter(entry -> "EPAM".equals(entry.getEmployer()))
                          .filter(entry -> entry.getDuration() > 1)
                          .getResult()
                          .isEmpty();
    }

    private boolean hasDeveloperExpirience(Employee employee) {
        return !FilterUtil.from(employee.getJobHistory())
                          .filter(entry -> "dev".equals(entry.getPosition()))
                          .getResult()
                          .isEmpty();
    }
}
