package interfaces;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
interface First {

    int value = 100;

    default int getValue() {
        return value;
    }

//    int getValue();

//    default int getValue() {
//        return value;
//    }
}

@SuppressWarnings("all")
interface Second {

    int value = -100;

    default int getValue() {
        return value;
    }

//    int getValue();

//    default int getValue() {
//        return value;
//    }
}

@SuppressWarnings("all")
class Example3 implements First, Second {

    @Override
    public int getValue() {
        return First.super.getValue() + Second.super.getValue();
    }

    @Test
    void testValues() {
        Example3 impl = new Example3();
        assertThat(impl.getValue(), is(0));
    }
}
