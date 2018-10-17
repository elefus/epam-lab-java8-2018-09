package spliterators.part3;

import java.util.stream.StreamSupport;

public class Example1 {

    public static void main(String[] args) {
        String[] data = {"a", "b", "c", "d", "e"};

//        for (int i = 0; i < data.length; ++i) {
//            if (i % 2 == 0) {
//                System.out.println(data[i]);
//            }
//        }

//        IntStream.range(0, data.length)
//                 .filter(index -> index % 2 == 0)
//                 .mapToObj(index -> data[index])
//                 .forEachOrdered(System.out::println);
//
//        AtomicInteger index = new AtomicInteger();
//        Arrays.stream(data)
//              .parallel()
//              .filter(element -> index.getAndIncrement() % 2 == 0)
//              .forEachOrdered(System.out::println);

        StreamSupport.stream(new IndexedArraySpliterator<>(data), false)
                     .filter(indexedValue -> indexedValue.getIndex() > 1)
                     .filter(indexedValue -> indexedValue.getIndex() % 2 == 0)
                     .forEachOrdered(System.out::println);

    }
}
