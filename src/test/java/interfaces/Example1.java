package interfaces;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
class Parent {

    private static final int value = 42;

    public static int getValue() {
        return value;
    }
}

@SuppressWarnings("all")
class Child extends Parent {

    private static final int value = 0;

    public static int getValue() {
        return value;
    }
}

@SuppressWarnings("all")
class GrandChild extends Child {

    private static final int value = -42;
}

/**
 * @see <a href="https://jcp.org/en/jsr/detail?id=335">JSR-335: Lambda Expressions for the Java Programming Language</a>
 */
@SuppressWarnings("all")
class Example1 {

    // Parent - 42
    // Child - 0
    // GrandChild - -42

    @Test
    void testParent() {
        Parent parent = new Parent();

        assertThat(parent.getValue(), is(42));
        assertThat(Parent.getValue(), is(42));
    }

    @Test
    void testChild() {
        Parent parent = new Child();

        assertThat(parent.getValue(), is(42));
        assertThat(Child.getValue(), is(0)); // 42
    }

    @Test
    void testGrandChild() {
        Parent parent = new GrandChild();

        assertThat(parent.getValue(), is(42));
        assertThat(GrandChild.getValue(), is(0));
    }

    @Test
    void testNull() {
        Parent parent = null;

        assertThat(parent.getValue(), is(42));
        assertThat(((GrandChild)null).getValue(), is(0));
    }
}
