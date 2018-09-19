package interfaces;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
abstract class Base {

//    public Base() {
//        System.out.println(getProperty());
//    }

    public Base() {
        super();
        System.out.println("Base::ctr");
        System.out.println(getPropertyLength());
    }

    abstract String getProperty();

    public int getPropertyLength() {
        return getProperty().length();
    }
}

@SuppressWarnings("all")
class Derived extends Base {

    private final String property = "DERIVED".toString();

    public Derived() {
        super();
        System.out.println("Derived::ctr");
    }

    @Override
    String getProperty() {
        return property;
    }

    @Override
    public int getPropertyLength() {
        return 7;
    }
}

class Example4 {

    @Test
    void testPropertyLength() {
        Base base = new Derived();

        assertThat(base.getPropertyLength(), is(7));
    }
}
