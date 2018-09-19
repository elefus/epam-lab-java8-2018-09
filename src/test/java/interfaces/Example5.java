package interfaces;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
interface Interface {

    default int getValue() {
        return 50;
    }
}

@SuppressWarnings("all")
class Class {

    private int getValue() {
        return 999;
    }

//    private int getValue() {
//        return 999;
//    }
}

/**
 * 1. Методы по умолчанию предназначены для расширения существующих интерфейсов новыми возможностями.
 *
 * 2. Методы по умолчанию позволяют избежать создания служебных классов.
 *    Все необходимые методы могут быть представлены в самих интерфейсах.
 *
 * 3. Методы по умолчанию дают свободу классам выбрать метод, который нужно переопределить.
 *
 * 4. Метод по умолчанию не может переопределить метод класса java.lang.Object.
 */
class Example5 extends Class implements Interface {

    @Test
    void testValue() {
        Example5 ref = new Example5();

        assertThat(ref.getValue(), is(999));
    }
}
