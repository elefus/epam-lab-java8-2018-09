package futures;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
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

        CompletableFuture.completedFuture(1)
                         .thenApplyAsync(integer -> {
                             System.out.println(Thread.currentThread());
                             try {
                                 TimeUnit.SECONDS.sleep(3);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                             return integer + 1;
                         })
                         .thenCombine(CompletableFuture.completedFuture(2).thenApply(value -> {

                             return value;
                         }), (a, b) -> {
                             System.out.println(Thread.currentThread());
                             return Integer.sum(a, b);
                         })
                         .thenAccept(System.out::println)
                         .join();

//        CompletableFuture.supplyAsync(() -> "21")
//                         .thenApply(Integer::valueOf)
//                         .thenCombine(CompletableFuture.supplyAsync(() -> 12), Integer::sum)
//                         .thenAccept(System.out::println)
//                         .join();
    }

    @Test
    void exceptionally() throws InterruptedException {
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
            return 123;
        });

        TimeUnit.SECONDS.sleep(1);

        CompletableFuture.runAsync(() -> {
            task.exceptionally(throwable -> {
                System.out.println(Thread.currentThread());
                return 42;
            }).thenApply(value -> value + 1)
                .thenAccept(System.out::println);
        });

        task.thenAccept(System.out::println);

        task.thenApplyAsync(integer -> integer - 1)
            .thenAccept(System.out::println);


        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    void name() throws ExecutionException, InterruptedException {
        ExecutorService service = new ForkJoinPool(1);
        Future<Boolean> task = service.submit(() -> {
            try {
                service.submit(() -> {
                    throw new RuntimeException();
                }).get();
            } catch (ExecutionException | InterruptedException ex) {
                return false;
            }
            return true;
        });

        TimeUnit.SECONDS.sleep(2);
        service.shutdownNow();

        boolean result = task.get();

        System.out.println(result);
    }

    @Test
    void fixedThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<?> started = service.submit(() -> IntStream.rangeClosed(0, 1_000)
                                                          .parallel()
                                                          .mapToObj(i -> {
                                                              System.out.println(Thread.currentThread());
                                                              return String.valueOf(i);
                                                          })
                                                          .forEach(System.out::println));
        service.shutdown();
        started.get();
    }

    @Test
    void fjp() throws ExecutionException, InterruptedException {
        ExecutorService service = new ForkJoinPool();
        Future<?> started = service.submit(() -> IntStream.rangeClosed(0, 1_000)
                                                          .parallel()
                                                          .mapToObj(i -> {
                                                              System.out.println(Thread.currentThread());
                                                              return String.valueOf(i);
                                                          })
                                                          .forEach(System.out::println));
        service.shutdown();
        started.get();
    }

    @Test
    void mutableFuture() {
        CompletableFuture<ArrayList<Integer>> result = CompletableFuture.supplyAsync(() -> new ArrayList<>(Arrays.asList(1, 2, 3)));

        result.thenApply(list -> {
            list.add(1);
            return list;
        }).thenAccept(System.out::println);

        result.thenApply(list -> {
            list.clear();
            return list;
        }).thenAccept(System.out::println);
    }
}
