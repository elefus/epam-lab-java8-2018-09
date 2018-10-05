package generics.nonrecursive;

import org.junit.jupiter.api.Test;

import java.util.Objects;

class Launcher {

    @Test
    void test() {
        BaseStream<String> baseStream = null;

        baseStream.parallel()
                  .sequential()
                  .parallel();

        Stream<String> stream = null;
        stream.distinct()
              .filter(Objects::nonNull)
              .parallel()
              .distinct()
              .sequential()
              .distinct();



        baseStream = stream;
//        baseStream.distinct();
    }
}
