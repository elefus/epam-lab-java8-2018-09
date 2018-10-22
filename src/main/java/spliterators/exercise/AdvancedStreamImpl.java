package spliterators.exercise;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class AdvancedStreamImpl<T> implements AdvancedStream<T> {

    private final Stream<T> original;

    public AdvancedStreamImpl(Stream<T> original) {
        this.original = original;
    }

    @Override
    public AdvancedStream<T> takeWhile(Predicate<? super T> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <U> AdvancedStream<Pair<T, U>> zip(Stream<U> another) {
        throw new UnsupportedOperationException();
    }

    // Delegate methods

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return original.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return original.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return original.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return original.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return original.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return original.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return original.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return original.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return original.flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return original.distinct();
    }

    @Override
    public Stream<T> sorted() {
        return original.sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return original.sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return original.peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return original.limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return original.skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        original.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        original.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return original.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return original.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return original.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return original.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return original.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return original.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return original.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return original.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return original.max(comparator);
    }

    @Override
    public long count() {
        return original.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return original.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return original.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return original.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return original.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return original.findAny();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }

    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return original.spliterator();
    }

    @Override
    public boolean isParallel() {
        return original.isParallel();
    }

    @NotNull
    @Override
    public Stream<T> sequential() {
        return original.sequential();
    }

    @NotNull
    @Override
    public Stream<T> parallel() {
        return original.parallel();
    }

    @NotNull
    @Override
    public Stream<T> unordered() {
        return original.unordered();
    }

    @NotNull
    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return original.onClose(closeHandler);
    }

    @Override
    public void close() {
        original.close();
    }
}
