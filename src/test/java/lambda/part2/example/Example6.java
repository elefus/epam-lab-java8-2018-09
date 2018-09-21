package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.closeTo;

/**
 * Общее количество стандартных интерфейсов - 43.
 * @see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html">java.util.function</a>
 */
@SuppressWarnings({"Convert2MethodRef", "RedundantStringConstructorCall", "unused", "AssignmentToLambdaParameter"})
class Example6 {

    // Arity
    // 0: () -> R
    // 1: (T1) -> R
    // 2: (T1, T2) -> R
    // 3: (T1, T2, T3) -> R
    // 4: (T1, T2, T3, T4) -> R

    // IO int
    // IO long
    // IO double
    //  O boolean
    //  O void

    /**
     * Arity 0
     *
     *  1. Supplier: () -> R
     *  2. IntSupplier: () -> int
     *  3. LongSupplier: () -> long
     *  4. DoubleSupplier: () -> double
     *  5. BooleanSupplier: () -> boolean
     *  -. Runnable: () -> void
     */
    @Test
    void suppliers() {
        Supplier<String> strSupplier = () -> new String("some string");

        assertThat(strSupplier.get(), is("some string"));
        assertThat(strSupplier.get(), is(not(sameInstance(strSupplier.get()))));

        IntSupplier intSupplier = () -> 42;
        assertThat(intSupplier.getAsInt(), is(intSupplier.getAsInt()));

        LongSupplier longSupplier = () -> 1_000_000_000_000L;
        assertThat(longSupplier.getAsLong(), greaterThan((long)Integer.MAX_VALUE));

        DoubleSupplier doubleSupplier = () -> 0.1;
        assertThat(doubleSupplier.getAsDouble(), is(closeTo(0.1, 0.0001)));

        BooleanSupplier booleanSupplier = () -> true;
        assertThat(booleanSupplier.getAsBoolean(), is(true));

        Runnable runnable = () -> System.out.println("runnable");
    }

    /**
     * Arity 1
     *
     *  1. Consumer: (T) -> void
     *  2. IntConsumer: (int) -> void
     *  3. LongConsumer: (long) -> void
     *  4. DoubleConsumer: (double) -> void
     */
    @Test
    void consumers() {
        // String -> void
        Consumer<String> stringConsumer = System.out::println;
        stringConsumer.accept("string");
        Consumer<String> stringConsumer1 = stringConsumer.andThen(string -> System.out.println("another + " + string))
                                                         .andThen(string -> System.out.println("2: " + string));

        stringConsumer1.accept("1");

        IntConsumer intConsumer = System.out::println;
        intConsumer.accept(42);

        LongConsumer longConsumer = System.out::println;
        longConsumer.accept(1_000_000_000_000L);

        DoubleConsumer doubleConsumer = System.out::println;
        doubleConsumer.accept(42.5);
    }

    /**
     * Arity 1
     *
     *  5. Predicate: (T) -> boolean
     *  6. IntPredicate: (int) -> boolean
     *  7. LongPredicate: (long) -> boolean
     *  8. DoublePredicate: (double) -> boolean
     */
    @Test
    void predicates() {
        Predicate<String> emptyChecker = String::isEmpty;
        assertThat(emptyChecker.test(""), is(true));
        assertThat(emptyChecker.test("1"), is(false));

        // String -> boolean
        Predicate<String> isAstring = "A"::equals;

        List<String> values = Arrays.asList("A", "B", "C");
        Predicate<String> containsInValues = values::contains;
        assertThat(containsInValues.test("B"), is(true));
        assertThat(containsInValues.test("D"), is(false));

//        Predicate<String> notContainsInValues = string -> !values.contains(string);
        Predicate<String> notContainsInValues = containsInValues.negate();
        Predicate<String> alwaysTrue = containsInValues.or(notContainsInValues);
        Predicate<String> alwaysFalse = containsInValues.and(notContainsInValues);


        IntPredicate positiveChecker = value -> value > 0;
        assertThat(positiveChecker.test(-1), is(false));

        LongPredicate greaterThan1000Checker = value -> value > 1000;
        assertThat(greaterThan1000Checker.test(0), is(false));

        DoublePredicate closeToInteger = value -> Math.abs(value - Math.round(value)) < 0.0001;
        assertThat(closeToInteger.test(1.0), is(true));
        assertThat(closeToInteger.test(1.1), is(false));
    }

    /**
     * Arity 1
     *
     *  9. UnaryOperator: (T) -> T
     * 10. IntUnaryOperator: (int) -> int
     * 11. LongUnaryOperator: (long) -> long
     * 12. DoubleUnaryOperator: (double) -> double
     */
    @Test
    void unaryOperators() {
        UnaryOperator<String> reverse = string -> new StringBuilder(string).reverse().toString();
        assertThat(reverse.apply("string"), is("gnirts"));

        IntUnaryOperator negate = i -> -i;
        assertThat(negate.applyAsInt(1), is(-1));

        LongUnaryOperator increment = value -> ++value;
        assertThat(increment.applyAsLong(1_000_000_000_000L), is(1_000_000_000_001L));

        DoubleUnaryOperator decrement = value -> --value;
        assertThat(decrement.applyAsDouble(3.0), is(closeTo(2.0, 0.0001)));
    }

    /**
     * Arity 1
     *
     * 13. Function: (T) -> R
     * 14. ToIntFunction: (T) -> int
     * 15. ToLongFunction: (T) -> long
     * 16. ToDoubleFunction: (T) -> double
     * 17. IntFunction: (int) -> R
     * 18. LongFunction: (long) -> R
     * 19. DoubleFunction: (double) -> R
     * 20. IntToLongFunction: (int) -> long
     * 21. IntToDoubleFunction: (int) -> double
     * 22. LongToIntFunction: (long) -> int
     * 23. LongToDoubleFunction: (long) -> double
     * 24. DoubleToIntFunction: (double) -> int
     * 25. DoubleToLongFunction: (double) -> long
     */
    @Test
    void functions() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> firstNameExtractor = Person::getFirstName;
        assertThat(firstNameExtractor.apply(person), is("Иван"));

        ToIntFunction<Person> ageExtractor = Person::getAge;
        assertThat(ageExtractor.applyAsInt(person), is(33));

        ToLongFunction<Long> longUnboxer = Long::longValue;
        assertThat(longUnboxer.applyAsLong(100L), is(100L));

        ToDoubleFunction<Double> doubleUnboxer = Double::doubleValue;
        assertThat(doubleUnboxer.applyAsDouble(10.5), is(10.5));

        IntFunction<String> intToString = Integer::toString;
        assertThat(intToString.apply(123), is("123"));

        IntToDoubleFunction intToLong = value -> value;
        IntToDoubleFunction intToDouble = value -> value;

        LongToIntFunction longToInt = value -> (int)value;
        LongToDoubleFunction longToDouble = value -> value;

        DoubleToIntFunction doubleToInt = value -> (int)value;
        DoubleToLongFunction doubleToLong = Math::round;
    }

    /**
     * Arity 2
     *
     *  1. BiConsumer: (T, U) -> void
     *  2. ObjIntConsumer: (T, int) -> void
     *  3. ObjLongConsumer: (T, long) -> void
     *  4. ObjDoubleConsumer: (T, double) -> void
     */
    @Test
    void biConsumers() {
        Person ivan = new Person("Иван", "Мельников", 33);

        BiConsumer<Person, String> printGreeting = (person, appeal) -> System.out.println(appeal + " " + person.getFirstName());
        printGreeting.accept(ivan, "Mr.");

        ObjIntConsumer<Person> printIncrementedAge = (person, value) -> System.out.println(person.getAge() + value);
        printIncrementedAge.accept(ivan, 10);

        ObjLongConsumer<String> printStringIfLengthLessThen = (string, value) -> System.out.println(string.length() < value ? string : "");
        printStringIfLengthLessThen.accept("12345", 10);

        ObjDoubleConsumer<String> printIncrementedValue = (stringRepresentation, value) -> System.out.println(Double.parseDouble(stringRepresentation) + value);
        printIncrementedValue.accept("10.5", 0.5);
    }

    /**
     * Arity 2
     *
     *  5. BiPredicate: (T, U) -> boolean
     */
    @Test
    void biPredicate() {
        BiPredicate<String, Person> firstNameChecker = (string, person) -> string.equals(person.getFirstName());
        assertThat(firstNameChecker.test("Иван", new Person("Иван", "Мельников", 33)), is(true));
    }

    /**
     * Arity 2
     *  6. BinaryOperator: (T, T) -> T
     *  7. IntBinaryOperator: (int, int) -> int
     *  8. LongBinaryOperator: (long, long) -> long
     *  9. DoubleBinaryOperator: (double, double) -> double
     */
    @Test
    void binaryOperators() {
        BinaryOperator<String> concat = String::concat;
        assertThat(concat.apply("a", "b"), is("ab"));

        LongBinaryOperator longMin = Long::min;
        assertThat(longMin.applyAsLong(10, 100), is(10));

        IntBinaryOperator intSum = Integer::sum;
        assertThat(intSum.applyAsInt(1, 2), is(3));

        DoubleBinaryOperator doubleMax = Double::max;
        assertThat(doubleMax.applyAsDouble(5.9, 0.8), is(closeTo(5.9, 0.0001)));
    }

    /**
     * Arity 2
     *
     * 10. BiFunction: (T, U) -> R
     * 11. ToIntBiFunction: (T, U) -> int
     * 12. ToLongBiFunction: (T, U) -> long
     * 13. ToDoubleBiFunction: (T, U) -> double
     */
    @Test
    void biFunction() {
        Person ivan = new Person("Иван", "Мельников", 33);

        BiFunction<Person, String, Person> changeFirstName = (person, firstName) -> new Person(firstName, person.getLastName(), person.getAge());
        assertThat(changeFirstName.apply(ivan, "Семён"), is(new Person("Семён", "Мельников", 33)));

        ToIntBiFunction<Person, String> calcLengthAppeal = (person, string) -> String.format("%s %s %s", string, person.getFirstName(), person.getLastName()).length();
        assertThat(calcLengthAppeal.applyAsInt(ivan, "Mr."), is(18));

        ToLongBiFunction<Person, String> toLongBiFunction = (person, string) -> 42;
        ToDoubleBiFunction<Person, String> toDoubleBiFunction = (person, string) -> 42;
    }
}
