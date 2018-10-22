package spliterators.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<T1, T2> {

    private final T1 value1;
    private final T2 value2;
}
