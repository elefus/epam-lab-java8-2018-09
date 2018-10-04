package lambda.part4.example;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import optional.Optional;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

@Value
class StageFilter<T> implements Iterable<T> {

    Iterator<T> source;
    Predicate<? super T> condition;

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new PrimitiveIterator();
    }

    private class PrimitiveIterator implements Iterator<T> {

        private T next;
        private boolean elementFound = false;

        @Override
        public boolean hasNext() {
            if (!elementFound) {
                while (source.hasNext()) {
                    next = source.next();
                    if (condition.test(next)) {
                        elementFound = true;
                        break;
                    }
                }
            }
            return elementFound;
        }

        @Override
        public T next() {
            if (hasNext()) {
                elementFound = false;
                return next;
            }
            throw new NoSuchElementException();
        }
    }

    private class OptionalNPEIterator implements Iterator<T> {

        private T next;
        private boolean elementFound = false;

        @Override
        public boolean hasNext() {
            if (!elementFound) {
                findNextMatchesElement().ifPresent(element -> {
                    elementFound = true;
                    next = element;
                });
            }
            return elementFound;
        }

        @Override
        public T next() {
            if (hasNext()) {
                elementFound = false;
                return next;
            }
            throw new NoSuchElementException();
        }

        private Optional<T> findNextMatchesElement() {
            while (source.hasNext()) {
                T next = source.next();
                if (condition.test(next)) {
                    return Optional.ofNullable(next);
                }
            }
            return Optional.empty();
        }
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull"})
    @RequiredArgsConstructor
    private static class UglyOptionalIterator<T> implements Iterator<T> {

        private static final Object NULL_ELEMENT = new Object();

        private final Iterator<T> source;
        private final Predicate<? super T> condition;

        private Optional<Object> next = null;

        @Override
        public boolean hasNext() {
            if (next == null) {
                next = findNextMatchesElement();
            }
            return next.isPresent();
        }

        @Override
        public T next() {
            try {
                if (hasNext()) {
                    return (T) next.filter(element -> NULL_ELEMENT != element).orElse(null);
                }
                throw new NoSuchElementException();
            } finally {
                next = null;
            }
        }

        private Optional<Object> findNextMatchesElement() {
            while (source.hasNext()) {
                Object next = source.next();
                if (condition.test((T)next)) {
                    return Optional.of(Optional.ofNullable(next).orElse(NULL_ELEMENT));
                }
            }
            return Optional.empty();
        }
    }

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType"})
    @RequiredArgsConstructor
    private static class OptionalIterator<T> implements Iterator<T> {

        private final Iterator<T> source;
        private final Predicate<? super T> condition;

        private Optional<Optional<Optional<T>>> foundedElement = Optional.empty();

        @Override
        public boolean hasNext() {
            foundedElement = foundedElement.or(this::findNextMatchesElement);
            return foundedElement.flatMap(Function.identity())
                                 .isPresent();
        }

        @Override
        public T next() {
            return foundedElement.or(this::findNextMatchesElement)
                                 .flatMap(Function.identity())
                                 .orElseThrow(NoSuchElementException::new)
                                 .orElse(null);
        }

        private Optional<Optional<Optional<T>>> findNextMatchesElement() {
            while (source.hasNext()) {
                T next = source.next();
                if (condition.test(next)) {
                    return Optional.of(Optional.of(Optional.ofNullable(next)));
                }
            }
            return Optional.of(Optional.empty());
        }
    }

}
