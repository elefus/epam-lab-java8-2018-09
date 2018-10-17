package spliterators.part3;

import lombok.Data;
import spliterators.part4.Pair;

@Data
@SuppressWarnings("WeakerAccess")
public class IndexedValue<T> extends Pair<Long, T> {

    public IndexedValue(Long value1, T value2) {
        super(value1, value2);
    }

    public long getIndex() {
        return getValue1();
    }

    @Override
    public String toString() {
        return "[" + getIndex() + "] = " + getValue2();
    }
}
