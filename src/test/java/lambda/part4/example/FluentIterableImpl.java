package lambda.part4.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class FluentIterableImpl<T> implements FluentIterable<T> {

    private final Iterable<T> source;

    public FluentIterableImpl(Iterable<T> source) {
        this.source = source;
    }

    @Override
    public FluentIterable<T> filter(Predicate<? super T> predicate) {
        return new FluentIterableImpl<>(new StageFilter<>(source.iterator(), predicate));
    }

    @Override
    public FluentIterable<T> skip(long n) {
        return new FluentIterableImpl<>(new StageSkip<>(source.iterator(), n));
    }

    @Override
    public FluentIterable<T> limit(long maxSize) {
        return new FluentIterableImpl<>(new StageLimit<>(source.iterator(), maxSize));
    }

    @Override
    public FluentIterable<T> distnict() {
        return new FluentIterableImpl<>(new StageDistinct<>(source.iterator()));
    }

    @Override
    public List<T> toList() {
        ArrayList<T> result = new ArrayList<>();
        source.forEach(result::add);
        return result;
    }


    @Override
    public Object[] toArray() {
        return toList().toArray();
    }

    @Override
    public T[] toArray(IntFunction<T[]> generator) {
        List<T> result = toList();
        return result.toArray(generator.apply(result.size()));
    }
}
