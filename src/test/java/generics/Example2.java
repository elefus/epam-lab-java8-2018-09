package generics;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
class Example2 {

    @Test
    void test() {
        int value = 50;
        NumberHolder<MyNumber> intHolder = new NumberHolder<>(null);


        Holder<String> stringHolder = new Holder<>("abc");
        Holder rawStringHolder = stringHolder;
        rawStringHolder.setValue(1);
        System.out.println(stringHolder.getValue());


        NumberHolder rawHolder = intHolder;
//        NumberHolder<Object> objectHolder = intHolder;
//        NumberHolder<?> wildcardHolder = intHolder;

//        rawHolder.setValue("14assdsd23");

        MyNumber result = intHolder.getValue();

        System.out.println(intHolder.getValue());
//        Matcher<Integer> integerMatcher = is(value);
//        Matcher<Object> matcher = is(value);
//        assertThat(rawHolder.getValue(), matcher);
    }

    private abstract class MyNumber extends Number implements Comparable<String> {

    }

    private static class NumberHolder<T extends Number & Serializable & Comparable<String>> {

        private T value;

        public NumberHolder(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    private static class Holder<T> {

        private T value;

        public Holder(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
