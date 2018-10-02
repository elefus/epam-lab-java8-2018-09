package lambda.part1.example;

import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class Example13 {

    @Test
    void test() {
        Talkative talkative = () -> System.out.println(Talkative.empty().say());
        talkative.run();
    }

    private String say() {
        return "Bonjour";
    }
}

@SuppressWarnings("all")
@FunctionalInterface
interface Talkative extends Runnable {

    static Talkative empty() {
        return () -> {};
    }

    default String say() {
        return "Hello!";
    }
}
