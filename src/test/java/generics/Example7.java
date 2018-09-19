package generics;

import java.util.Collection;

@SuppressWarnings("all")
public class Example7 {

    public static <T extends Comparable<? super T>> T max(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }


    private static <T extends Object & Comparable<T>> T genericMax(Collection<? extends T> collection) {
        return null;
    }
}
