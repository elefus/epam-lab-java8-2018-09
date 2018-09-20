package lambda.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

class Example6 {

    @Test
    void runNormalRunnable() {
        Runnable runnable = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    @FunctionalInterface
    private interface ThrowableRunnable {

        void run() throws Exception;
    }

    @Test
    void runThrowableRunnable() throws Exception {
        ThrowableRunnable throwableRunnable = () -> Thread.sleep(100);

        throwableRunnable.run();
    }

    @Test
    void callNormalCallable() {
        Callable<Integer> callable = () -> {
            Thread.sleep(100);
            return 42;
        };
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void throwRuntimeExceptionFromRunnable() {
        Runnable runnable = () -> {
            throw new IllegalStateException("Какое-то исключение времени исполнения");
        };


        runnable.run();
    }
}
