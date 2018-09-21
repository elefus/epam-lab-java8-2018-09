package lambda.part1.example;

import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
class Example7 {

    @FunctionalInterface
    private interface Usable {

        void use();
    }

    private void perform(Runnable runnable) {
        System.out.println("Runnable");
        runnable.run();
    }

    private void perform(Usable usable) {
        System.out.println("Usable");
        usable.use();
    }

    private void doSomething() {
        System.out.println("Non-static method doSomething");
    }

    @Test
    void ambiguousMethodReference() {
        perform((Runnable)() -> System.out.println("Lambda for Usable"));

        Usable doSomething = this::doSomething;
        perform(doSomething);
    }
}
