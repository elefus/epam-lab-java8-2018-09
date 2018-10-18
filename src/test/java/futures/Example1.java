package futures;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Example1 {

    @Test
    void complete() {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        assertThat(future.isDone(), is(false));

        future.complete(42);

        assertThat(future.isDone(), is(true));
        assertThat(future.isCompletedExceptionally(), is(false));
        assertThat(future.join(), is(42));
    }

    @Test
    void completeExceptionally() {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        assertThat(future.isDone(), is(false));

        future.completeExceptionally(new IllegalStateException());

        assertThat(future.isDone(), is(true));
        assertThat(future.isCompletedExceptionally(), is(true));
        assertThrows(CompletionException.class, future::join);
    }

    @Test
    void supplyAsync() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 123);

        future.join();
        assertThat(future.isDone(), is(true));
        assertThat(future.join(), is(123));
    }

    @Test
    void thenAccept() {
        CompletableFuture.supplyAsync(Example1::calc)
                         .thenAccept(System.out::println)
                         .thenRun(() -> System.out.println("After"))
                         .join();
    }

    @SneakyThrows
    private static int calc() {
        TimeUnit.SECONDS.sleep(1);
        return 123;
    }

    @Test
    void combine() {
//        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> "21")
//                                                            .thenApply(Integer::valueOf);
//
//        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> 21);
//        CompletableFuture<Integer> result = task1.thenCombine(task2, Integer::sum);
//        result.thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> "21")
                         .thenApply(Integer::valueOf)
                         .thenCombine(CompletableFuture.supplyAsync(() -> 12), Integer::sum)
                         .thenAccept(System.out::println)
                         .join();
    }

    @Test
    void exceptionally() {
        CompletableFuture.supplyAsync(() -> {
                                if (true) {
                                    throw new IllegalArgumentException();
                                }
                                return 123;
                         })
                         .exceptionally(throwable -> 42)
                         .thenApply(value -> {
//                             if (true) {
//                                 throw new IllegalStateException();
//                             }
                             return value + 1;
                         })
//                         .exceptionally(throwable -> 50)
                         .thenAccept(System.out::println)
                         .join();
    }
}
