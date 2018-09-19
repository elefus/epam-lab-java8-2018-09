package interfaces.compatibility;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * - Изменение реализации существующих методов, конструкторов или блоков инициализации
 * - Добавление новых полей, методов и конструкторов существующим классам и интерфейсам
 * - Удаление приватных полей, методов и конструкторов класса
 * - Перемещение методов наверх по иерархии классов
 * - Добавление новых классов и интерфейсов
 * @see <a href="https://habrahabr.ru/post/133907">Хабр: бинарная совместимость в примерах и не только</a>
 */
@SuppressWarnings("all")
public class Checker {

    public String method(A a) {
        return "Method with A param";
    }

    public String method(B a) {
        return "Method with B param";
    }

    @Test
    void testMethod() {
        C object = new C();
        assertThat(method((A)object), is("Method with A param"));
    }

    @Test
    void testDefaultMethod() {
        C object = new C();
        assertThat(object.superMethod(), is("Super method from A!"));
    }
}
