package interfaces;

/**
 * Single Abstract Method (SAM)-interface, предназначенный для суммирования элементов.
 * @param <T> Тип суммируемых элементов.
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface Summator<T> {

    T sum(T left, T right);

    static void add() {

    }

    /*
     * Default-методы позволяют добавлять поведение ранее существовавшим интерфейсам.
     */
    default T twice(T value) {
        return sum(value, value);
    }
}
