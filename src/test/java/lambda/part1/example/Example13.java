package lambda.part1.example;

import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class Example13 {

    @Test
    void test() {
        Talkative talkative = () -> System.out.println(say());
        talkative.run();
    }

    private String say() {
        return "Bonjour";
    }
}

@SuppressWarnings("all")
interface Talkative extends Runnable {

    default String say() {
        return "Hello!";
    }
}
