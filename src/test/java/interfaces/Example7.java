package interfaces;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
interface A {

    default Number getValue() {
        System.out.println("A.getValue()");
        return 0;
    }
}

@SuppressWarnings("all")
interface B extends A {

    default Integer getValue() {
        System.out.println("B.getValue()");
        return 1;
    }
}

@SuppressWarnings("all")
interface C extends A {

    Integer getValue();
}

@SuppressWarnings("all")
class D implements B, C {

    @Override
    public Integer getValue() {
        return B.super.getValue();
    }

    @Test
    void test() {
        D object = new D();

        assertThat(object.getValue(), is(-1));
    }
}
