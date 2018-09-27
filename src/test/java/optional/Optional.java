package optional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    private Optional() {
        value = null;
    }

    private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T>empty() {
        return (Optional<T>) EMPTY;
    }

    public static <T> Optional<T>of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T>ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        if (!isPresent()) {
            throw new NoSuchElementException();
        }
        return value;
    }

    public void ifPresent(Consumer<T> action) {
        if (isPresent()) {
            action.accept(value);
        }
    }

    public T orElse(T defaultValue) {
        return isPresent() ? value : defaultValue;
    }

    public T orElseGet(Supplier<T> defaultValueSupplier) {
        return isPresent() ? value : defaultValueSupplier.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<X> throwableSupplier) throws X {
        if (isPresent()) {
            return value;
        }
        throw throwableSupplier.get();
    }

    public Optional<T> filter(Predicate<T> condition) {
        if (isPresent()) {
            return condition.test(value) ? this : empty();
        }
        return this;
    }

    public <R> Optional<R> map(Function<T, R> mapper) {
        if (isPresent()) {
            return ofNullable(mapper.apply(value));
        }
        return empty();
    }

    public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
        return isPresent() ? mapper.apply(value) : empty();
    }
}
