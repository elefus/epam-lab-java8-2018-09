package lambda.part4.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public interface FluentIterable<T> {

    static <T> FluentIterable<T> of(T...values) {
        return of(Arrays.asList(values));
    }

    static <T> FluentIterable<T> of(Iterable<T> source) {
        return new FluentIterableImpl<>(source);
    }


    /**
     * Returns a stream consisting of the elements of this stream that match the given predicate.
     *
     * This is an intermediate operation.
     *
     * @param predicate a non-interfering, stateless predicate to apply to each element to determine if it should be included
     * @return the new FluentIterable
     */
    FluentIterable<T> filter(Predicate<? super T> predicate);

    FluentIterable<T> skip(long n);

    FluentIterable<T> limit(long maxSize);

    FluentIterable<T> distnict();


    List<T> toList();

    /**
     * Returns an array containing the elements of this stream.
     *
     * This is a terminal operation.
     *
     * @return an array containing the elements of this stream
     */
    Object[] toArray();

    /**
     * Returns an array containing the elements of this stream, using the
     * provided {@code generator} function to allocate the returned array, as
     * well as any additional arrays that might be required for a partitioned
     * execution or for resizing.
     *
     * This is a terminal operation.
     *
     * @apiNote
     * The generator function takes an integer, which is the size of the
     * desired array, and produces an array of the desired size.  This can be
     * concisely expressed with an array constructor reference:
     *
     * @param generator a function which produces a new array of the desired type and the provided length
     * @return an array containing the elements in this stream
     * @throws ArrayStoreException if the runtime type of the array returned
     * from the array generator is not a supertype of the runtime type of every element in this stream
     */
    T[] toArray(IntFunction<T[]> generator);
}
