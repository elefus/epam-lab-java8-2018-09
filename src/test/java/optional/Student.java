package optional;

import java.io.Serializable;
import java.util.Optional;

public class Student implements Serializable {

    private int id;
    private String group = null;

    public Student(int id) {
        this.id = id;
    }

    public Student(int id, String group) {
        this.id = id;
        this.group = group;
    }

    public Optional<String> getGroup() {
        return Optional.ofNullable(group);
    }
}
