package lambda.example;

import org.junit.jupiter.api.Test;

class Example6 {

    @Test
    void runNormalRunnable() {
        Runnable runnable = () -> {
//            Thread.sleep(100);
        };
    }

    // TODO is it functional?
    private interface ThrowableRunnable {

        void run() throws Exception;
    }

    @Test
    void runThrowableRunnable() {
//        ThrowableRunnable throwableRunnable = () -> Thread.sleep(100);

//        throwableRunnable.run();
    }

    @Test
    void callNormalCallable() {
//        Callable<Integer> callable = () -> {
//            Thread.sleep(100);
//            return 42;
//        };
//        callable.call();
    }

    @Test
    void throwRuntimeExceptionFromRunnable() {
//        Runnable runnable = () -> {
//            throw new IllegalStateException("Какое-то исключение времени исполнения");
//        };
//        runnable.run();
    }
}
