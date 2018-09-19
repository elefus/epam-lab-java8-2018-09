package interfaces;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
interface Left {

    int value = 100;

    static int getValue() {
        return value;
    }
}

@SuppressWarnings("all")
interface Right {

    int value = -100;

    static int getValue() {
        return value;
    }
}

@SuppressWarnings("all")
class GrandGrandChild extends GrandChild implements Left, Right {

    GrandGrandChild() {
        int value = getValue();
        System.out.println(value);
    }
}

/**
 * 1. Статические методы в интерфейсе являются частью интерфейса.
 *    Их нельзя вызывать на объектах, реализующих интерфейс (в отличие от статических методов классов).
 *
 * 2. Статические методы в интерфейсе помогают обеспечивать безопасность.
 *    Не позволяют классам, которые реализуют интерфейс, переопределить их.
 *
 * 3. Нельзя определять статические методы, совпадающие по сигнатуре с методами класса Object.
 *    В классе не может быть два метода с совпадающими сигнатурами.
 *
 * 4. Статические методы в интерфейсах хороши для обеспечения вспомогательных методов.
 *    Stream, List, Map, Set (Java9+)
 */
@SuppressWarnings("all")
class Example2 {

//    Map<String, Integer> map = Map.ofEntries(Map.entry("1", 1), Map.entry("2", 2));

    @Test
    void testGrandGrandChild() {
        GrandGrandChild grandGrandChild = new GrandGrandChild();

        assertThat(grandGrandChild.getValue(), is(0));
    }

    @Test
    void testInterfaceStaticMethods() {
        GrandGrandChild grandGrandChild = new GrandGrandChild();
        Left left = grandGrandChild;
        Right right = grandGrandChild;
//        assertThat(left.getValue(), is(null));
//        assertThat(right.getValue(), is(null));
    }
}
