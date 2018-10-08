package streams.part1.example;

import lambda.data.Employee;
import lambda.part3.example.Example1;
import org.junit.jupiter.api.Test;

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

        String[] ivansLastNames = null;

        assertThat(ivansLastNames, arrayContaining("Мельников", "Александров"));
    }

    @Test
    void checkAny25AgedIvanHasDevExperience() {
        List<Employee> employees = Example1.getEmployees();


        boolean any25IvanHasDevExperience = false;

        assertThat(any25IvanHasDevExperience, is(true));
    }
}
