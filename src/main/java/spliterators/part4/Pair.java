package spliterators.part4;

import lombok.Data;

@Data
public class Pair<T, R> {

    private final T value1;
    private final R value2;
}
