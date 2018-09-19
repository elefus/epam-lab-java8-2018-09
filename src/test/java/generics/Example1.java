package generics;

import lombok.SneakyThrows;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @see <a href="https://youtu.be/mNyQYTp-Njw">Александр Маторин - Неочевидные дженерики</a>
 */
public class Example1 {

    @SneakyThrows
    public static void main(String[] args) {
        Arrays.stream(Pair.class.getDeclaredFields()).forEach(System.out::println);

        IndexedString indexedString = new IndexedString(1, "String");

        ParameterizedType indexedStringType = (ParameterizedType)indexedString.getClass().getGenericSuperclass();
        System.out.println(indexedStringType.getActualTypeArguments()[0]);
        System.out.println(indexedStringType.getActualTypeArguments()[1]);

        ParameterizedType listFieldType = (ParameterizedType)IndexedString.class.getDeclaredField("list").getGenericType();
        System.out.println(listFieldType.getActualTypeArguments()[0]);

        ParameterizedType staticMethodReturnType = (ParameterizedType)IndexedString.class.getDeclaredMethod("staticGenericList").getGenericReturnType();
        System.out.println(staticMethodReturnType.getActualTypeArguments()[0]);

        ParameterizedType nonStaticMethodReturnType = (ParameterizedType)IndexedString.class.getDeclaredMethod("nonStaticGenericList").getGenericReturnType();
        System.out.println(nonStaticMethodReturnType.getActualTypeArguments()[0]);
    }

    @SuppressWarnings("all")
    private static class Pair<T1, T2> {

        private final T1 first;
        private final T2 second;

        private Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        public T1 getFirst() {
            return first;
        }

        public T2 getSecond() {
            return second;
        }
    }

    private static class IndexedString extends Pair<Integer, String> {

        private static List<String> list = Arrays.asList("Value1", "Value2", "Value3");

        private IndexedString(Integer first, String second) {
            super(first, second);
        }

        private static List<Integer> staticGenericList() {
            return Arrays.asList(1, 2, 3);
        }

        private List<IndexedString> nonStaticGenericList() {
            return Collections.singletonList(this);
        }
    }
}
