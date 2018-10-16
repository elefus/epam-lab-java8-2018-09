package spliterators.part3;

import lombok.Value;

@Value
public class IndexedValue<T> {

    int index;
    T value;
}
