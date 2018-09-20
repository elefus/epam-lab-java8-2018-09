package lambda.example;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "ConstantConditions", "UnnecessaryLocalVariable"})
public class Example10 {

    private List<String> strings = Arrays.asList("1", "2", "3");

    private byte[] veryBigField = new byte[100_000_000];

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);

        Example10 deadly = new Example10();
        new Checker(deadly.getPredicateEnclosingField()).start();

        Example10 zombie = new Example10();
        new Checker(zombie.getPredicateEnclosingThis()).start();

        TimeUnit.SECONDS.sleep(3);

        deadly = null;
        System.out.println(deadly);

        zombie = null;
        System.out.println(zombie);

        System.gc();
        System.gc();
        System.gc();
        TimeUnit.SECONDS.sleep(7);
    }

    private Predicate<String> getPredicateEnclosingField() {
        return this.strings::contains;
    }

    private Predicate<String> getPredicateEnclosingThis() {
        return string -> strings.contains(string);
    }
}

class Checker extends Thread {

    private final Predicate<String> predicate;

    Checker(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Check: " + predicate + ", result: " + predicate.test("4"));
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}